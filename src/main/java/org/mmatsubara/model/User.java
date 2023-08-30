package org.mmatsubara.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "User_info")
public class User extends PanacheEntityBase {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "userSequence", sequenceName = "user_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequence")
    public Long id;

    @Column(length = 100, nullable = false)
    public String name;

    @Column(length = 255, nullable = false, unique = true)
    public String email;

    @Column(name = "is_active")
    public boolean isActive;

    @Column(name = "is_verified")
    public boolean isVerified;
}
