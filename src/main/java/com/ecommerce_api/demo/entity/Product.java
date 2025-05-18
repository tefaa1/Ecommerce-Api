package com.ecommerce_api.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Min(value = 0, message = "Quantity must be at least 0")
    @Column(nullable = false)
    private Integer stockQuantity;

    @Min(value = 0, message = "Discount must be at least 0")
    @Max(value = 100, message = "Discount cannot exceed 100")
    @Column(nullable = false)
    private Integer discount;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<CartItem> cartItems =new HashSet<>();

    @ManyToMany(mappedBy = "products")
    private Set<Wishlist>wishlists = new HashSet<>();

    @PrePersist
    private void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate(){
        updatedAt = LocalDateTime.now();
    }   

    @Transient
    public BigDecimal getNewPrice() {
        BigDecimal discountAmount = price.multiply(BigDecimal.valueOf(discount))
                                       .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return price.subtract(discountAmount);
    }
}
