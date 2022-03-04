package com.example.inventory.service.importExportHistory;

import com.example.inventory.dto.ImportExportHistoryDto;
import com.example.inventory.entity.ImportExportHistory;
import com.example.inventory.entity.Product;
import com.example.inventory.enums.Status;
import com.example.inventory.exceptions.NotFoundException;
import com.example.inventory.exceptions.SystemException;
import com.example.inventory.mappers.ImportExportHistoryMapper;
import com.example.inventory.repository.ImportExportHistoryRepository;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.specification.ParamField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class importExportHistoryImpl implements importExportHistoryService{
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ImportExportHistoryRepository importExportHistoryRepository;

    @Override
    public Page<ImportExportHistory> getImportExportHistories(ParamField field) {
        Specification<ImportExportHistory> spe = Specification.where(null);
        PageRequest paging = PageRequest.of(field.getPage() - 1,field.getPageSize());

        return importExportHistoryRepository.findAll(spe,paging);
    }

    @Override
    public ImportExportHistoryDto save(ImportExportHistory importExportHistory) {
        Optional<Product> productExist = productRepository.findById(importExportHistory.getProductID());
        if(!productExist.isPresent()){
            throw new NotFoundException("Product Not Found!");
        }
        int presentQuantity = productExist.get().getInStock();
        importExportHistoryRepository.save(importExportHistory);
        if(importExportHistory.getType().equals(Status.HistoryType.IMPORT.name())){
            productExist.get().setInStock(presentQuantity + importExportHistory.getQuantity());
        }else if(importExportHistory.getType().equals(Status.HistoryType.EXPORT.name())){
            if(importExportHistory.getQuantity() > productExist.get().getInStock()){
                throw new SystemException("Product in stock not enough");
            }
            productExist.get().setInStock(presentQuantity - importExportHistory.getQuantity());
        }
        productRepository.save(productExist.get());
        return ImportExportHistoryMapper.INSTANCE.importExportHistoryToImportExportHistoryDto(importExportHistory);
    }

    @Override
    public ImportExportHistoryDto getImportExportHistory(Long id) {
        Optional<ImportExportHistory> importExportHistoryExist = importExportHistoryRepository.findById(id);
        if(!importExportHistoryExist.isPresent()){
            throw new NotFoundException("Not found!");
        }
        return ImportExportHistoryMapper.INSTANCE.importExportHistoryToImportExportHistoryDto(importExportHistoryExist.get());
    }

    @Override
    public ImportExportHistoryDto edit(Long id, ImportExportHistory importExportHistory) {
        Optional<ImportExportHistory> importExportHistoryExist = importExportHistoryRepository.findById(id);
        Optional<Product> productExist = productRepository.findById(importExportHistory.getProductID());
        if(!importExportHistoryExist.isPresent()){
            throw new NotFoundException("Not found!");
        }
        if(!productExist.isPresent()){
            throw new NotFoundException("Product is not found!");
        }

        return null;
    }

    @Override
    public String delete(Long importExportHistoryID) {
        Optional<ImportExportHistory> importExportHistoryExist =importExportHistoryRepository.findById(importExportHistoryID);
        if(!importExportHistoryExist.isPresent()){
            throw new NotFoundException("Not found!");
        }
        importExportHistoryRepository.deleteById(importExportHistoryID);
        return "Delete successfully";
    }
}
