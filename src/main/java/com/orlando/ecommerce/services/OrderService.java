package com.orlando.ecommerce.services;

import com.orlando.ecommerce.entities.DTOs.OrderDTO;
import com.orlando.ecommerce.entities.DTOs.OrderItemDTO;
import com.orlando.ecommerce.entities.DTOs.ProductDTO;
import com.orlando.ecommerce.entities.Order;
import com.orlando.ecommerce.entities.OrderItem;
import com.orlando.ecommerce.entities.Product;
import com.orlando.ecommerce.entities.User;
import com.orlando.ecommerce.entities.enums.OrderStatus;
import com.orlando.ecommerce.repositories.OrderItemRepository;
import com.orlando.ecommerce.repositories.OrderRepository;
import com.orlando.ecommerce.repositories.ProductRepository;
import com.orlando.ecommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id){
        return new OrderDTO(orderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Produto com ID " + id + " não encontrado.")));

    }

    @Transactional
    public OrderDTO insert(OrderDTO orderDTO) {
        Order order = new Order();
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);
        order.setUser(userService.authenticated());

        orderRepository.save(order);

        for (OrderItemDTO item: orderDTO.getItems()){
            Product product = productRepository.getReferenceById(item.getProductId());
            OrderItem orderItem = new OrderItem(product,order,item.getQuantity(), product.getPrice());
            order.getOrderItems().add(orderItem);
        }

        orderItemRepository.saveAll(order.getOrderItems());

        return new OrderDTO(order);
    }
}
