package com.personalexpensesmanagment.controller;

import com.personalexpensesmanagment.api.ExpensesApi;
import com.personalexpensesmanagment.dto.ExpenseDto;
import com.personalexpensesmanagment.dto.TotalDto;
import com.personalexpensesmanagment.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ExpensesController implements ExpensesApi {

    private final ExpenseService expenseService;

    @Override
    public ExpenseDto createExpense(ExpenseDto expenseDto) {
       return expenseService.createExpense(expenseDto);
    }

    @Override
    public Map<LocalDate, List<ExpenseDto>> getExpenses() {
        return expenseService.getExpenses();
    }

    @Override
    public List<ExpenseDto> deleteExpenseByDate(LocalDate date) {
        return expenseService.deleteByDate(date);
    }

    @Override
    public TotalDto getTotal(String base) {
        return expenseService.getTotal(base);
    }
}
