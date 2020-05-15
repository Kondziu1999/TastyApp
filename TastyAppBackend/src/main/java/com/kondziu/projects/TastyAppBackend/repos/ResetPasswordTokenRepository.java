package com.kondziu.projects.TastyAppBackend.repos;

import com.kondziu.projects.TastyAppBackend.models.ConfirmationToken;
import com.kondziu.projects.TastyAppBackend.models.ResetPasswordToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ResetPasswordTokenRepository extends CrudRepository<ResetPasswordToken,Long> {

    Optional<ResetPasswordToken> findResetPasswordTokenByResetPasswordToken(String resetPasswordToken);
}
