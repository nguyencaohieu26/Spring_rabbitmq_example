package com.example.inventory.service.supplier;

import com.example.inventory.dto.SupplierDto;
import com.example.inventory.entity.Supplier;
import com.example.inventory.enums.SearchOperation;
import com.example.inventory.exceptions.NotFoundException;
import com.example.inventory.mappers.SupplierMapper;
import com.example.inventory.repository.SupplierRepository;
import com.example.inventory.specification.ParamField;
import com.example.inventory.specification.SearchCriteria;
import com.example.inventory.specification.SupplierSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService{

    @Autowired
    SupplierRepository supplierRepository;

    @Override
    public Page<Supplier> getSuppliers(ParamField filter) {
        Specification<Supplier> spe  = Specification.where(null);
        PageRequest paging = PageRequest.of(filter.getPage() - 1,filter.getPageSize());
        if(filter.getName() != null && filter.getName().length() > 0){
            spe = spe.and(new SupplierSpecification(new SearchCriteria(ParamField.NAME, SearchOperation.EQUALITY,filter.getName())));
        }
        if(filter.getId() > 0){
            spe = spe.and(new SupplierSpecification(new SearchCriteria(ParamField.ID,SearchOperation.EQUALITY,filter.getId())));
        }
        return supplierRepository.findAll(spe,paging);
    }

    @Override
    public SupplierDto save(Supplier supplier) {
        supplierRepository.save(supplier);
        return SupplierMapper.INSTANCE.supplierToSupplierDto(supplier);
    }

    @Override
    public SupplierDto getSupplier(Long supplierID) {
        Optional<Supplier> supplier = supplierRepository.findById(supplierID);
        if(!supplier.isPresent()){
            throw new NotFoundException("Supplier is not found!");
        }
        return SupplierMapper.INSTANCE.supplierToSupplierDto(supplier.get());
    }

    @Override
    public SupplierDto edit(Long id, Supplier supplier) {
        Optional<Supplier> supplierExist = supplierRepository.findById(id);
        if(!supplierExist.isPresent()){
            throw new NotFoundException("Supplier is not found!");
        }
        supplierExist.get().updateSupplier(supplier);
        supplierRepository.save(supplierExist.get());
        return SupplierMapper.INSTANCE.supplierToSupplierDto(supplierExist.get());
    }

    @Override
    public String delete(Long supplierID) {
        Optional<Supplier> supplier = supplierRepository.findById(supplierID);
        if(!supplier.isPresent()){
            throw new NotFoundException("Supplier is not found!");
        }
        supplierRepository.deleteById(supplierID);
        return "Delete supplier successfully";
    }
}
