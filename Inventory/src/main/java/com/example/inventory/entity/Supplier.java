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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "suppliers")
@SQLDelete(sql = "UPDATE suppliers SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")

public class Supplier extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    @NotNull(message = "Supplier name is required")
    private String name;

    @Column(columnDefinition = "TEXT")
    @NotNull(message = "Address is required")
    private String address;

    @Column(columnDefinition = "TEXT")
    @NotNull(message = "Phone is required")
    private String phone;

    @Column(columnDefinition = "TEXT")
    @NotNull(message = "Email is required")
    private String email;

    private Boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "supplier")
    @JsonIgnore
    private Set<Product> products;

    public void updateSupplier(Supplier supplier){
        this.name       = supplier.getName();
        this.address    = supplier.getAddress();
        this.phone      = supplier.getPhone();
        this.email      = supplier.getEmail();
    }
}
