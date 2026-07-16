package com.henderson.catalog.service;

import com.henderson.catalog.dto.CategoryDTO;
import com.henderson.catalog.entity.Category;
import com.henderson.catalog.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        List<Category> result = categoryRepository.findAll();
        return result.stream().map(x -> new CategoryDTO(x)).toList();
    }
}
