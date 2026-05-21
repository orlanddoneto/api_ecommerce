package com.orlando.ecommerce.controllers;

import com.orlando.ecommerce.entities.DTOs.CategoryDTO;
import com.orlando.ecommerce.entities.DTOs.ProductDTO;
import com.orlando.ecommerce.entities.DTOs.ProductMinDTO;
import com.orlando.ecommerce.services.CategoryService;
import com.orlando.ecommerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAll() {
        List<CategoryDTO> dtos = categoryService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }


}