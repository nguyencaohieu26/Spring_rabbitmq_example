package com.example.inventory.service.product;

import com.example.inventory.dto.ProductDto;
import com.example.inventory.entity.Product;
import com.example.inventory.enums.SearchOperation;
import com.example.inventory.exceptions.NotFoundException;
import com.example.inventory.mappers.ProductMapper;
import com.example.inventory.repository.CategoryRepository;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.repository.SupplierRepository;
import com.example.inventory.specification.ParamField;
import com.example.inventory.specification.ProductSpecification;
import com.example.inventory.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Override
    public Page<Product> getProducts(ParamField filter) {
        Specification<Product> spe = Specification.where(null);
        PageRequest paging = null;
        paging = PageRequest.of(filter.getPage() - 1,filter.getPageSize());
        if(filter.getSort() > - 1){
            if(filter.getSort() == 0){
                paging = PageRequest.of(filter.getPage() - 1,filter.getPageSize(), Sort.Direction.DESC,ParamField.NAME);
            }else if(filter.getSort() == 1){
                paging = PageRequest.of(filter.getPage() - 1,filter.getPageSize(), Sort.Direction.ASC,ParamField.NAME);
            }else if(filter.getSort() == 2){
                paging = PageRequest.of(filter.getPage() - 1,filter.getPageSize(), Sort.Direction.ASC,ParamField.PRICE);
            }else if(filter.getSort() == 3){
                paging = PageRequest.of(filter.getPage() - 1,filter.getPageSize(), Sort.Direction.DESC,ParamField.PRICE);
            }
        }
        if(filter.getName() != null && filter.getName().length() > 0){
            spe = spe.and(new ProductSpecification(new SearchCriteria(ParamField.NAME, SearchOperation.EQUALITY,filter.getName())));
        }
        if(filter.getCategory() > 0){
            spe = spe.and(new ProductSpecification(new SearchCriteria(ParamField.CATEGORY_ID,SearchOperation.EQUALITY,filter.getCategory())));
        }
        if(filter.getSupplier() > 0){
            spe = spe.and(new ProductSpecification(new SearchCriteria(ParamField.SUPPLIER_ID,SearchOperation.EQUALITY,filter.getSupplier())));
        }
        if(filter.getMaxPrice().doubleValue() > 0){

        }
        if(filter.getMinPrice().doubleValue() > 0){

        }
        if(filter.getId() > 0){
            spe = spe.and(new ProductSpecification(new SearchCriteria(ParamField.ID,SearchOperation.EQUALITY,filter.getId())));
        }
        return productRepository.findAll(spe,paging);
    }

    @Override
    public ProductDto save(Product product) {
        product.setCategory(categoryRepository.findById(product.getCategoryID()).get());
        product.setSupplier(supplierRepository.findById(product.getSupplierID()).get());
        productRepository.save(product);
        return ProductMapper.INSTANCE.productToProductDto(product);
    }

    @Override
    public ProductDto getProduct(Long productID) {
        Optional<Product> product = productRepository.findById(productID);
        if(!product.isPresent()){
            throw new NotFoundException("Product is not found");
        }
        return ProductMapper.INSTANCE.productToProductDto(product.get());
    }

    @Override
    public ProductDto edit(Long id,Product product) {
        Optional<Product> productExist = productRepository.findById(id);
        if(!productExist.isPresent()){
            throw new NotFoundException("Product is not found");
        }
        productExist.get().updateProduct(product);
        productExist.get().setCategory(categoryRepository.findById(product.getCategoryID()).get());
        productExist.get().setSupplier(supplierRepository.findById(product.getSupplierID()).get());
        productRepository.save(productExist.get());
        return ProductMapper.INSTANCE.productToProductDto(productExist.get());

    }

    @Override
    public void delete(Long productID) {
        Optional<?> product = productRepository.findById(productID);
        if(!product.isPresent()){
            throw new NotFoundException("Product is not found!");
        }
        productRepository.deleteById(productID);
    }

    @Override
    public void updateQuantity(Long productID, int quantity) {
        Optional<?> product = productRepository.findById(productID);
        if(!product.isPresent()){
            throw new NotFoundException("Product is not found!");
        }
    }
}
