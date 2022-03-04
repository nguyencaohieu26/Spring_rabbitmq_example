package com.example.order.repository;

import com.example.order.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(
            value = "SELECT * FROM Cart u WHERE u.access_token = ?1",
            nativeQuery = true)
    Cart findCartAccess_token(String access_token);
}
