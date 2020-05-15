package com.kondziu.projects.TastyAppBackend.controllers;

import com.kondziu.projects.TastyAppBackend.exceptions.AppException;
import com.kondziu.projects.TastyAppBackend.exceptions.InvalidConfirmationTokenException;
import com.kondziu.projects.TastyAppBackend.exceptions.UserNotFoundException;
import com.kondziu.projects.TastyAppBackend.models.*;
import com.kondziu.projects.TastyAppBackend.payload.*;
import com.kondziu.projects.TastyAppBackend.repos.ConfirmationTokenRepository;
import com.kondziu.projects.TastyAppBackend.repos.ResetPasswordTokenRepository;
import com.kondziu.projects.TastyAppBackend.repos.RoleRepository;
import com.kondziu.projects.TastyAppBackend.repos.UserRepository;
import com.kondziu.projects.TastyAppBackend.security.JwtTokenProvider;
import com.kondziu.projects.TastyAppBackend.services.PasswordRestoreService;
import com.kondziu.projects.TastyAppBackend.services.RegistrationEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider tokenProvider;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final RegistrationEmailService registrationEmailService;

    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    private final PasswordRestoreService passwordRestoreService;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider, ConfirmationTokenRepository confirmationTokenRepository, RegistrationEmailService registrationEmailService, ResetPasswordTokenRepository resetPasswordTokenRepository, PasswordRestoreService passwordRestoreService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.registrationEmailService = registrationEmailService;
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
        this.passwordRestoreService = passwordRestoreService;
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt,jwtExpirationInMs/1000));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return getBadRequestResponseEntityWithErrorMsg("Username is already taken!");
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return getBadRequestResponseEntityWithErrorMsg("Email Address already in use!");
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        //create confirmation token and store it in db
        ConfirmationToken confirmationToken=new ConfirmationToken(result);
        confirmationTokenRepository.save(confirmationToken);
        //pass it to service
        registrationEmailService.prepareMessageAndSend(confirmationToken.getConfirmationToken(),user.getEmail());

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @PostMapping("/checkCredentials")
    public ResponseEntity<?> checkUsernameAndEmailAvailability(@RequestBody CheckCredentialsPayload payload){
        boolean usernameExists = userRepository.existsByUsername(payload.getUsername());
        boolean emailExists = userRepository.existsByEmail(payload.getEmail());

        //Response is returned always with 200 code since this endpoint is used for register validation
        return ResponseEntity.ok(new CheckCredentialsResponse(!usernameExists,!emailExists));
    }
    
    private ResponseEntity<?> getBadRequestResponseEntityWithErrorMsg(String message){
        return new ResponseEntity<>(new ApiResponse(false, message),
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/confirmEmail/{token}")
    public ResponseEntity<?> confirmEmail(@PathVariable String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findConfirmationTokenByConfirmationToken(token)
                .orElseThrow( () -> new InvalidConfirmationTokenException("token doesnt exist"));
         //throw invalid since it is token error not user
         User user = userRepository.findByEmail(confirmationToken.getUser().getEmail())
                 .orElseThrow( () -> new InvalidConfirmationTokenException("user associated with token doesnt exists") );

         //set confirm email flag to true
         user.setEnabled(true);
         userRepository.save(user);

        //Consider delete token from db since it is not longer need
        return ResponseEntity.ok(confirmationToken);
    }
    @PostMapping(value ={"/resetPassword"})
    public ResponseEntity<?>  resetPassword(@RequestBody ResetPasswordPayload payload){
        //if it is request for sending URL with token via email
        //contains usernameOrEmail  and frontendUrl
        if(payload.getToken()==null){
            User user=userRepository.findByUsernameOrEmail(payload.getUsernameOrEmail(),payload.getUsernameOrEmail())
                    .orElseThrow( () -> new UserNotFoundException("user with given username or email not found") );

            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/api/auth/resetPassword")
                    .buildAndExpand(user.getUsername()).toUri();

            ResetPasswordToken token=new ResetPasswordToken(user);
            resetPasswordTokenRepository.save(token);

            passwordRestoreService.sendRestorePasswordEmail(token.getResetPasswordToken(),user.getEmail(),payload.getFrontendUrl());

            return ResponseEntity.created(location).body(new ApiResponse(true, "Email send successfully"));
        }
        //otherwise it must be request which change password
        //contains usernameOrEmail  token  password
        else {
            ResetPasswordToken token = resetPasswordTokenRepository.findResetPasswordTokenByResetPasswordToken(payload.getToken())
                    .orElseThrow( () -> new InvalidConfirmationTokenException("invalid token") );
            User user=userRepository.findByUsernameOrEmail(payload.getUsernameOrEmail(), payload.getUsernameOrEmail())
                    .orElseThrow( () -> new UserNotFoundException("user with given username or email not found") );

            String password = payload.getPassword();

            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);

            resetPasswordTokenRepository.delete(token);

            return ResponseEntity.ok().body(payload);
        }
    }

}
