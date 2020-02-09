package com.besafx.app.ink.config;

import com.besafx.app.ink.model.Person;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PersonUserDetails implements UserDetails {

    @Getter
    private final Person person;

    private final Collection<? extends GrantedAuthority> grantedAuthorities;

    public PersonUserDetails(Person person) {
        this(person, new ArrayList<>());
    }

    public PersonUserDetails(Person person, Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.person = person;
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getEmail();
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
        return this.person.getEnabled();
    }
}
