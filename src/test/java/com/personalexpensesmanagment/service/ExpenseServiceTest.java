package com.personalexpensesmanagment.service;

import com.personalexpensesmanagment.dto.ExpenseDto;
import com.personalexpensesmanagment.mapper.ExpenseMapper;
import com.personalexpensesmanagment.mapper.ExpenseMapperImpl;
import com.personalexpensesmanagment.model.Expense;
import com.personalexpensesmanagment.model.ExpenseHelper;
import com.personalexpensesmanagment.repository.ExpenseRepository;
import com.personalexpensesmanagment.util.ExchangeRatesUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest extends ExpenseHelper {

    @InjectMocks private ExpenseService expenseService;

    @Mock private ExpenseRepository expenseRepository;

    @Spy private ExchangeRatesUtil exchangeRatesUtil = new ExchangeRatesUtil();
    @Spy private ExpenseMapper expenseMapper = new ExpenseMapperImpl();

    @Test
    @DisplayName("create expense should return same expense")
    void createExpenseTest() {
        ExpenseDto testExpenseDto = createTestExpenseDto();

        Expense expense = createTestExpense();
        when(expenseRepository.save(any())).thenReturn(expense);
        ExpenseDto expenseDto = expenseService.createExpense(testExpenseDto);

        assertThat(expenseDto, allOf(
                hasProperty("amount", equalTo(testExpenseDto.getAmount())),
                hasProperty("currency", equalTo(testExpenseDto.getCurrency())),
                hasProperty("product", equalTo(testExpenseDto.getProduct())),
                hasProperty("date", equalTo(testExpenseDto.getDate()))
        ));
    }

    @Test
    @DisplayName("get expenses should return list of expenses")
    void getExpensesTest() {
        ExpenseDto testExpenseDto = createTestExpenseDto();
        Expense expense = createTestExpense();

        when(expenseRepository.findAll(any(Sort.class))).thenReturn(Collections.singletonList(expense));

        Map<LocalDate, List<ExpenseDto>> expenses = expenseService.getExpenses();

        assertTrue("Size should be equal to 1", expenses.size() == 1);

        List<ExpenseDto> expenseDtos = expenses.get(LocalDate.now());

        assertTrue("Size should be equal to 1", expenseDtos.size() == 1);

        assertThat(expenseDtos.get(0), allOf(
                hasProperty("amount", equalTo(testExpenseDto.getAmount())),
                hasProperty("currency", equalTo(testExpenseDto.getCurrency())),
                hasProperty("product", equalTo(testExpenseDto.getProduct())),
                hasProperty("date", equalTo(testExpenseDto.getDate()))
        ));
    }

    @Test
    @DisplayName("delete expenses by date should return list of expenses by date they deleted")
    void deleteByDateTest() {
        ExpenseDto testExpenseDto = createTestExpenseDto();
        Expense expense = createTestExpense();

        when(expenseRepository.findAllByDate(any())).thenReturn(Collections.singletonList(expense));
        doNothing().when(expenseRepository).deleteAllByDate(any());

        List<ExpenseDto> expenseDtos = expenseService.deleteByDate(LocalDate.now());

        verify(expenseRepository, times(1)).deleteAllByDate(LocalDate.now());

        assertTrue("Size should be equal to 1", expenseDtos.size() == 1);

        assertThat(expenseDtos.get(0), allOf(
                hasProperty("amount", equalTo(testExpenseDto.getAmount())),
                hasProperty("currency", equalTo(testExpenseDto.getCurrency())),
                hasProperty("product", equalTo(testExpenseDto.getProduct())),
                hasProperty("date", equalTo(testExpenseDto.getDate()))
        ));
    }
}
