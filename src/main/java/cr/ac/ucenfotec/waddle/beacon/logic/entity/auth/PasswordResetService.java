package cr.ac.ucenfotec.waddle.beacon.logic.entity.auth;



import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.User;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordResetService(UserRepository userRepository, JavaMailSender mailSender, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    public String generateResetToken(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("No user found with this email address");
        }

        User user = userOptional.get();
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        userRepository.save(user);

        return token;
    }

    public void sendResetEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("pmoonn@ucenfotec.ac.cr");
        message.setSubject("Solicitud de Nueva Contrasena");
        message.setText("Para restablecer su nueva contrasena ingresa al siguiente enlace:\n"
                + "http://localhost:4200/beacon/reset-password?token=" + token);

        try {
            mailSender.send(message);
        } catch (MailException e) {
            // Log the exception or rethrow it if necessary
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public User validatePasswordResetToken(String token) {
        Optional<User> userOptional = userRepository.findByResetToken(token);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("Invalid token");
        }

        return userOptional.get();
    }

    public void resetPassword(User user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetToken(null);
        userRepository.save(user);
        System.out.println("Password reset for user: " + user.getEmail());
        System.out.println("New encoded password: " + encodedPassword);
    }

    public void sendPasswordChangeConfirmationEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("pmoonn@ucenfotec.ac.cr");
        message.setSubject("Confirmación de Cambio de Contraseña");
        message.setText("Su contraseña ha sido cambiada exitosamente. Si no ha sido usted, por favor contacte con el soporte inmediatamente.");

        try {
            mailSender.send(message);
        } catch (MailException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public void sendEmailChangeNotification(String oldEmail, String newEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(newEmail);
        message.setFrom("pmoonn@ucenfotec.ac.cr");
        message.setSubject("Confirmación de Cambio de Correo Electrónico");
        message.setText("Su correo electrónico ha sido cambiado de " + oldEmail + " a " + newEmail + ". Si no ha sido usted, por favor contacte con el soporte inmediatamente.");

        try {
            mailSender.send(message);
        } catch (MailException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
