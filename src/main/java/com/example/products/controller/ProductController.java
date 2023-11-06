package com.example.products.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.products.dtos.ProductRecordDto;
import com.example.products.models.ProductModel;
import com.example.products.repositories.ProductRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;
	
	@PostMapping
	//@valid para validar os metodos de autenticação da classe dto
	public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto){
		//posso usar o var para nao precisar colocar a classe no lado esquerdo e sim somente depois do new
		var productModel = new ProductModel();
		//esse metodo recebe o que vai ser convertido e o tipo que ira realizar a conversao ou seja, o dto vai ser convertido em model
		BeanUtils.copyProperties(productRecordDto, productModel);
		//se ocorreu bem ele retorna o status created no corpo(body) retorna o que foi criado e salva na base de dados pelo productRepository
		return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
	}
	
	@GetMapping
	public ResponseEntity<List<ProductModel>> getAllProducts(){
		//o retorno será um response entity, porém no corpo tera uma lista de produtos
		return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
	}
	
	//apresenta um unico produto passando o id
	@GetMapping("/{id}")
	public ResponseEntity<Object> getOneProducts(@PathVariable(value="id") UUID id){
		//percorre a nase de dados para verificar se existe esse id
		Optional<ProductModel> productO= productRepository.findById(id);
		//se for vazio nao encontrado
		if(productO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
		//se nao return o product
		return ResponseEntity.status(HttpStatus.OK).body(productO.get());	
	}
	
	//atualizar as informações
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateProduct(@PathVariable(value="id") UUID id, 
	@RequestBody @Valid ProductRecordDto productRecordDto){
		Optional<ProductModel> productO = productRepository.findById(id);
		if(productO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
		var productModel = productO.get();
		//metodo put
		BeanUtils.copyProperties(productRecordDto, productModel);
		return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable(value="id") UUID id){
		//percorre a nase de dados para verificar se existe esse id
		Optional<ProductModel> productO= productRepository.findById(id);
		//se for vazio nao encontrado
		if(productO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
		//deletando do banco de dados
		productRepository.delete(productO.get());
		return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");	
	}
	
	
}
