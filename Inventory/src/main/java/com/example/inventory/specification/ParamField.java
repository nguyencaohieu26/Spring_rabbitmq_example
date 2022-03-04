package com.example.inventory.specification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ParamField {
    public final static String NAME     = "name";
    public final static String ID       = "id";
    public final static String PRICE    = "price";
    public final static String PHONE    = "phone";
    public final static String EMAIL    = "email";
    public final static String CATEGORY_ID = "categoryID";
    public final static String SUPPLIER_ID = "supplierID";
    public final static String PRODUCT_ID  = "productID";
    public final static String ORDER_ID    = "orderID";

    private long id;
    private long category;
    private long supplier;
    private long product;
    private long order;

    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    private int page;
    private int pageSize;

    private String name;
    private String phone;
    private String email;
    private int sort;
    private int type;
    private HashMap<String,String> mapField;

    public static final class ParamFieldBuilder {
        private long id;
        private long category;
        private long supplier;
        private long product;
        private long order;
        private BigDecimal minPrice;
        private BigDecimal maxPrice;
        private int page;
        private int pageSize;
        private String name;
        private String phone;
        private String email;
        private int sort;
        private int type;
        private HashMap<String,String> mapField;

        private ParamFieldBuilder() {
        }

        public static ParamFieldBuilder aParamField() {
            return new ParamFieldBuilder();
        }

        public ParamFieldBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public ParamFieldBuilder withCategory(long category) {
            this.category = category;
            return this;
        }

        public ParamFieldBuilder withSupplier(long supplier) {
            this.supplier = supplier;
            return this;
        }

        public ParamFieldBuilder withProduct(long product) {
            this.product = product;
            return this;
        }

        public ParamFieldBuilder withOrder(long order) {
            this.order = order;
            return this;
        }

        public ParamFieldBuilder withMinPrice(BigDecimal minPrice) {
            this.minPrice = minPrice;
            return this;
        }

        public ParamFieldBuilder withMaxPrice(BigDecimal maxPrice) {
            this.maxPrice = maxPrice;
            return this;
        }

        public ParamFieldBuilder withPage(int page) {
            this.page = page;
            return this;
        }

        public ParamFieldBuilder withPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public ParamFieldBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ParamFieldBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public ParamFieldBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public ParamFieldBuilder withSort(int sort) {
            this.sort = sort;
            return this;
        }

        public ParamFieldBuilder withType(int type) {
            this.type = type;
            return this;
        }

        public ParamFieldBuilder withMapField(HashMap<String, String> mapField) {
            this.mapField = mapField;
            return this;
        }

        public ParamField build() {
            ParamField paramField = new ParamField();
            paramField.setId(id);
            paramField.setCategory(category);
            paramField.setSupplier(supplier);
            paramField.setProduct(product);
            paramField.setOrder(order);
            paramField.setMinPrice(minPrice);
            paramField.setMaxPrice(maxPrice);
            paramField.setPage(page);
            paramField.setPageSize(pageSize);
            paramField.setName(name);
            paramField.setPhone(phone);
            paramField.setEmail(email);
            paramField.setSort(sort);
            paramField.setType(type);
            paramField.setMapField(mapField);
            return paramField;
        }
    }
}
