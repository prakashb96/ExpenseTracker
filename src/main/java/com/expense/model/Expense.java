package com.expense.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Expense {
     private int eid;
    private BigDecimal amount;
    private String description;
    private LocalDateTime created_at;
    private int category_id;

    public Expense() {

    }


    public Expense(int eid, BigDecimal amount, String description, LocalDateTime created_at, int category_id) {
        this.eid = eid;
        this.amount = amount;
        this.description = description;
        this.created_at = created_at;
        this.category_id = category_id;
    }
    
    public int getEid() {
        return eid;
    }
    public void setEid(int eid) {
        this.eid = eid;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDateTime getCreated_at() {
        return created_at;
    }
    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
    public int getCategory_id() {
        return category_id;
    }
    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }






}
