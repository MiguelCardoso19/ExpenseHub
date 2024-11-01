package com.budgetMicroservice.service;

import com.budgetMicroservice.dto.BudgetSubtypeDTO;
import com.budgetMicroservice.exception.BudgetSubtypeAlreadyExistsException;
import com.budgetMicroservice.exception.BudgetSubtypeNotFoundException;
import com.budgetMicroservice.exception.BudgetTypeNotFoundException;
import com.budgetMicroservice.model.BudgetSubtype;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BudgetSubtypeService {
    BudgetSubtypeDTO addSubtypeToBudget(BudgetSubtypeDTO budgetSubtypeDTO) throws BudgetTypeNotFoundException, BudgetSubtypeAlreadyExistsException, BudgetSubtypeNotFoundException;
    BudgetSubtypeDTO updateBudgetSubtype(BudgetSubtypeDTO budgetSubtypeDTO) throws BudgetSubtypeNotFoundException, BudgetSubtypeAlreadyExistsException;
    void deleteBudgetSubtype(UUID subtypeId) throws BudgetSubtypeNotFoundException;
    BudgetSubtypeDTO findBudgetSubtypeDTOById(UUID subtypeId) throws BudgetSubtypeNotFoundException;
    Page<BudgetSubtypeDTO> findAllBudgetSubtypes(Pageable pageable);
    BudgetSubtype findBudgetSubtypeEntityById(UUID subtypeId) throws BudgetSubtypeNotFoundException;
    void save(BudgetSubtype subtype);
}
