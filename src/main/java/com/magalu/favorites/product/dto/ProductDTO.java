package com.magalu.favorites.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProductDTO {
    @JsonProperty("price")
    private String price;
    @JsonProperty("image")
    private String image;
    @JsonProperty("brand")
    private String brand;
    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;
}
