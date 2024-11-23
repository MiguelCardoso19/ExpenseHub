package com.portalMicroservice.dto.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(name = "StripeCardTokenDTO", description = "DTO containing details for Stripe card token generation.")
public class StripeCardTokenDTO {

    @Schema(description = "Correlation ID to track the request",
            example = "123e4567-e89b-12d3-a456-426614174000",
            nullable = true)
    private UUID correlationId;

    @NotNull
    @NotEmpty
    @Schema(description = "Card number (no spaces or dashes)",
            example = "4242424242424242",
            required = true)
    private String cardNumber;

    @NotNull
    @NotEmpty
    @Schema(description = "Expiration month of the card",
            example = "12",
            required = true)
    private String expMonth;

    @NotNull
    @NotEmpty
    @Schema(description = "Expiration year of the card",
            example = "2025",
            required = true)
    private String expYear;

    @NotNull
    @NotEmpty
    @Schema(description = "CVC (Card Verification Code) of the card",
            example = "123",
            required = true)
    private String cvc;

    @Email
    @Schema(description = "Email associated with the card",
            example = "user@example.com",
            required = true)
    private String email;

    @Schema(description = "Token generated by Stripe after successful card processing",
            example = "tok_visa",
            nullable = true)
    private String token;

    @Schema(description = "Indicates whether the card tokenization was successful",
            example = "true",
            nullable = true)
    private boolean success;
}
