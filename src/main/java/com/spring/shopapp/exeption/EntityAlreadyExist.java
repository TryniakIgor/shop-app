package com.spring.shopapp.exeption;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(value = HttpStatus.CONFLICT)
public class EntityAlreadyExist extends RuntimeException {
    private static final long serialVersionUID = 2L;
    private String resourceName;
    private String fieldName;

    public EntityAlreadyExist(String resourceName, String fieldName) {
        super(String.format("%1s with name  %2s already exist ", resourceName, fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
    }
}