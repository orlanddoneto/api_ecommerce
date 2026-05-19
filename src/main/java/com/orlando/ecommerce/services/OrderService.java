package com.orlando.ecommerce.services;

import com.orlando.ecommerce.entities.DTOs.OrderDTO;
import com.orlando.ecommerce.entities.DTOs.ProductDTO;
import com.orlando.ecommerce.repositories.OrderRepository;
import com.orlando.ecommerce.repositories.ProductRepository;
import com.orlando.ecommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id){
        return new OrderDTO(orderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Produto com ID " + id + " não encontrado.")));

    }
}
