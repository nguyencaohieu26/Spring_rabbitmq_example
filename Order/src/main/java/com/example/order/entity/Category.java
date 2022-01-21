package com.example.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
@Entity

public class Category extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Category name is required")
    private String name;

    @NotEmpty(message = "Description is required")
    private String description;

    //create relationship between product & category
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Product> products = new HashSet<>();
}
