package com.example.order.specification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FilterField {

    public final static String NAME             = "name";
    public final static String PRICE            = "price";
    public final static String ID               = "id";
    public final static String CATEGORY_ID      = "category_id";
    public final static String DATE_START       = "";
    public final static String DATE_END         = "";
    public final static String PHONE            = "phone";
    public final static String EMAIL            = "email";
    public final static String CHECKOUT         = "checkOut";
    public final static String ACCOUNT_ID       = "account_id";
    public final static String ORDER_PRICE      = "totalPrice";
    public final static String SHIPSTATUS       = "shipStatus";

    private int id;
    private String name;
    private int page;
    private int pageSize;
    private int minPrice;
    private int shipStatus;
    private int maxPrice;
    private int categoryId;
    private int accountId;
    private String phone;
    private String email;
    private String dateStart;
    private String dateEnd;
    private int sortType;
    private boolean checkOut = false;


    public static final class FilterFieldBuilder {
        private int id;
        private String name;
        private int page;
        private int pageSize;
        private int minPrice;
        private int shipStatus;
        private int maxPrice;
        private int categoryId;
        private int accountId;
        private String phone;
        private String email;
        private String dateStart;
        private String dateEnd;
        private int sortType;
        private boolean checkOut = false;

        private FilterFieldBuilder() {
        }

        public static FilterFieldBuilder aFilterField() {
            return new FilterFieldBuilder();
        }

        public FilterFieldBuilder withId(int id) {
            this.id = id;
            return this;
        }

        public FilterFieldBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public FilterFieldBuilder withPage(int page) {
            this.page = page;
            return this;
        }

        public FilterFieldBuilder withPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public FilterFieldBuilder withMinPrice(int minPrice) {
            this.minPrice = minPrice;
            return this;
        }

        public FilterFieldBuilder withShipStatus(int shipStatus) {
            this.shipStatus = shipStatus;
            return this;
        }

        public FilterFieldBuilder withMaxPrice(int maxPrice) {
            this.maxPrice = maxPrice;
            return this;
        }

        public FilterFieldBuilder withCategoryId(int categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public FilterFieldBuilder withAccountId(int accountId) {
            this.accountId = accountId;
            return this;
        }

        public FilterFieldBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public FilterFieldBuilder withEmail(String email) {
            this.email = email;
            return this;
        }
        public FilterFieldBuilder withSortType(int sortType){
            this.sortType = sortType;
            return this;
        }

        public FilterFieldBuilder withDateStart(String dateStart) {
            this.dateStart = dateStart;
            return this;
        }

        public FilterFieldBuilder withDateEnd(String dateEnd) {
            this.dateEnd = dateEnd;
            return this;
        }

        public FilterFieldBuilder withCheckOut(boolean checkOut) {
            this.checkOut = checkOut;
            return this;
        }

        public FilterField build() {
            FilterField filterField = new FilterField();
            filterField.setId(id);
            filterField.setName(name);
            filterField.setPage(page);
            filterField.setPageSize(pageSize);
            filterField.setMinPrice(minPrice);
            filterField.setShipStatus(shipStatus);
            filterField.setMaxPrice(maxPrice);
            filterField.setCategoryId(categoryId);
            filterField.setAccountId(accountId);
            filterField.setPhone(phone);
            filterField.setEmail(email);
            filterField.setDateStart(dateStart);
            filterField.setDateEnd(dateEnd);
            filterField.setCheckOut(checkOut);
            filterField.setSortType(sortType);
            return filterField;
        }
    }
}
