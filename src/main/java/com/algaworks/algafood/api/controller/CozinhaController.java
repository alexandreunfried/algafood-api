package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.CozinhaDTO;
import com.algaworks.algafood.api.dto.input.CozinhaInput;
import com.algaworks.algafood.api.mapper.CozinhaMapper;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository repository;

    @Autowired
    private CozinhaService service;

    @Autowired
    private CozinhaMapper mapper;

    @GetMapping
    public Page<CozinhaDTO> listar(@PageableDefault(size = 20) Pageable pageable) {
        Page<Cozinha> cozinhasPage = repository.findAll(pageable);

        List<CozinhaDTO> cozinhasDto = mapper.toDTO(cozinhasPage.getContent());

        return new PageImpl<>(cozinhasDto, pageable,
                cozinhasPage.getTotalElements());
    }

    @GetMapping("/{id}")
    public CozinhaDTO buscar(@PathVariable final Long id) {
        return mapper.toDTO(service.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO adicionar(@RequestBody @Valid final CozinhaInput cozinhaInput) {
        Cozinha cozinha = mapper.toDomainObject(cozinhaInput);
        return mapper.toDTO(service.salvar(cozinha));
    }

    @PutMapping("/{id}")
    public CozinhaDTO atualizar(
            @PathVariable final Long id,
            @RequestBody @Valid final CozinhaInput cozinhaInput
    ) {
        Cozinha cozinhaAtual = service.buscarOuFalhar(id);
        mapper.copyToDomainObject(cozinhaInput, cozinhaAtual);
        return mapper.toDTO(service.salvar(cozinhaAtual));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remover(@PathVariable final Long id) {
        service.excluir(id);
    }

}
