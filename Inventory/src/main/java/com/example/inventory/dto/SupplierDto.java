package com.example.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDto {
    private long id;
    private String name;
    private String address;
    private String phone;
    private String email;
}
