package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstadoService {

    private static final String MSG_ESTADO_EM_USO =
            "Estado de código %d não pode ser removido, pois está em uso";

    @Autowired
    private EstadoRepository repository;

    @Transactional
    public Estado salvar(final Estado estado) {
        return repository.save(estado);
    }

    @Transactional
    public void excluir(final Long id) {
        try {
            repository.deleteById(id);
            repository.flush();
        } catch (final DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_ESTADO_EM_USO, id));
        } catch (final EmptyResultDataAccessException ex) {
            throw new EstadoNaoEncontradoException(id);
        }
    }

    public Estado buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EstadoNaoEncontradoException(id));
    }

}
