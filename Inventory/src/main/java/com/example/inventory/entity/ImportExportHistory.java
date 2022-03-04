package com.example.inventory.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "importExportHistories")
@SQLDelete(sql = "UPDATE importExportHistories SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")

public class ImportExportHistory extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderID;

    private int quantity;

    private Long productID;

    private BigDecimal totalValue;

    private String type;

    private Boolean deleted = Boolean.FALSE;

}
