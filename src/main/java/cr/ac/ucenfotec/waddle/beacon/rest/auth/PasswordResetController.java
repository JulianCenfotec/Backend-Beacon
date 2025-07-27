package cr.ac.ucenfotec.waddle.beacon.rest.auth;


import cr.ac.ucenfotec.waddle.beacon.logic.entity.auth.PasswordResetRequest;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.auth.PasswordResetService;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/password")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/request-reset")
    public ResponseEntity<?> requestReset(@RequestBody PasswordResetRequest request) {
        try {
            String token = passwordResetService.generateResetToken(request.getEmail());
            passwordResetService.sendResetEmail(request.getEmail(), token);
            return ResponseEntity.ok().body("{\"message\": \"Password reset email sent\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"Error: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest resetRequest) {
        System.out.println("Token: " + resetRequest.getToken());
        System.out.println("New Password: " + resetRequest.getNewPassword());
        try {
            User user = passwordResetService.validatePasswordResetToken(resetRequest.getToken());
            passwordResetService.resetPassword(user, resetRequest.getNewPassword());
            return ResponseEntity.ok().body("{\"message\": \"Password has been reset\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"Error: " + e.getMessage() + "\"}");
        }
    }

}
