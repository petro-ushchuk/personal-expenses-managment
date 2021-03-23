package com.personalexpensesmanagment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class TotalDto {
    private double total;
    private String currency;

    public void setTotal(double total) {
        this.total = (int) (total*100) / 100.0;
    }
}
