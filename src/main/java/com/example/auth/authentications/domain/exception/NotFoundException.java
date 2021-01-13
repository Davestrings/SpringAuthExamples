package com.example.auth.authentications.domain.exception;

import org.bson.types.ObjectId;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
    public NotFoundException(Class<?> klass, long id){
        super(String.format("Entity %s with id %d not found", klass.getSimpleName(), id));
    }
    public NotFoundException(Class<?> klass, String id){
        super(String.format("Entity %s with id %s not found", klass.getSimpleName(), id));
    }
    public NotFoundException(Class<?> klass, ObjectId id){
        super(String.format("Entity %s with id %s not found", klass.getSimpleName(), id));
    }
}
