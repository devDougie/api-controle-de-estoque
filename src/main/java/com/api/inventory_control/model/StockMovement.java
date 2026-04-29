package com.api.inventory_control.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType type;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public User getUser() { return user; }
    public MovementType getType() { return type; }
    public Integer getQuantity() { return quantity; }
    public LocalDateTime getDateTime() { return dateTime; }

    public void setId(Long id) { this.id = id; }
    public void setProduct(Product product) { this.product = product; }
    public void setUser(User user) { this.user = user; }
    public void setType(MovementType type) { this.type = type; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
}