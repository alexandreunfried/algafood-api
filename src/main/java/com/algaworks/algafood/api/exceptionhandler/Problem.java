package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@ApiModel("Problema")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    @ApiModelProperty(example = "400", position = 1)
    private final Integer status;

    @ApiModelProperty(example = "2021-06-27T18:34:48.5904412Z", position = 5)
    private final OffsetDateTime timestamp;

    @ApiModelProperty(example = "https://algafood.com.br/dados-invalidos", position = 10)
    private final String type;

    @ApiModelProperty(example = "Dados inválidos", position = 15)
    private final String title;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",
            position = 20)
    private final String detail;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",
            position = 25)
    private final String userMessage;

    @ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro (opcional)", position = 30)
    private final List<Object> objects;

    @ApiModel("ObjetoProblema")
    @Getter
    @Builder
    public static class Object {

        @ApiModelProperty("preco")
        private final String name;

        @ApiModelProperty("O preço é obrigatório")
        private final String userMessage;

    }

}
