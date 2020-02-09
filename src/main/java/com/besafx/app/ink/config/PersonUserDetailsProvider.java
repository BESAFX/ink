package com.besafx.app.ink.config;

import com.besafx.app.ink.dao.PersonDao;
import com.besafx.app.ink.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class PersonUserDetailsProvider implements UserDetailsService {

    @Autowired
    private PersonDao personDao;

    public PersonUserDetailsProvider() {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personDao
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with -> email : " + email));

        List<GrantedAuthority> authorities = new ArrayList<>();

        log.info("Setting authorities...");
        Optional.ofNullable(person.getTeam().getAuthorities())
                .ifPresent(value -> Arrays.asList(value.split(",")).stream()
                        .forEach(s -> authorities.add(new SimpleGrantedAuthority(s))));

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Saving login information...");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        person.setLastLoginDate(new Date());
        person.setActive(true);
        person.setIpAddress(request.getRemoteAddr());
        personDao.save(person);

        return new PersonUserDetails(person, authorities);
    }
}
