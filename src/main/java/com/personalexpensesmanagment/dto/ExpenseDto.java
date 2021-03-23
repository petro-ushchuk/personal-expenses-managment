package com.personalexpensesmanagment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personalexpensesmanagment.dto.validation.group.OnCreate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpenseDto {
    @JsonProperty(access = READ_ONLY)
    private long id;
    @NotNull(groups = OnCreate.class, message = "Can't be null")
    private LocalDate date;
    @NotNull(groups = OnCreate.class, message = "Can't be null")
    private double amount;
    @NotNull(groups = OnCreate.class, message = "Can't be null")
    @Pattern(regexp = "[a-zA-Z]{3}", message = "Currency isn't valid.")
    private String currency;
    @NotNull(groups = OnCreate.class, message = "Can't be null")
    private String product;

    @Override
    public String toString() {
        return "(" +
                "id=" + id +
                ", date=" + date +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", product='" + product + '\'' +
                ')';
    }
}
