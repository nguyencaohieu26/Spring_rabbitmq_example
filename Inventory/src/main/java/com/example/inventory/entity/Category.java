package com.example.inventory.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
@SQLDelete(sql = "UPDATE categories SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")

public class Category extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    @NotNull(message = "Category name is required")
    private String name;

    @Column(columnDefinition = "TEXT")
    @NotNull(message = "Thumbnail is required")
    private String thumbnail;

    @Column(columnDefinition = "TEXT")
    @NotNull(message = "Description is required")
    private String description;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<Product> products;

    private Boolean deleted = Boolean.FALSE;

    public void updateCategory(Category category){
        this.name        = category.getName();
        this.thumbnail   = category.getThumbnail();
        this.description = category.getDescription();
    }
}
