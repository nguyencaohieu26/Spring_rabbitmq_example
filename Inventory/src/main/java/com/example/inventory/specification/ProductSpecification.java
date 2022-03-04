package com.example.inventory.specification;

import com.example.inventory.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProductSpecification implements Specification<Product> {
    private final SearchCriteria searchCriteria;

    public ProductSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
            switch (searchCriteria.getOperation()){
                case GREATER_THAN:
                    return builder.greaterThanOrEqualTo(
                            root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
                case LESS_THAN:
                    return builder.lessThanOrEqualTo(
                            root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
                case EQUALITY:
                    if (root.get(searchCriteria.getKey()).getJavaType() == String.class) {
                        return builder.like(
                                root.get(searchCriteria.getKey()), "%" + searchCriteria.getValue() + "%");
                    } else {
                        return builder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
                    }
                case JOIN:
                default:
                    return null;
            }
    }
}
