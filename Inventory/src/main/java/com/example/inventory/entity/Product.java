package com.example.inventory.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
@SQLDelete(sql = "UPDATE products SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")

public class Product extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Product name is required")
    private String name;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    @Column(columnDefinition = "TEXT")
    @NotNull(message = "Description is required")
    private String description;

    @Column(columnDefinition = "TEXT")
    @NotEmpty(message = "Thumbnail is required")
    private String thumbnail;

    @NotNull(message = "Product quantity is required")
    private int inStock;

    private Boolean deleted = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name="supplierID",insertable = false,updatable = false)
    private Supplier supplier;
    private Long supplierID;

    @ManyToOne
    @JoinColumn(name = "categoryID",insertable = false,updatable = false)
    private Category category;
    private Long categoryID;

    public void updateProduct(Product product){
        this.name           = product.getName();
        this.price          = product.getPrice();
        this.inStock        = product.getInStock();
        this.thumbnail      = product.getThumbnail();
        this.supplierID     = product.getSupplierID();
        this.categoryID     = product.getCategoryID();
    }

}
