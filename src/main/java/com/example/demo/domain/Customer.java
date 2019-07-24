package com.example.demo.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "CUSTOMER")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_SEQ")
    @SequenceGenerator(allocationSize = 1, name = "CUSTOMER_SEQ", sequenceName = "CUSTOMER_SEQ")
    private Integer id;

    @Column(name = "NAME")
    private String name;
}
