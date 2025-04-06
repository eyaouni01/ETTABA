package com.intern.backendettaba.controllers;

import com.intern.backendettaba.entities.ConfirmationToken;
import com.intern.backendettaba.entities.Token;
import com.intern.backendettaba.entities.User;
import com.intern.backendettaba.repositories.ConfirmationTokenRepository;
import com.intern.backendettaba.repositories.UserRepository;
import com.intern.backendettaba.services.EmailService;
import com.intern.backendettaba.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService ;


    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

    private final UserRepository userRepository;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final PasswordEncoder bcryptEncoder;

    @Value("${spring.mail.username}") private String sender;

    String loginurl="http://localhost:4200/login";

    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public ModelAndView displayForgotPasswordPage() {
        return new ModelAndView("forgotPassword");
    }

    // Process form submission from forgotPassword page
    @Transactional
    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public String processForgotPasswordForm(@RequestParam("email") String userEmail, HttpServletRequest request) {

        // Lookup user in database by e-mail
        Optional<User> optional = emailService.findUserByEmail(userEmail);

        String status="";
        if (optional.isEmpty()) {
            status="Il n'y a pas de compte pour ce e-mail address.";
        } else {

            // Generate random 36-character string token for reset password
            User user = optional.get();

            // Save token to database

            // Email message

            if(confirmationTokenRepository.existsByUser(user)){
                System.out.println("the user exists!!");
                confirmationTokenRepository.deleteByUser_Email(userEmail);
                status="Code déjà envoyé à ce e-mail address.";
                return status;
            }
            /////
            else {
                ConfirmationToken confirmationToken = new ConfirmationToken(user);

                confirmationTokenRepository.save(confirmationToken);

            ////
            String appUrl = request.getScheme() + "://" + request.getServerName()+":4200";
            ///
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Changement de mot de passe");
            mailMessage.setText("Salutations,\n Vous avez envoyé une requête de récupération de votre compte pour l'application ETTABA.\n Pour changer votre mot de passe, veuillez cliquer sur le lien ci-dessous:\n" + appUrl
                    + "/reset?token=" + confirmationToken.getConfirmationToken()+"\n\n Si vous n'avez pas envoyé cette requête alors, veuillez ignorer ce message.\n\n Cher utilisateur, à nos sentiments cordiaux.");
            emailService.sendEmail(mailMessage);
        }

        }
        return status;

    }

    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String tokenq) {

        //Optional<User> user = emailService.fetchUserByOptToken(token);
        Optional<ConfirmationToken> token = confirmationTokenRepository.findByConfirmationToken(tokenq);


        if (token.isPresent()) { // Token found in DB
            modelAndView.addObject("resetToken", tokenq);
        } else { // Token not found in DB
            modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
        }

        modelAndView.setViewName("resetPassword");
        return modelAndView;
    }

    // Process reset password form
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public String setNewPassword( @RequestParam Map<String, String> requestParams) {

        // Find the user associated with the reset token
        //Optional<User> user = emailService.fetchUserByOptToken(requestParams.get("token"));
        Optional<ConfirmationToken> token = confirmationTokenRepository.findByConfirmationToken(requestParams.get("token"));


        String status="";
        // This should always be non-null but we check just in case
        if (token.isPresent()) {

            User resetUser = token.get().getUserEntity();
            ConfirmationToken confirmationToken=token.get();

            LocalDateTime tokenCreationDate = token.get().getCreatedDate();
            if (isTokenExpired(tokenCreationDate)) {
                status="Oops! Token Expired.";
                return status;

            }

            // Set new password
            resetUser.setPassword(bcryptEncoder.encode(requestParams.get("password")));
            // Save user
            userRepository.save(resetUser);

            // Set the reset token to null so it cannot be used again
            confirmationToken.setConfirmationToken(null);
            confirmationToken.setCreatedDate(null);

            confirmationTokenRepository.save(confirmationToken);

            status="You have successfully reset your password.  You may now login.";
            return status;

        } else {
            status="Oops!  This is an invalid password reset link.";
        }

        return status;
    }

    // Going to reset page without a token redirects to login page
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
        return new ModelAndView("redirect:"+loginurl);
    }

    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }
}
