package com.example.auth.authentications.repository;

import com.example.auth.authentications.domain.dto.Page;
import com.example.auth.authentications.domain.dto.SearchUsersQuery;
import com.example.auth.authentications.domain.exception.NotFoundException;
import com.example.auth.authentications.domain.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
@CacheConfig(cacheNames = "users")
public interface UserRepo extends MongoRepository<AppUser, ObjectId>, UserRepoCustom {

    @CacheEvict(allEntries = true)
    <S extends AppUser>List<S> saveAll(Iterable<S> entities);

    @Caching(evict = { @CacheEvict(key = "#p0.id"), @CacheEvict(key = "#p0.username")})
    <S extends AppUser> S save(S entity);

    @Cacheable
    Optional<AppUser> findById(ObjectId id);

    @Cacheable
    default AppUser getById(ObjectId id){
        Optional<AppUser> optionalAppUser = findById(id);
        if(optionalAppUser.isEmpty()){
            throw new NotFoundException(AppUser.class, id);
        }
        if(!optionalAppUser.get().isEnabled()){
            throw new NotFoundException(AppUser.class, id);
        }
        return optionalAppUser.get();
    }
    @Cacheable
    Optional<AppUser> findByUsername(String username);
}
interface UserRepoCustom{
    List<AppUser> searchUsers(Page page, SearchUsersQuery query);
}

@RequiredArgsConstructor
class UserRepoCustomImpl implements UserRepoCustom{

    private final MongoTemplate mongoTemplate;

    @Override
    public List<AppUser> searchUsers(Page page, SearchUsersQuery query) {
        List<AggregationOperation> operations = new ArrayList<>();
        List<Criteria> criteriaList = new ArrayList<>();

        if(!StringUtils.isEmpty(query.getId())){
            criteriaList.add(Criteria.where("id").is(new ObjectId(query.getId())));
        }
        if(!StringUtils.isEmpty(query.getUsername())){
            criteriaList.add(Criteria.where("username").regex(query.getUsername(), "i"));
        }
        if(!StringUtils.isEmpty(query.getFullname())){
            criteriaList.add(Criteria.where("fullName").regex(query.getFullname(), "i"));
        }
        if(!criteriaList.isEmpty()){
            Criteria userCriteria = new Criteria().andOperator(criteriaList.toArray(criteriaList.toArray(new Criteria[0])));
            operations.add(match(userCriteria));
        }

        operations.add(sort(Sort.Direction.DESC, "createdAt"));
        operations.add(skip((page.getNumber() - 1) * page.getLimit()));
        operations.add(limit(page.getLimit()));

        TypedAggregation<AppUser> aggregation = newAggregation(AppUser.class, operations);
        AggregationResults<AppUser> results = mongoTemplate.aggregate(aggregation, AppUser.class);
        return results.getMappedResults();
    }
}
