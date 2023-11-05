package com.example.products.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.products.models.ProductModel;

@Repository 
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {

//com essa inteface, consigo obter todos os metodos para realizar as operacoes CRUD
}
