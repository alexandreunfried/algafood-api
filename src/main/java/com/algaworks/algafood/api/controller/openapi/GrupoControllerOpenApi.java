package com.algaworks.algafood.api.controller.openapi;

import com.algaworks.algafood.api.dto.GrupoDTO;
import com.algaworks.algafood.api.dto.input.GrupoInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

    @ApiOperation("Lista os grupos")
    public List<GrupoDTO> listar();

    @ApiOperation("Busca um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da grupo inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    public GrupoDTO buscar(
            @ApiParam(value = "ID de um grupo", example = "1")
                    Long grupoId);

    @ApiOperation("Cadastra um grupo")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Grupo cadastrado"),
    })
    public GrupoDTO adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo grupo")
                    GrupoInput grupoInput);

    @ApiOperation("Atualiza um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupo atualizado"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    public GrupoDTO atualizar(
            @ApiParam(value = "ID de um grupo", example = "1")
                    Long grupoId,

            @ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados")
                    GrupoInput grupoInput);

    @ApiOperation("Exclui um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Grupo excluído"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    public void remover(
            @ApiParam(value = "ID de um grupo", example = "1")
                    Long grupoId);

}
