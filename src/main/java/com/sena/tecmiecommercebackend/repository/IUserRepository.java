package com.sena.tecmiecommercebackend.repository;

import com.sena.tecmiecommercebackend.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
