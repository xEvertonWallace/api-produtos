package com.example.produtos.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/products")
	public ResponseEntity<List<ProductModel>> getAllProducts(){
		//o retorno será um response entity, porém no corpo tera uma lista de produtos
		return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
	}
	
	//apresenta um unico produto passando o id
	@GetMapping("/products/{id}")
	public ResponseEntity<Object> getOneProducts(@PathVariable(value="id") UUID id){
		//percorre a nase de dados para verificar se existe esse id
		Optional<ProductModel> productID = productRepository.findById(id);
		//se for vazio nao encontrado
		if(productID.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found.");
		}
		//se nao return o product
		return ResponseEntity.status(HttpStatus.OK).body(productID.get());
		
		
	}
}
