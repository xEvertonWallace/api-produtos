package com.example.products.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//classe para implementar os metodos de get da classe produtos
public record ProductRecordDto(@NotBlank String name,@NotNull BigDecimal value) {
	
}
