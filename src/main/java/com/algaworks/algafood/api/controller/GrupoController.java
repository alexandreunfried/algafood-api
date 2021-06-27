package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.controller.openapi.GrupoControllerOpenApi;
import com.algaworks.algafood.api.dto.GrupoDTO;
import com.algaworks.algafood.api.dto.input.GrupoInput;
import com.algaworks.algafood.api.mapper.GrupoMapper;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController implements GrupoControllerOpenApi {

    @Autowired
    private GrupoRepository repository;

    @Autowired
    private GrupoService service;

    @Autowired
    private GrupoMapper mapper;

    @GetMapping
    public List<GrupoDTO> listar() {
        List<Grupo> todosGrupos = repository.findAll();

        return mapper.toDTO(todosGrupos);
    }

    @GetMapping("/{id}")
    public GrupoDTO buscar(@PathVariable Long id) {
        Grupo grupo = service.buscarOuFalhar(id);

        return mapper.toDTO(grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO adicionar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = mapper.toDomainObject(grupoInput);

        grupo = service.salvar(grupo);

        return mapper.toDTO(grupo);
    }

    @PutMapping("/{id}")
    public GrupoDTO atualizar(@PathVariable Long id,
                              @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoAtual = service.buscarOuFalhar(id);

        mapper.copyToDomainObject(grupoInput, grupoAtual);

        grupoAtual = service.salvar(grupoAtual);

        return mapper.toDTO(grupoAtual);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.excluir(id);
    }

}
