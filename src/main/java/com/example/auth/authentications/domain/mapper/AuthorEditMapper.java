package com.example.auth.authentications.domain.mapper;

import com.example.auth.authentications.domain.dto.EditAuthorRequest;
import com.example.auth.authentications.domain.model.AppUser;
import com.example.auth.authentications.domain.model.Author;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper
public interface AuthorEditMapper {
    Author create(EditAuthorRequest request);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    void update(EditAuthorRequest request, @MappingTarget AppUser user);
}
