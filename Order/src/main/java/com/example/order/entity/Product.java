package com.example.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@SQLDelete(sql = "UPDATE products SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")

public class Product extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //create relationship with order detail
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Set<OrderDetail> orderDetails = new HashSet<>();

    //create relationship with category
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id",insertable = false,updatable = false)
    @JsonIgnore
    private Category category;

    @NotNull(message = "Category ID is required")
    @Column(name = "category_id")
    private Integer category_id;

    @NotNull(message = "Product name is required")
    private String name;


    @NotNull(message = "Price is required")
    private int price;

    @NotEmpty(message = "Thumbnail is required")
    @Column(columnDefinition = "TEXT")
    private String thumbnail;

    private Boolean deleted = Boolean.FALSE;
}
