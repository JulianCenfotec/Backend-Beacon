package cr.ac.ucenfotec.waddle.beacon.logic.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.notification.NotificationOption;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.rol.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "user")
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String displayname;

    @Column(nullable = false)
    private String password;

    private String resetToken;

    private boolean initialNotify;
    private boolean finalNotify;
    private NotificationOption notificationOption;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    // Constructors
    public User() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> listRole = new ArrayList<GrantedAuthority>();

        listRole.add(new SimpleGrantedAuthority("ROLE_" + role.getName().toString()));
        return listRole;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public String getUsername() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayname(){
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Role getRole() {
        return role;
    }

    public User setRole(Role role) {
        this.role = role;

        return this;
    }
    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public boolean isInitialNotify() {
        return initialNotify;
    }

    public void setInitialNotify(boolean initialNotify) {
        this.initialNotify = initialNotify;
    }

    public boolean isFinalNotify() {
        return finalNotify;
    }

    public void setFinalNotify(boolean finalNotify) {
        this.finalNotify = finalNotify;
    }

    public NotificationOption getNotificationOption() {
        return notificationOption;
    }

    public void setNotificationOption(NotificationOption notificationOption) {
        this.notificationOption = notificationOption;
    }
}