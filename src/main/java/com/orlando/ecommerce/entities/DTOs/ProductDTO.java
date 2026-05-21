package com.orlando.ecommerce.entities.DTOs;


import com.orlando.ecommerce.entities.Category;
import com.orlando.ecommerce.entities.Product;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

public class ProductDTO {

    private Long id;
    @Size(min = 3, max = 80, message = "O nome deve ter tamanho entre 3 e 80 caracteres.")
    @NotBlank(message = "O campo é obrigatório.")
    private String name;
    @Size(min = 10, message = "A descrição deve ter no mínimo 10 caracteres.")
    @NotBlank(message = "O campo é obrigatório.")
    private String description;
    @NotNull(message = "Campo requerido")
    @Positive(message = "O preço deve ser positivo")
    private Double price;
    private String imgUrl;

    @NotEmpty(message = "Deve haver ao menos uma categoria")
    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO() {
    }

    public ProductDTO(Product product) {
        id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.imgUrl = product.getImgUrl();
        for (Category category : product.getCategories()){
            CategoryDTO cat2 = new CategoryDTO(category);
            categories.add(cat2);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }
}
