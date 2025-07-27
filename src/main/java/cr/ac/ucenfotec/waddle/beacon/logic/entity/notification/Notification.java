package cr.ac.ucenfotec.waddle.beacon.logic.entity.notification;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.calendario.Calendario;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.calendario.CalendarioPlan;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.calendario.CalendarioService;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.plan.Plan;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.plan.PlanService;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.User;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Component
public class Notification implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private PlanService planService;
    @Autowired
    private CalendarioService calendarioService;
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.checkNotification();
    }

    public void checkNotification() {
        LocalDate today = LocalDate.now();
        List<User> users = UserRepository.findAll();
        for (User user : users) {
            System.out.println(user.getEmail());
            if(user.isFinalNotify()|user.isInitialNotify()){
                List<Calendario> calendario= calendarioService.getFromUserId(user.getId());
                for (Calendario c : calendario) {
                    for (CalendarioPlan cp : c.getCalendarioPlans()) {
                        LocalDate startDate = cp.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        switch (user.getNotificationOption()){
                            case ZERO -> {
                                if (user.isInitialNotify()){
                                    this.sendInitialNotification(0,startDate,user,cp.getPlan());
                                }
                                if (user.isFinalNotify()){
                                   this.sendFinalNotification(0,startDate,cp.getPlan().getPeriodo(),user,cp.getPlan());
                                }
                            }
                            case ONE -> {
                                if (user.isInitialNotify()){
                                    this.sendInitialNotification(1,startDate,user,cp.getPlan());
                                }
                                if (user.isFinalNotify()){
                                    this.sendFinalNotification(1,startDate,cp.getPlan().getPeriodo(),user,cp.getPlan());
                                }
                            }
                            case THREE -> {
                                if (user.isInitialNotify()){
                                    this.sendInitialNotification(3,startDate,user,cp.getPlan());
                                }
                                if (user.isFinalNotify()){
                                    this.sendFinalNotification(3,startDate,cp.getPlan().getPeriodo(),user,cp.getPlan());
                                }
                            }
                            case SEVEN -> {
                                if (user.isInitialNotify()){
                                    this.sendInitialNotification(7,startDate,user,cp.getPlan());
                                }
                                if (user.isFinalNotify()){
                                    this.sendFinalNotification(7,startDate,cp.getPlan().getPeriodo(),user,cp.getPlan());
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    private void sendInitialNotification(int daysBefore,LocalDate startDate, User user, Plan plan){
        LocalDate today = LocalDate.now();
        LocalDate notificationDate = startDate.minusDays(daysBefore);
        if (today.isEqual(notificationDate)){
            this.sendNotification(user.getEmail(),"Recordatorio de inicio de plan "+plan.getTitulo(),"Su plan inicia en "+daysBefore+" días");
        }
    }
    private void sendFinalNotification(int daysBefore, LocalDate startDate, Plan.Periodo periodo, User user, Plan plan){
        LocalDate finalDate=startDate;
        LocalDate today = LocalDate.now();
        switch (periodo){
            case SEMANAL -> {
                finalDate = startDate.plusWeeks(1);
            }
            case QUINCENAL -> {
                finalDate = startDate.plusWeeks(2);
            }
            case MENSUAL -> {
                finalDate = startDate.plusMonths(1);
            }
        }
        LocalDate notificationDate = finalDate.minusDays(daysBefore);
        if (today.isEqual(notificationDate)){
          this.sendNotification(user.getEmail(),"Recordatorio de fin de plan"+plan.getTitulo(),"Su plan finaliza en "+daysBefore+" días");
        }
    }
    private void sendNotification(String to, String subject, String text){
        System.out.println(text);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("pmoonn@ucenfotec.ac.cr");
        message.setSubject(subject);
        message.setText(text);
        try {
            mailSender.send(message);
        }catch (MatchException e){
            throw new RuntimeException("Error sending email",e);
        }

    }


}
