package com.personalexpensesmanagment.repository;

import com.personalexpensesmanagment.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    void deleteAllByDate(LocalDate date);

    List<Expense> findAllByDate(LocalDate date);
}
