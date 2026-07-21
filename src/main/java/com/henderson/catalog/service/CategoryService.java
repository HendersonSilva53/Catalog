package com.henderson.catalog.service;

import com.henderson.catalog.dto.CategoryDTO;
import com.henderson.catalog.entity.Category;
import com.henderson.catalog.repository.CategoryRepository;
import com.henderson.catalog.service.exceptions.DatabaseException;
import com.henderson.catalog.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
        Page<Category> result = categoryRepository.findAll(pageRequest);
        return result.map(x -> new CategoryDTO(x));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){
        Optional<Category> obj = categoryRepository.findById(id);
        Category entity = obj.orElseThrow(()-> new ResourceNotFoundException("Entidade não encontrada"));
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto){
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = categoryRepository.save(entity);
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto){
        try{
            Category category = categoryRepository.getReferenceById(id);
            category.setName(dto.getName());
            category = categoryRepository.save(category);
            return new CategoryDTO(category);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException("Entidade não encontrada");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        if(!categoryRepository.existsById(id)){
            throw new ResourceNotFoundException("Entidade não encontrada");
        }
        try{
            categoryRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Falha de integridade referencial");
        }
    }
}
