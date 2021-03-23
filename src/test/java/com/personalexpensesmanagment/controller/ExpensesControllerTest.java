package com.personalexpensesmanagment.controller;

import com.personalexpensesmanagment.dto.ExpenseDto;
import com.personalexpensesmanagment.dto.TotalDto;
import com.personalexpensesmanagment.service.ExpenseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ExpensesController.class)
@Import(ExpensesController.class)
@DisplayName("ExpensesController")
public class ExpensesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseServiceMock;

    @Test
    @DisplayName("create expense should return 201 when json is ok")
    public void createExpenseWhenJsonIsOk() throws Exception {
        String expenseClient = "{\n" +
                "\t\"id\": 0,\n" +
                "\t\"date\": \"2021-03-21\",\n" +
                "\t\"amount\": 65.0,\n" +
                "\t\"currency\": \"UAH\",\n" +
                "\t\"product\": \"Camel Blue\"\n" +
                "}";
        mockMvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(expenseClient))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("create expense should return 400 when missed parameters")
    public void createExpenseWhenMissedParametrs() throws Exception {
        String expenseClient = "{\n" +
                "}";
        mockMvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(expenseClient))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(3)))
                .andExpect(jsonPath("$.errors", containsInAnyOrder("product: Can't be null", "date: Can't be null", "currency: Can't be null")));
    }

    @Test
    @DisplayName("get expenses should return 200 and empty map when db is empty")
    public void getExpensesWhenItsEmpty() throws Exception {
        mockMvc.perform(get("/expenses"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("get expenses should return 200 and list grouped and sorted by date")
    public void getExpensesWhenItFilled() throws Exception {
        LocalDate firstDate = LocalDate.of(2021, 4, 22);
        LocalDate secondDate = LocalDate.of(2021, 4, 27);
        ExpenseDto salmon = new ExpenseDto(1, firstDate, 12, "USD", "Salmon");
        ExpenseDto beer = new ExpenseDto(2, secondDate, 12, "EUR", "Beer");
        ExpenseDto sweets = new ExpenseDto(3, secondDate, 12, "UAH", "Sweets");
        Map<LocalDate, List<ExpenseDto>> expenses = new HashMap<>();
        expenses.put(firstDate, List.of(salmon));
        expenses.put(secondDate, List.of(beer, sweets));
        Mockito.when(expenseServiceMock.getExpenses())
                .thenReturn(expenses);
        mockMvc.perform(get("/expenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.2021-04-22").isArray())
                .andExpect(jsonPath("$.2021-04-22", hasSize(1)))
                .andExpect(jsonPath("$.2021-04-22[0].id").value(salmon.getId()))
                .andExpect(jsonPath("$.2021-04-22[0].date").value(salmon.getDate().toString()))
                .andExpect(jsonPath("$.2021-04-22[0].amount").value(salmon.getAmount()))
                .andExpect(jsonPath("$.2021-04-22[0].currency").value(salmon.getCurrency()))
                .andExpect(jsonPath("$.2021-04-22[0].product").value(salmon.getProduct()))
                .andExpect(jsonPath("$.2021-04-27").isArray())
                .andExpect(jsonPath("$.2021-04-27", hasSize(2)))
                .andExpect(jsonPath("$.2021-04-27[0].id").value(beer.getId()))
                .andExpect(jsonPath("$.2021-04-27[0].date").value(beer.getDate().toString()))
                .andExpect(jsonPath("$.2021-04-27[0].amount").value(beer.getAmount()))
                .andExpect(jsonPath("$.2021-04-27[0].currency").value(beer.getCurrency()))
                .andExpect(jsonPath("$.2021-04-27[0].product").value(beer.getProduct()))
                .andExpect(jsonPath("$.2021-04-27[1].id").value(sweets.getId()))
                .andExpect(jsonPath("$.2021-04-27[1].date").value(sweets.getDate().toString()))
                .andExpect(jsonPath("$.2021-04-27[1].amount").value(sweets.getAmount()))
                .andExpect(jsonPath("$.2021-04-27[1].currency").value(sweets.getCurrency()))
                .andExpect(jsonPath("$.2021-04-27[1].product").value(sweets.getProduct()));
    }

    @Test
    @DisplayName("delete expenses should return 400 when without parameter")
    public void deleteExpensesWithoutParam() throws Exception {
        mockMvc.perform(delete("/expenses"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("delete expenses should return 201 and empty map when db is empty")
    public void deleteExpensesWhenItsEmpty() throws Exception {
        mockMvc.perform(delete("/expenses")
                .param("date", "2021-04-27"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("delete should return 200 and a list with deleted elements")
    public void deleteExpensesWhenItsFilled() throws Exception {
        LocalDate firstDate = LocalDate.of(2021, 4, 22);
        ExpenseDto salmon = new ExpenseDto(1, firstDate, 12, "USD", "Salmon");

        List<ExpenseDto> expenses = new LinkedList<>();
        expenses.add(salmon);

        Mockito.when(expenseServiceMock.deleteByDate(firstDate))
                .thenReturn(expenses);

        mockMvc.perform(delete("/expenses")
                .param("date", "2021-04-22"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(salmon.getId()))
                .andExpect(jsonPath("$[0].date").value(salmon.getDate().toString()))
                .andExpect(jsonPath("$[0].amount").value(salmon.getAmount()))
                .andExpect(jsonPath("$[0].currency").value(salmon.getCurrency()))
                .andExpect(jsonPath("$[0].product").value(salmon.getProduct()));
    }

    @Test
    @DisplayName("get total should return 400 when without parameter")
    public void getTotalWithoutParam() throws Exception {
        Mockito.when(expenseServiceMock.getTotal(""))
                .thenReturn(new TotalDto());
        mockMvc.perform(get("/total"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("get total should return 200 when with parameter")
    public void getTotalWithParam() throws Exception {
        Mockito.when(expenseServiceMock.getTotal(""))
                .thenReturn(new TotalDto());
        mockMvc.perform(get("/total")
                .param("base", "UAH"))
                .andExpect(status().isOk());
    }
}
