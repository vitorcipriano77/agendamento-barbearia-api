package com.cipriano.agendamento.dto;

import java.time.LocalDateTime;

public record AgendamentoResponseDTO(
        Long id,
        Long barbeiroId,
        String nomeBarbeiro,
        LocalDateTime dataHoraAgendamento
) {}