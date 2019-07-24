package com.example.demo.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "TEAM")
public class Team implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAM_SEQ")
    @SequenceGenerator(allocationSize = 1, name = "TEAM_SEQ", sequenceName = "TEAM_SEQ")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "TEAM_CUSTOMER",
            joinColumns = @JoinColumn(name = "TEAM_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID"))
    private Set<Customer> customers;

    public Set<Customer> getCustomers() {
        if (customers == null) {
            customers = new HashSet<>();
        }
        return customers;
    }
}

