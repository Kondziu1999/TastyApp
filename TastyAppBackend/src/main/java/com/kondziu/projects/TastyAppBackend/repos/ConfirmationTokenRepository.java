package com.kondziu.projects.TastyAppBackend.repos;

import com.kondziu.projects.TastyAppBackend.models.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken,String> {

    //a bit foolish name but works
    Optional<ConfirmationToken> findConfirmationTokenByConfirmationToken(String confirmationToken);
}
