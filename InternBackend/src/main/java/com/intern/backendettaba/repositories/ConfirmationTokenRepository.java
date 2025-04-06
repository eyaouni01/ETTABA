package com.intern.backendettaba.repositories;

import com.intern.backendettaba.entities.ConfirmationToken;
import com.intern.backendettaba.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("confirmationTokenRepository")
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken);

    Boolean existsByConfirmationToken(String Token);

    Boolean existsByUser(User user);

    void deleteByUser_Email(String email);
}