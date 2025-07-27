package cr.ac.ucenfotec.waddle.beacon.logic.entity.subscriptionPlan;


import java.util.Calendar;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_subscription")
public class UserSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "subscription_id", nullable = false)
    private SubscriptionPlan subscription;
    
    @Column(unique = false, nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private UserSubscriptionStateEnum estado;

    @Column(name = "subscription_ends_at")
    private Date endOfSuscriptionAt;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    
    public UserSubscription() {}
    public UserSubscription(SubscriptionPlan subscription, User user, UserSubscriptionStateEnum estado) {
    	this.subscription = subscription;
    	this.estado = estado;
    	this.user = user;
    	
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, this.subscription.getPlazo());
        this.endOfSuscriptionAt = calendar.getTime(); 
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SubscriptionPlan getSubscription() {
        return subscription;
    }

    public void setSubscription(SubscriptionPlan subscription) {
        this.subscription = subscription;
    }

    public UserSubscriptionStateEnum getEstado() {
        return estado;
    }

    public void setEstado(UserSubscriptionStateEnum estado) {
        this.estado = estado;
    }

    public Date getEndOfSuscriptionAt() {
        return endOfSuscriptionAt;
    }

    public void setEndOfSuscriptionAt(Date endOfSuscriptionAt) {
        this.endOfSuscriptionAt = endOfSuscriptionAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
