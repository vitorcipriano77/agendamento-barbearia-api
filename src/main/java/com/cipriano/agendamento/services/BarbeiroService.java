package com.cipriano.agendamento.services;

import com.cipriano.agendamento.dto.BarbeiroRequestDTO;
import com.cipriano.agendamento.dto.BarbeiroResponseDTO;
import com.cipriano.agendamento.exception.ResourceNotFoundException;
import com.cipriano.agendamento.infrastructure.entity.Barbeiro;
import com.cipriano.agendamento.repository.BarbeiroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BarbeiroService {

    private final BarbeiroRepository barbeiroRepository;

    public List<BarbeiroResponseDTO> listarTodos() {
        return barbeiroRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public BarbeiroResponseDTO buscarPorId(Long id) {
        return toResponse(buscarOuLancar(id));
    }

    public BarbeiroResponseDTO criar(BarbeiroRequestDTO dto) {
        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setNome(dto.nome());
        return toResponse(barbeiroRepository.save(barbeiro));
    }

    public BarbeiroResponseDTO atualizar(Long id, BarbeiroRequestDTO dto) {
        Barbeiro barbeiro = buscarOuLancar(id);
        barbeiro.setNome(dto.nome());
        return toResponse(barbeiroRepository.save(barbeiro));
    }

    public void deletar(Long id) {
        if (!barbeiroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Barbeiro não encontrado com id: " + id);
        }
        barbeiroRepository.deleteById(id);
    }

    private Barbeiro buscarOuLancar(Long id) {
        return barbeiroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Barbeiro não encontrado com id: " + id));
    }

    private BarbeiroResponseDTO toResponse(Barbeiro b) {
        return new BarbeiroResponseDTO(b.getId(), b.getNome());
    }
}