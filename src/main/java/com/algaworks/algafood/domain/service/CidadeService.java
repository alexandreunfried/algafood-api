package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CidadeService {

    private static final String MSG_CIDADE_EM_USO
            = "Cidade de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CidadeRepository repository;

    @Autowired
    private EstadoService estadoService;

    @Transactional
    public Cidade salvar(final Cidade cidade) {
        final Long estadoId = cidade.getEstado().getId();
        final Estado estado = estadoService.buscarOuFalhar(estadoId);

        cidade.setEstado(estado);

        return repository.save(cidade);
    }

    @Transactional
    public void excluir(final Long id) {
        try {
            repository.deleteById(id);
            repository.flush();
        } catch (final DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, id));
        } catch (final EmptyResultDataAccessException ex) {
            throw new CidadeNaoEncontradaException(id);
        }
    }

    public Cidade buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }

}
