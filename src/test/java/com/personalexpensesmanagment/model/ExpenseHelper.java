package com.personalexpensesmanagment.model;

import com.personalexpensesmanagment.dto.ExpenseDto;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

public class ExpenseHelper {

    public Expense createTestExpense() {
        return Expense.builder()
                .id(1L)
                .amount(2.28)
                .currency("UAH")
                .date(LocalDate.now())
                .product("Product")
                .build();
    }

    @SneakyThrows
    public ExpenseDto createTestExpenseDto() {
        ExpenseDto expenseDto = new ExpenseDto();
        BeanUtils.copyProperties(createTestExpense(), expenseDto);
        return expenseDto;
    }

}
