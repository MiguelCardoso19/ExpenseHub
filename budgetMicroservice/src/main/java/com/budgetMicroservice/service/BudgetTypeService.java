package com.budgetMicroservice.service;

import com.budgetMicroservice.dto.BudgetTypeDTO;
import com.budgetMicroservice.exception.BudgetSubtypeNotFoundException;
import com.budgetMicroservice.exception.BudgetTypeAlreadyExistsException;
import com.budgetMicroservice.exception.BudgetTypeNotFoundException;
import com.budgetMicroservice.model.BudgetType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BudgetTypeService {
    BudgetTypeDTO createBudgetType(BudgetTypeDTO budgetTypeDTO) throws BudgetTypeAlreadyExistsException;
    BudgetTypeDTO updateBudgetType(BudgetTypeDTO budgetTypeDTO) throws BudgetTypeNotFoundException, BudgetTypeAlreadyExistsException;
    void deleteBudgetType(UUID id) throws BudgetTypeNotFoundException;
    BudgetTypeDTO findBudgetTypeDTOById(UUID id) throws BudgetTypeNotFoundException, BudgetSubtypeNotFoundException;
    Page<BudgetTypeDTO> findAllBudgetTypes(Pageable pageable);
    BudgetType findBudgetTypeEntityById(UUID id) throws BudgetSubtypeNotFoundException;
    void save(BudgetType budgetType);
}
