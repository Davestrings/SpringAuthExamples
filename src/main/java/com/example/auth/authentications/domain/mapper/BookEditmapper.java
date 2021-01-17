package com.example.auth.authentications.domain.mapper;

import com.example.auth.authentications.domain.dto.EditBookRequest;
import com.example.auth.authentications.domain.model.AppUser;
import com.example.auth.authentications.domain.model.Book;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper
public interface BookEditmapper {
     Book  create(EditBookRequest request);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    void update(EditBookRequest request, @MappingTarget AppUser user);
}
