package com.ta.artem.service;

import com.ta.artem.model.OrdersData;
import com.ta.artem.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public List<OrdersData> getAllOrders() {
        return ordersRepository.findAll();
    }

    public void createOrder(OrdersData order) {
        ordersRepository.save(order);
    }
}
