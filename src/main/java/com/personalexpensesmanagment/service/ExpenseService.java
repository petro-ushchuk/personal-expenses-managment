package com.personalexpensesmanagment.service;

import com.personalexpensesmanagment.dto.ExpenseDto;
import com.personalexpensesmanagment.dto.TotalDto;
import com.personalexpensesmanagment.mapper.ExpenseMapper;
import com.personalexpensesmanagment.model.Expense;
import com.personalexpensesmanagment.repository.ExpenseRepository;
import com.personalexpensesmanagment.util.ExchangeRatesUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final ExchangeRatesUtil exchangeRatesUtil;

    public ExpenseDto createExpense(ExpenseDto expenseDto) {
        Expense expense = expenseMapper.mapExpenseDtoToExpense(expenseDto);
        expense = expenseRepository.save(expense);
        return expenseMapper.mapExpenseToExpenseDto(expense);
    }

    public Map<LocalDate, List<ExpenseDto>> getExpenses() {
        List<Expense> expenses = expenseRepository.findAll(Sort.by("date"));
        List<ExpenseDto> expenseDtos = expenseMapper.mapExpensesToExpenseDtos(expenses);
        return expenseDtos
                .stream()
                .sorted()
                .collect(Collectors.groupingBy(ExpenseDto::getDate));
    }

    @Transactional
    public List<ExpenseDto> deleteByDate(LocalDate date) {
        List<Expense> deletedExpenses = expenseRepository.findAllByDate(date);
        expenseRepository.deleteAllByDate(date);
        return expenseMapper.mapExpensesToExpenseDtos(deletedExpenses);
    }

    public TotalDto getTotal(String base) {
        TotalDto totalDto = new TotalDto();
        List<Expense> expenses = expenseRepository.findAll(Sort.by("currency"));
        if (expenses.isEmpty()) {
            return totalDto;
        }
        Map<String, Double> rates = exchangeRatesUtil.getMapOfExchangeRates(base);
        double total = expenses.stream().mapToDouble(e -> e.getAmount() / rates.get(e.getCurrency())).sum();
        if (total != 0) {
            totalDto.setTotal(total);
            totalDto.setCurrency(base);
        }
        return totalDto;
    }
}
