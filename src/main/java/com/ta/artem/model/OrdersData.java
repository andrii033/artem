package com.ta.artem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrdersData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;

    String Manager;
    String Technologist;
    String Warehouse;
    String QC;
    String Purchase;
}
