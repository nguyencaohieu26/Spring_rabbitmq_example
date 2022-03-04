package com.example.inventory.specification;

import com.example.inventory.entity.ImportExportHistory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ImportExportHistorySpecification implements Specification<ImportExportHistory> {
    private final SearchCriteria criteria;

    public ImportExportHistorySpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<ImportExportHistory> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        switch (criteria.getOperation()){
            case GREATER_THAN:
                return builder.greaterThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN:
                return builder.lessThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString());
            case EQUALITY:
                if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    return builder.like(
                            root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
                } else {
                    return builder.equal(root.get(criteria.getKey()), criteria.getValue());
                }
            case JOIN:
            default:
                return null;
        }
    }
}
