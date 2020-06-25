package com.example.sweater.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "usr")
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Username can`t be empty")
    private String username;
    @NotBlank(message = "Password can`t be empty")
    private String password;
    @Transient //tell hibernate that this field should not be taken from DB
    @NotBlank(message = "Password confirmation can`t be empty")
    private String password2;
    private boolean active;
    @Email(message = "Email is not correct") //check if it look like email
    @NotBlank(message = "Email can`t be empty")
    private String email;
    private String activationCode; //used to affirmative that user is owner of this mailbox

    //@ElementCollection create additional table to enum containing
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    //@CollectionTable tells that this field will be locating in another table;
    //to which we don`t need to describe mapping
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING) //tell that we want save enum in String type
    private Set<Role> roles;

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
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
        return isActive();
    }
}