package com.orlando.ecommerce.repositories;

import com.orlando.ecommerce.entities.Order;
import com.orlando.ecommerce.entities.OrderItem;
import com.orlando.ecommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
