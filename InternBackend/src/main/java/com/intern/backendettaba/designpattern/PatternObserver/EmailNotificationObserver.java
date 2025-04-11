package com.intern.backendettaba.designpattern.PatternObserver;
import com.intern.backendettaba.entities.User;
import com.intern.backendettaba.services.EmailService;
import org.springframework.stereotype.Component;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.http.ResponseEntity;
import com.intern.backendettaba.entities.ConfirmationToken;
import com.intern.backendettaba.repositories.ConfirmationTokenRepository;
@Component
public class EmailNotificationObserver implements Observer {
    private  EmailService emailService; // Injection
    private  ConfirmationTokenRepository confirmationTokenRepository;


    public EmailNotificationObserver(EmailService emailService,
                                     ConfirmationTokenRepository confirmationTokenRepository) {
        this.emailService = emailService;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public void update(String eventType, Object data) {
        if ("USER_REGISTERED".equals(eventType)) {
            try {
                User user = extractUserFromData(data);
                if (user != null && user.getEmail() != null) {
                    sendConfirmationEmail(user);
                }
            } catch (Exception e) {
                System.err.println("Erreur grave dans l'Observer: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private User extractUserFromData(Object data) {
        // Gestion des différents types de données reçues
        if (data instanceof User) {
            return (User) data;
        } else if (data instanceof ResponseEntity<?> responseEntity
                && responseEntity.getBody() instanceof User) {
            return (User) responseEntity.getBody();
        }
        return null;
    }

    private void sendConfirmationEmail(User user) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setSubject("Confirmation d'inscription");


        ConfirmationToken confirmationToken = confirmationTokenRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Token not found for user"));

        mail.setText("Bonjour " + user.getFirstName() + ",\n\n"

                + "To confirm your account, please click here : "   +"http://localhost:8082/confirm-account?token=" + confirmationToken.getConfirmationToken() + "\n\n"
                + "Cordialement,\nL'équipe Example");

        emailService.sendEmail(mail);

        System.out.printf("\n" + """
            ╔════════════════════════════════╗
            ║  EMAIL ENVOYÉ AVEC SUCCÈS      ║
            ╠════════════════════════════════╣
            ║  Destinataire: %-15s ║
            ╚════════════════════════════════╝
            """.formatted(user.getEmail()));
    }

}

