package com.example.order.repository;

import com.example.order.entity.OrderDetail;
import com.example.order.entity.OrderDetailKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailKey> {
}
