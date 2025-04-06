package com.intern.backendettaba.repositories;

import com.intern.backendettaba.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT u from User u where u.email = ?1")
    Optional<User> findUsersByEmail(String email);


    Boolean existsByEmail(String email);

    User findByEmailAndPassword(String email, String password);
}
