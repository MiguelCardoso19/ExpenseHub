package com.budgetMicroservice.mapper;

import com.budgetMicroservice.dto.InvoiceDTO;
import com.budgetMicroservice.model.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    Invoice toEntity(InvoiceDTO invoiceDTO);

    @Mapping(target = "movement.budgetType.subtypes", ignore = true)
    @Mapping(target = "movement.subtype.budgetType", ignore = true)
    @Mapping(target = "movement.invoice", ignore = true)
    InvoiceDTO toDTO(Invoice invoice);

    void updateFromDTO(InvoiceDTO invoiceDTO, @MappingTarget Invoice invoice);
}
