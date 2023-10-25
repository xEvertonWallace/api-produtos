package com.example.produtos.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.produtos.dtos.ProductRecordDto;
import com.example.produtos.models.ProductModel;
import com.example.produtos.repositories.ProductRepository;

import jakarta.validation.Valid;

@RestController
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;
	
	@PostMapping("/products")
	//@valid para validar os metodos de autenticação da classe dto
	public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto){
		//posso usar o var para nao precisar colocar a classe no lado esquerdo e sim somente depois do new
		var productModel = new ProductModel();
		//esse metodo recebe o que vai ser convertido e o tipo que ira realizar a conversao ou seja, o dto vai ser convertido em model
		BeanUtils.copyProperties(productRecordDto, productModel);
		//se ocorreu bem ele retorna o status created no corpo(body) retorna o que foi criado e salva na base de dados pelo productRepository
		return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
	}
}
