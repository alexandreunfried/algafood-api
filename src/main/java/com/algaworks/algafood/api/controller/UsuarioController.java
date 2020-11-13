package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.UsuarioDTO;
import com.algaworks.algafood.api.dto.input.SenhaInput;
import com.algaworks.algafood.api.dto.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.dto.input.UsuarioInput;
import com.algaworks.algafood.api.mapper.UsuarioMapper;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioService service;

    @Autowired
    private UsuarioMapper mapper;

    @GetMapping
    public List<UsuarioDTO> listar() {
        List<Usuario> todasUsuarios = repository.findAll();

        return mapper.toDTO(todasUsuarios);
    }

    @GetMapping("/{id}")
    public UsuarioDTO buscar(@PathVariable Long id) {
        Usuario usuario = service.buscarOuFalhar(id);

        return mapper.toDTO(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = mapper.toDomainObject(usuarioInput);
        usuario = service.salvar(usuario);

        return mapper.toDTO(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioDTO atualizar(@PathVariable Long id,
                                @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioAtual = service.buscarOuFalhar(id);
        mapper.copyToDomainObject(usuarioInput, usuarioAtual);
        usuarioAtual = service.salvar(usuarioAtual);

        return mapper.toDTO(usuarioAtual);
    }

    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaInput senha) {
        service.alterarSenha(id, senha.getSenhaAtual(), senha.getNovaSenha());
    }

}
