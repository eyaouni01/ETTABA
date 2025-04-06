package com.intern.backendettaba.services;

import com.intern.backendettaba.entities.ConfirmationToken;
import com.intern.backendettaba.entities.Image;
import com.intern.backendettaba.entities.User;
import com.intern.backendettaba.repositories.ConfirmationTokenRepository;
import com.intern.backendettaba.repositories.ImageRepository;
import com.intern.backendettaba.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

    private final UserRepository userRepository;


    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final EmailService emailService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public ResponseEntity<User> saveUser(@RequestBody User user){

        log.info("Saving new user with email {}",user.getEmail());
        /*Optional<User> optionalUser=userRepository.findUsersByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            throw new IllegalStateException("already in use");
        }
        return new ResponseEntity<>(user,HttpStatus.CREATED);*/

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalStateException("already in use");
        }

        user.setCreatedAt(LocalDate.now());

        userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8082/confirm-account?token="+confirmationToken.getConfirmationToken());
        emailService.sendEmail(mailMessage);

        System.out.println("Confirmation Token: " + confirmationToken.getConfirmationToken());

        return new ResponseEntity<>(user,HttpStatus.CREATED);

    }

    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken).get();

        User user = userRepository.findUsersByEmail(token.getUserEntity().getEmail()).orElseThrow();
        user.setEnabled(true);
        userRepository.save(user);
        return ResponseEntity.ok("Email verified successfully!");
    }

    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<User> getUserById(Long id){

        User user=userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if(user.getDob() != null) user.setAge(Period.between(user.getDob(),LocalDate.now()).getYears());

        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    public ResponseEntity<User> getUserByEmail(String email){

        User user=userRepository.findUsersByEmail(email).orElseThrow(EntityNotFoundException::new);
        if(user.getDob() != null) user.setAge(Period.between(user.getDob(),LocalDate.now()).getYears());

        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @Transactional
    public void updateUserNameEmail(Long id, String fname, String lname, String email){
        if (id == null) {
            throw new IllegalArgumentException(
                    "ID cannot be null");
        }
        User existingUser = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException(String.valueOf(id)));
        if(fname!=null && fname.length()>0 && !Objects.equals(existingUser.getFirstName(),fname)) {
            existingUser.setFirstName(fname);
        }
        if(lname!=null && lname.length()>0 && !Objects.equals(existingUser.getLastName(),lname)) {
            existingUser.setLastName(lname);
        }
        if(email!=null && email.length()>0 && !Objects.equals(existingUser.getEmail(),email)) {
            Optional<User> optionalUser=userRepository.findUsersByEmail(email);
            if(optionalUser.isPresent()){
                throw new IllegalStateException("already in use");
            }
            existingUser.setEmail(email);
        }
    }

    public ResponseEntity<User> updateUser(Long id, User updatedUser){
        if (id == null) {
            throw new IllegalArgumentException(
                    "ID cannot be null");
        }
        User existingUser = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException(String.valueOf(id)));

        if(Objects.nonNull(updatedUser.getEmail()) && !Objects.equals(updatedUser.getEmail(),existingUser.getEmail())){
            existingUser.setEmail(updatedUser.getEmail());
        }
        if(Objects.nonNull(updatedUser.getFirstName()) && !Objects.equals(updatedUser.getFirstName(),existingUser.getFirstName())){
            existingUser.setFirstName(updatedUser.getFirstName());
        }
        if(Objects.nonNull(updatedUser.getLastName()) && !Objects.equals(updatedUser.getLastName(),existingUser.getFirstName())){
            existingUser.setLastName(updatedUser.getLastName());
        }
        if(Objects.nonNull(updatedUser.getPassword()) && !Objects.equals(updatedUser.getPassword(),existingUser.getPassword())){
            existingUser.setPassword(bcryptEncoder.encode(updatedUser.getPassword()));
        }
        if(Objects.nonNull(updatedUser.getDob()) && !Objects.equals(updatedUser.getDob(),existingUser.getDob())){
            existingUser.setDob(updatedUser.getDob());
        }
        if(Objects.nonNull(updatedUser.getSolde()) && !Objects.equals(updatedUser.getSolde(),existingUser.getSolde())){
            existingUser.setSolde(updatedUser.getSolde());
        }
        if(Objects.nonNull(updatedUser.getPhoneNumber()) && !Objects.equals(updatedUser.getPhoneNumber(),existingUser.getPhoneNumber())){
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        }
        if(Objects.nonNull(updatedUser.getRole()) && !Objects.equals(updatedUser.getRole(),existingUser.getRole())){
            existingUser.setRole(updatedUser.getRole());
        }
        if(Objects.nonNull(updatedUser.getUserImage()) && !Objects.equals(updatedUser.getUserImage(),existingUser.getUserImage())){
            existingUser.setUserImage(updatedUser.getUserImage());
        }

        return ResponseEntity.ok(userRepository.save(existingUser));
    }

    public ResponseEntity<String> deleteUser(Long id){
        boolean exists=userRepository.existsById(id);
        if (!exists){
            throw new IllegalStateException("user by id:"+id+" doesn't exist");
        }
        userRepository.deleteById(id);
        return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
    }


    ///////////////////

    public User fetchUserByEmail(String Email){
        Optional<User> user= userRepository.findUsersByEmail(Email);
        if(user.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }
        return user.get();
    }

    public User fetchUserByToken(String Token){
        Optional<ConfirmationToken> confirmationToken=confirmationTokenRepository.findByConfirmationToken(Token);
        if(confirmationToken.isEmpty()){
            throw new EntityNotFoundException("confirmationToken not found");
        }
        return confirmationToken.get().getUserEntity();
    }

    public Boolean existUser (String matricule) {
        return userRepository.existsByEmail(matricule);
    }

    public Boolean existUserByToken (String token) {
        return confirmationTokenRepository.existsByConfirmationToken(token);
    }

    public ConfirmationToken resetToken(String token) throws IllegalStateException {

        Optional<ConfirmationToken> userOptional = confirmationTokenRepository.findByConfirmationToken(token);

        ConfirmationToken tokenize = userOptional.get();

        tokenize.setConfirmationToken(null);
        tokenize.setCreatedDate(null);

        return confirmationTokenRepository.save(tokenize);

    }
}
