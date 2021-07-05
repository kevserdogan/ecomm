package com.tybootcamp.ecomm.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "basket")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToMany
    private List<Product> products;
}
