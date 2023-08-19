package org.mmatsubara.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Product")
public class Product extends PanacheEntityBase {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "productSequence", sequenceName = "product_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productSequence")
    public Long id;

    @Column(nullable = false, length = 100)
    public String name;

    @Column(length = 300)
    public String description;

    @Column(nullable = false)
    public BigDecimal price;

    @Column(name = "product_type", nullable = false)
    @Enumerated(EnumType.STRING)
    public ProductType productType;

    @Column(name = "created_date", nullable = false)
    public LocalDateTime createdDate;

    @Column(name = "is_active", nullable = false)
    public boolean isActive;

    public Product() {
    }
    public Product(String name, String description, BigDecimal price, ProductType productType) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.productType = productType;
        this.createdDate = LocalDateTime.now();
        this.isActive = true;
    }


}
