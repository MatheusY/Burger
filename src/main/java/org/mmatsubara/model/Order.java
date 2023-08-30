package org.mmatsubara.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Purchase_order")
public class Order extends PanacheEntityBase {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "orderSequence", sequenceName = "order_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSequence")
    public Long id;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    public List<Item> items;

    @Column(nullable = false)
    public LocalDateTime orderedDate;

    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    public OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_fk", nullable = false)
    public User customer;
}
