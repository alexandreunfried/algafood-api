package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.controller.openapi.CidadeControllerOpenApi;
import com.algaworks.algafood.api.dto.CidadeDTO;
import com.algaworks.algafood.api.dto.input.CidadeInput;
import com.algaworks.algafood.api.mapper.CidadeMapper;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository repository;

    @Autowired
    private CidadeService service;

    @Autowired
    private CidadeMapper mapper;

    @GetMapping
    public List<CidadeDTO> listar() {
        return mapper.toDTO(repository.findAll());
    }

    @GetMapping("/{id}")
    public CidadeDTO buscar(
            @PathVariable Long id) {
        return mapper.toDTO(service.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO adicionar(
            @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = mapper.toDomainObject(cidadeInput);
            return mapper.toDTO(service.salvar(cidade));
        } catch (EstadoNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public CidadeDTO atualizar(
            @PathVariable final Long id,
            @RequestBody @Valid CidadeInput cidadeInput
    ) {
        try {
            Cidade cidadeAtual = service.buscarOuFalhar(id);
            mapper.copyToDomainObject(cidadeInput, cidadeAtual);
            return mapper.toDTO(service.salvar(cidadeAtual));
        } catch (EstadoNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remover(
            @PathVariable Long id) {
        service.excluir(id);
    }

}
