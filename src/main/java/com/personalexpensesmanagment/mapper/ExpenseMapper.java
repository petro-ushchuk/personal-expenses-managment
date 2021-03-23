package com.personalexpensesmanagment.mapper;

import com.personalexpensesmanagment.dto.ExpenseDto;
import com.personalexpensesmanagment.model.Expense;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    Expense mapExpenseDtoToExpense(ExpenseDto expenseDto);

    ExpenseDto mapExpenseToExpenseDto(Expense expense);

    List<ExpenseDto> mapExpensesToExpenseDtos(List<Expense> expenses);

}
