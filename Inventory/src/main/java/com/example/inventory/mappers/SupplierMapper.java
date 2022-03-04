package com.example.inventory.mappers;

import com.example.inventory.dto.SupplierDto;
import com.example.inventory.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SupplierMapper {
    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);
    SupplierDto supplierToSupplierDto(Supplier supplier);
}
