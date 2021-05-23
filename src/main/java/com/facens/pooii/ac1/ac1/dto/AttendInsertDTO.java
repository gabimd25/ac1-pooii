package com.facens.pooii.ac1.ac1.dto;


public class AttendInsertDTO extends BaseUserInsertDTO{
    
    private Double balance = 0.00 ;

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public AttendInsertDTO(){

    }
}
