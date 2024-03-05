package com.Icwd.electronic.store.entities;

import com.Icwd.electronic.store.dtos.CategoryDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Products")
public class Product {

    @Id
    private String productId;
    private String title;

    @Column(length = 10000)
    private String description;

    private int price;
    private int discountedPrice;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private String productImageName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;




}
