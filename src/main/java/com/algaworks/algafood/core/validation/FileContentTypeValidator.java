package com.algaworks.algafood.core.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

	private List<String> allowedContentTypes;

	@Override
	public void initialize(FileContentType constraintAnnotation) {
		allowedContentTypes = Arrays.asList(constraintAnnotation.allowed());
	}

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext constraintValidatorContext) {
		return value == null || allowedContentTypes.contains(value.getContentType());
	}

}
