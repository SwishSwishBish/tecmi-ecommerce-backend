package com.sena.tecmiecommercebackend.repository;

import com.sena.tecmiecommercebackend.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long > {
}
