package cr.ac.ucenfotec.waddle.beacon.rest.notification;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.notification.Notification;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.User;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/notify")
public class NotificationController {
    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private Notification notification;

    @PutMapping("/{id}")
    public Optional<User> updateNotification(@PathVariable Long id, @RequestBody User user) {

        Optional<User> ModUser = UserRepository.findById(id);

        return ModUser

                .map(existingUser -> {
                    if (user.isInitialNotify()!=existingUser.isInitialNotify()){
                        existingUser.setInitialNotify(user.isInitialNotify());
                    }
                    if (user.isFinalNotify()!=existingUser.isFinalNotify()){
                        existingUser.setFinalNotify(user.isFinalNotify());
                    }
                    if (user.getNotificationOption()!=null){
                        existingUser.setNotificationOption(user.getNotificationOption());
                    }
                    return UserRepository.save(existingUser);
                });
    }
    @PostMapping
    public void sendNotification() {
    notification.checkNotification();
    }
}


