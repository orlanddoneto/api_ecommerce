package com.orlando.ecommerce.services;

import com.orlando.ecommerce.entities.Category;
import com.orlando.ecommerce.entities.DTOs.CategoryDTO;
import com.orlando.ecommerce.entities.DTOs.ProductDTO;
import com.orlando.ecommerce.entities.DTOs.ProductMinDTO;
import com.orlando.ecommerce.entities.Product;
import com.orlando.ecommerce.repositories.CategoryRepository;
import com.orlando.ecommerce.repositories.ProductRepository;
import com.orlando.ecommerce.services.exceptions.DatabaseException;
import com.orlando.ecommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;


    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> new CategoryDTO(category)).toList();
    }


    }



