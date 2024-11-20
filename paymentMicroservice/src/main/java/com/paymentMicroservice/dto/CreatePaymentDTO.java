package com.paymentMicroservice.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import com.paymentMicroservice.enumerators.MovementType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePaymentDTO {

    @NotNull
    String currency;

    @NotNull
    String paymentMethodId;

    @Email
    String receiptEmail;

    @NotNull
    String description;

    @NotNull
    @SerializedName("items")
    CreatePaymentItemDTO[] items;

    @NotNull
    @Enumerated(EnumType.STRING)
    MovementType movementType;

    private UUID correlationId;
}