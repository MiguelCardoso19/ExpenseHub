package com.paymentMicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CreatePaymentResponseDTO {
    private String paymentIntentId;
    private UUID correlationId;
}