package com.personalexpensesmanagment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personalexpensesmanagment.dto.validation.CustomCurrency;
import com.personalexpensesmanagment.dto.validation.group.OnCreate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpenseDto {
    @Null(groups = OnCreate.class, message = "${validation.must.be.null")
    @JsonProperty(access = READ_ONLY)
    private long id;
    @NotNull(groups = OnCreate.class, message = "${validation.cant.be.null")
    private LocalDate date;
    @NotNull(groups = OnCreate.class)
    private double amount;
    @NotNull(groups = OnCreate.class)
    @CustomCurrency
    private String currency;
    @NotNull(groups = OnCreate.class)
    private String product;
}
