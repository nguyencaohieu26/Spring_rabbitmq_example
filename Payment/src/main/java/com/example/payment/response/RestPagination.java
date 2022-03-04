package com.example.payment.response;

public class RestPagination {
    //limit + offset (skip)
    //101 records
    //page 2, limit 10
    //total pages = 11;
    //101 / limit

    private int page;
    private int limit;
    private int totalPages;
    private long totalItems;

    //create constructor
    public RestPagination(){}
    public RestPagination(int page,int limit,long totalItems){
        this.page =page;
        this.limit = limit;
        if(limit != 0){
            //count how many pages
            //if total items % limit equal 0 -> we will have exacted the number of page
            //else if total items % limit greater than 0 -> we will plus 1 more pages
            this.totalPages =(totalItems % limit == 0) ? (int) (totalItems / limit) : ((int) (totalItems / limit) + 1);
        }else{
            this.totalPages = 0;
        }
            this.totalItems =totalItems;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }
}

