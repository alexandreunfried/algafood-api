package com.algaworks.algafood.api.mapper;

import com.algaworks.algafood.api.dto.FotoProdutoDTO;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoDTO toDTO(FotoProduto foto) {
        return modelMapper.map(foto, FotoProdutoDTO.class);
    }

}
