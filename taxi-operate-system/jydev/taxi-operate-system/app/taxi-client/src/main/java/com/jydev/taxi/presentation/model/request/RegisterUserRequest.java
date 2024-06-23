package com.jydev.taxi.presentation.model.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterUserRequest(

        @NotBlank
        String name
) {
}
