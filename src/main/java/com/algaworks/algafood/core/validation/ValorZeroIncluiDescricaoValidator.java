package com.algaworks.algafood.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
        valorField = constraintAnnotation.valorField();
        descricaoField = constraintAnnotation.descricaoField();
        descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object objetoValidacao, ConstraintValidatorContext constraintValidatorContext) {
        boolean valido = true;

        try {
            BigDecimal valor = (BigDecimal) BeanUtils
                    .getPropertyDescriptor(objetoValidacao.getClass(), valorField)
                    .getReadMethod()
                    .invoke(objetoValidacao);

            String descricao = (String) BeanUtils
                    .getPropertyDescriptor(objetoValidacao.getClass(), descricaoField)
                    .getReadMethod()
                    .invoke(objetoValidacao);

            if (BigDecimal.ZERO.equals(valor) && descricao != null) {
                valido = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
            }

        } catch (Exception ex) {
            throw new ValidationException(ex);
        }

        return valido;
    }
}
