package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CozinhaService {

    private static final String COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CozinhaRepository repository;

    @Transactional
    public Cozinha salvar(final Cozinha cozinha) {
        return repository.save(cozinha);
    }

    @Transactional
    public void excluir(final Long id) {
        try {
            repository.deleteById(id);
            repository.flush();
        } catch (final DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(
                    String.format(COZINHA_EM_USO, id));
        } catch (final EmptyResultDataAccessException ex) {
            throw new CozinhaNaoEncontradaException(id);
        }
    }

    public Cozinha buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }

}
