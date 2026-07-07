package com.cipriano.agendamento.repository;

import com.cipriano.agendamento.infrastructure.entity.Agendamento;
import com.cipriano.agendamento.infrastructure.entity.Barbeiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    boolean existsByBarbeiroAndDataHoraAgendamento(Barbeiro barbeiro, LocalDateTime dataHoraAgendamento);

    boolean existsByBarbeiroAndDataHoraAgendamentoBetween(Barbeiro barbeiro, LocalDateTime inicio, LocalDateTime fim);

    boolean existsByBarbeiroAndDataHoraAgendamentoBetweenAndIdNot(Barbeiro barbeiro, LocalDateTime inicio, LocalDateTime fim, Long id);
}