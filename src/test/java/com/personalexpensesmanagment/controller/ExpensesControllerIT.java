package com.personalexpensesmanagment.controller;

import com.personalexpensesmanagment.PersonalExpensesManagmentApplication;
import com.personalexpensesmanagment.model.Expense;
import com.personalexpensesmanagment.model.ExpenseHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = PersonalExpensesManagmentApplication.class,
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureJsonTesters
public class ExpensesControllerIT extends ExpenseHelper {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JacksonTester<Expense> json;

    @Test
    @DisplayName("create expense should return 201 when json is ok")
    public void testAddExpense() throws IOException {
        Expense expense = createTestExpense();
        JsonContent<Expense> testExpenseJson = json.write(expense);

        ResponseEntity<String> responseEntity = this.testRestTemplate
                .postForEntity("http://localhost:" + port + "/expenses", expense, String.class);
        assertEquals(201, responseEntity.getStatusCodeValue());

        assertThat(testExpenseJson).isEqualToJson(responseEntity.getBody());

    }

}
