package com.cipriano.agendamento.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AgendamentoRequestDTO(

        @NotNull(message = "O ID do barbeiro é obrigatório")
        Long barbeiroId,

        @NotNull(message = "A data e hora são obrigatórias")
        @Future(message = "O agendamento deve ser em uma data futura")
        LocalDateTime dataHoraAgendamento

) {}