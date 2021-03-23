package com.personalexpensesmanagment.api;

import com.personalexpensesmanagment.dto.ExpenseDto;
import com.personalexpensesmanagment.dto.TotalDto;
import com.personalexpensesmanagment.dto.validation.group.OnCreate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Api(tags = "Expense management API")
@ApiResponses({
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@RequestMapping
public interface ExpensesApi {

    @ApiOperation("Create expense")
    @ApiResponse(code = 201, message = "Created", response = ExpenseDto.class)
    @PostMapping(value = "/expenses", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    ExpenseDto createExpense(@RequestBody
                             @Validated(OnCreate.class) ExpenseDto expenseDto);

    @ApiOperation("Get expenses")
    @ApiResponse(code = 200, message = "OK", response = Map.class)
    @GetMapping("/expenses")
    @ResponseStatus(HttpStatus.OK)
    Map<LocalDate, List<ExpenseDto>> getExpenses();

    @ApiOperation("Delete expense by date")
    @ApiResponse(code = 200, message = "OK", response = List.class)
    @DeleteMapping("/expenses")
    @ResponseStatus(HttpStatus.OK)
    List<ExpenseDto> deleteExpenseByDate(@RequestParam
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                 LocalDate date);

    @ApiOperation("Get total with base")
    @ApiResponse(code = 200, message = "OK", response = TotalDto.class)
    @GetMapping("/total")
    @ResponseStatus(HttpStatus.OK)
    TotalDto getTotal(@RequestParam String base);
}
