package com.example.inventory.service.supplier;

import com.example.inventory.dto.SupplierDto;
import com.example.inventory.entity.Supplier;
import com.example.inventory.specification.ParamField;
import org.springframework.data.domain.Page;

public interface SupplierService {
    Page<Supplier> getSuppliers(ParamField filter);
    SupplierDto save(Supplier supplier);
    SupplierDto getSupplier(Long supplierID);
    SupplierDto edit(Long id,Supplier supplier);
    String delete(Long supplierID);
}
