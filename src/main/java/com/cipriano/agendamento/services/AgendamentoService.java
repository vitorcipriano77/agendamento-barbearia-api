package com.cipriano.agendamento.services;

import com.cipriano.agendamento.dto.AgendamentoRequestDTO;
import com.cipriano.agendamento.dto.AgendamentoResponseDTO;
import com.cipriano.agendamento.exception.ConflitoHorarioException;
import com.cipriano.agendamento.exception.ResourceNotFoundException;
import com.cipriano.agendamento.infrastructure.entity.Agendamento;
import com.cipriano.agendamento.infrastructure.entity.Barbeiro;
import com.cipriano.agendamento.repository.AgendamentoRepository;
import com.cipriano.agendamento.repository.BarbeiroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final BarbeiroRepository barbeiroRepository;

    public List<AgendamentoResponseDTO> listarTodos() {
        return agendamentoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public AgendamentoResponseDTO buscarPorId(Long id) {
        return toResponse(buscarAgendamentoOuLancar(id));
    }

    public AgendamentoResponseDTO criar(AgendamentoRequestDTO dto) {
        Barbeiro barbeiro = buscarBarbeiroOuLancar(dto.barbeiroId());
        validarConflito(barbeiro, dto.dataHoraAgendamento(), null);

        Agendamento agendamento = new Agendamento();
        agendamento.setBarbeiro(barbeiro);
        agendamento.setDataHoraAgendamento(dto.dataHoraAgendamento());

        return toResponse(agendamentoRepository.save(agendamento));
    }

    public AgendamentoResponseDTO atualizar(Long id, AgendamentoRequestDTO dto) {
        Agendamento agendamento = buscarAgendamentoOuLancar(id);
        Barbeiro barbeiro = buscarBarbeiroOuLancar(dto.barbeiroId());
        validarConflito(barbeiro, dto.dataHoraAgendamento(), id);

        agendamento.setBarbeiro(barbeiro);
        agendamento.setDataHoraAgendamento(dto.dataHoraAgendamento());

        return toResponse(agendamentoRepository.save(agendamento));
    }

    public void deletar(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agendamento não encontrado com id: " + id);
        }
        agendamentoRepository.deleteById(id);
    }

    private void validarConflito(Barbeiro barbeiro, LocalDateTime inicio, Long idIgnorar) {
        LocalDateTime fim = inicio.plusHours(1);

        boolean conflito = idIgnorar == null
                ? agendamentoRepository.existsByBarbeiroAndDataHoraAgendamentoBetween(barbeiro, inicio, fim)
                : agendamentoRepository.existsByBarbeiroAndDataHoraAgendamentoBetweenAndIdNot(barbeiro, inicio, fim, idIgnorar);

        if (conflito) {
            throw new ConflitoHorarioException("O horário já está ocupado para esse barbeiro.");
        }
    }

    private Agendamento buscarAgendamentoOuLancar(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com id: " + id));
    }

    private Barbeiro buscarBarbeiroOuLancar(Long id) {
        return barbeiroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Barbeiro não encontrado com id: " + id));
    }

    private AgendamentoResponseDTO toResponse(Agendamento a) {
        return new AgendamentoResponseDTO(
                a.getId(),
                a.getBarbeiro().getId(),
                a.getBarbeiro().getNome(),
                a.getDataHoraAgendamento()
        );
    }
}