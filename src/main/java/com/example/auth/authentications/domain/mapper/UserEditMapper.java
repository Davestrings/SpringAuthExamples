package com.example.auth.authentications.domain.mapper;

import com.example.auth.authentications.domain.dto.CreateUserRequest;
import com.example.auth.authentications.domain.dto.UpdateUserRequest;
import com.example.auth.authentications.domain.model.AppUser;
import com.example.auth.authentications.domain.model.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.*;

import java.util.stream.Collectors;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", uses = ObjectMapper.class)
public abstract class UserEditMapper {

    @Mapping(target = "authorities", ignore = true)
    public abstract AppUser create(CreateUserRequest request);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "authorities", ignore = true)
    public abstract void update(UpdateUserRequest request, @MappingTarget AppUser user);

    @AfterMapping
    protected void afterCreate(CreateUserRequest request, @MappingTarget AppUser user){
        if(request.getAuthorities() != null){
            user.setAuthorities(request.getAuthorities().stream().map(Role::new).collect(Collectors.toSet()));
        }
    }
    @AfterMapping
    protected void afterUpdate(UpdateUserRequest request, @MappingTarget AppUser user){
        if(request.getAuthorities() != null){
            user.setAuthorities(request.getAuthorities().stream().map(Role::new).collect(Collectors.toSet()));
        }
    }
}
