package com.henderson.catalog.dto;

import com.henderson.catalog.entity.Category;

import java.io.Serializable;

public class CategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    public CategoryDTO(){}

    public CategoryDTO(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category entity){
        id = entity.getId();
        name = entity.getName();
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}
