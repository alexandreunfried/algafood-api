package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.mapper.CidadeMapper;
import com.algaworks.algafood.api.dto.CidadeDTO;
import com.algaworks.algafood.api.dto.input.CidadeInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository repository;

    @Autowired
    private CidadeService service;

    @Autowired
    private CidadeMapper mapper;

    @ApiOperation("Lista as cidades")
    @GetMapping
    public List<CidadeDTO> listar() {
        return mapper.toDTO(repository.findAll());
    }

    @ApiOperation("Busca uma cidade por ID")
    @GetMapping("/{id}")
    public CidadeDTO buscar(
            @ApiParam(value = "Id de uma cidade", example = "1") @PathVariable Long id) {
        return mapper.toDTO(service.buscarOuFalhar(id));
    }

    @ApiOperation("Cadastra uma cidade")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade")
            @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = mapper.toDomainObject(cidadeInput);
            return mapper.toDTO(service.salvar(cidade));
        } catch (EstadoNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @ApiOperation("Atualiza uma cidade por ID")
    @PutMapping("/{id}")
    public CidadeDTO atualizar(
            @ApiParam(value = "ID de uma cidade", example = "1")
            @PathVariable final Long id,
            @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
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

    @ApiOperation("Exclui uma cidade por ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    private void remover(
            @ApiParam(value = "ID de uma cidade", example = "1")
            @PathVariable Long id) {
        service.excluir(id);
    }

}
