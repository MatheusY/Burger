package org.mmatsubara.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.mmatsubara.model.Product;

import java.math.BigDecimal;

@Entity
@Table(name = "Item")
public class Item extends PanacheEntityBase {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "itemSequence", sequenceName = "item_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemSequence")
    public Long id;

    @ManyToOne
    @JoinColumn(name = "product_fk", nullable = false)
    public Product product;

    @ManyToOne
    @JoinColumn(name = "order_fk", nullable = false)
    public Order order;

    @Column(nullable = false)
    public Integer quantity;

    @Column(nullable = false)
    public BigDecimal subtotal;
}
