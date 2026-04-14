package com.orlando.ecommerce.controllers;

import com.orlando.ecommerce.entities.DTOs.ProductDTO;
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
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        ProductDTO productDTO = productService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Page<ProductDTO>> getByName(@RequestParam (name = "name", defaultValue = "") String name, Pageable pageable) {
       Page<ProductDTO> dtos = productService.findByName(name, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAll(Pageable pageable) {
        Page<ProductDTO> dtos = productService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDTO> insert (@RequestBody @Valid ProductDTO productDTO){
        ProductDTO dto = productService.insert(productDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@RequestBody @Valid ProductDTO productDTO, @PathVariable Long id){
        ProductDTO dtoAtt = productService.update(id,productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(dtoAtt);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}