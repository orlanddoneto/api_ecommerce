package com.orlando.ecommerce.controllers;

import com.orlando.ecommerce.entities.DTOs.OrderDTO;
import com.orlando.ecommerce.entities.DTOs.ProductDTO;
import com.orlando.ecommerce.entities.DTOs.ProductMinDTO;
import com.orlando.ecommerce.services.OrderService;
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


@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable Long id) {
        OrderDTO orderDTO = orderService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
    }



}