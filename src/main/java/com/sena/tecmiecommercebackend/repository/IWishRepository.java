package com.sena.tecmiecommercebackend.repository;

import com.sena.tecmiecommercebackend.repository.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWishRepository extends JpaRepository<Wish, Integer> {
    List<Wish> findAllByUserIdOrderByCreatedDateDesc(Long userId);

}
