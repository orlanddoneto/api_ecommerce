package com.orlando.ecommerce.services;

import com.orlando.ecommerce.entities.DTOs.ProductDTO;
import com.orlando.ecommerce.entities.Product;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {


    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findByName(String name, Pageable pageable){
        Page<Product> products = productRepository.search_name(name, pageable);
        Page<ProductDTO> dtos = products.map(ent ->mapper.map(ent,ProductDTO.class));
        return dtos;
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        return new ProductDTO(productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Produto com ID " + id + " não encontrado.")));

    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        Page<Product> page = productRepository.findAll(pageable);
        return page.map(product -> mapper.map(product, ProductDTO.class));
    }

    @Transactional
    public ProductDTO insert(ProductDTO productDTO){
        Product product = mapper.map(productDTO, Product.class);
        product.setId(null);
        return mapper.map(productRepository.save(product),ProductDTO.class);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Produto com ID " + id + " não encontrado"));

            copyDtoToEntity(productDTO, product);

            return new ProductDTO(productRepository.save(product));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado.");
        }
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto com ID " + id + " não encontrado.");
        }
        try {
            productRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }

    }

    public void copyDtoToEntity(ProductDTO dto, Product entity){
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
    }


}
