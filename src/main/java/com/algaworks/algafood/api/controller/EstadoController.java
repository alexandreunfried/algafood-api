package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.mapper.EstadoMapper;
import com.algaworks.algafood.api.dto.EstadoDTO;
import com.algaworks.algafood.api.dto.input.EstadoInput;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository repository;

    @Autowired
    private EstadoService service;

    @Autowired
    private EstadoMapper mapper;

    @GetMapping
    public List<EstadoDTO> listar() {
        return mapper.toDTO(repository.findAll());
    }

    @GetMapping("/{id}")
    public EstadoDTO buscar(@PathVariable Long id) {
        return mapper.toDTO(service.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private EstadoDTO adicionar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = mapper.toDomainObject(estadoInput);
        return mapper.toDTO(service.salvar(estado));
    }

    @PutMapping("/{id}")
    private EstadoDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid EstadoInput estadoInput
    ) {
        Estado estadoAtual = service.buscarOuFalhar(id);
        mapper.copyToDomainObject(estadoInput, estadoAtual);
        return mapper.toDTO(service.salvar(estadoAtual));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    private void remover(@PathVariable Long id) {
        service.excluir(id);
    }

}
