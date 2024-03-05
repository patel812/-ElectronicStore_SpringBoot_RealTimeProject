package com.Icwd.electronic.store.dtos;

import com.Icwd.electronic.store.entities.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductDto {

    private String productId;
    private String title;
    private String description;
    private int price;
    private int discountedPrice;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private String productImageName;
    private CategoryDto category;



}
