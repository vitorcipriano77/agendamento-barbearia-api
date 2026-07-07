package com.cipriano.agendamento.dto;

import jakarta.validation.constraints.NotBlank;

public record BarbeiroRequestDTO(

        @NotBlank(message = "O nome do barbeiro é obrigatório")
        String nome

) {}