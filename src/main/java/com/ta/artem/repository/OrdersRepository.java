package com.ta.artem.repository;

import com.ta.artem.model.OrdersData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersData, Long> {
//    OrdersData findByOrderId(Long orderId);
//    OrdersData findByName(String name);
}
