package com.besafx.app.ink.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.besafx.app.ink.config.PersonUserDetails;
import com.besafx.app.ink.dao.ContactDao;
import com.besafx.app.ink.dao.PersonDao;
import com.besafx.app.ink.dto.PersonOptions;
import com.besafx.app.ink.exception.PersonNotFoundException;
import com.besafx.app.ink.jwt.JwtConfig;
import com.besafx.app.ink.model.Person;
import com.besafx.app.ink.util.JsonConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/person")
@Slf4j
public class PersonController {

    private final static String FILTER_JSON = "**,team[**,-persons]";
    private final static String FILTER_USER_INFO = "id,email,options,contact[id,shortName,shortNameEn,name,nameEn],team[**,-persons]";

    @Autowired
    private PersonDao personDao;

    @Autowired
    private ContactDao contactDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtConfig jwtConfig;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional
    @PreAuthorize("hasAuthority('ROLE_PERSON_CREATE')")
    public Person post(@RequestBody final Person person) {
        return SquigglyUtils.objectify(Squiggly.init(new ObjectMapper(), FILTER_JSON),
                personDao.save(person),
                Person.class);
    }

    @GetMapping(value = "/setUserLang/{email:.+}/{lang}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional
    public Person setUserLang(@PathVariable(value = "email") final String email, @PathVariable(value = "lang") final String lang) {
        final Optional<Person> result = personDao.findByEmail(email);
        result.orElseThrow(() -> new PersonNotFoundException(email));
        final Person person = result.get();
        final Optional<PersonOptions> options = JsonConverter.toOptionalObject(
                person.getOptions(),
                PersonOptions.class);
        options.ifPresent(val -> {
            val.setLang(lang);
            person.setOptions(JsonConverter.toString(options));
        });
        return SquigglyUtils.objectify(Squiggly.init(new ObjectMapper(), FILTER_USER_INFO),
                personDao.save(person),
                Person.class);
    }

    @GetMapping(value = "/setUserTheme/{email:.+}/{theme}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional
    public Person setUserTheme(@PathVariable(value = "email") final String email, @PathVariable(value = "theme") final String theme) {
        final Optional<Person> result = personDao.findByEmail(email);
        result.orElseThrow(() -> new PersonNotFoundException(email));
        final Person person = result.get();
        final Optional<PersonOptions> options = JsonConverter.toOptionalObject(
                person.getOptions(),
                PersonOptions.class);
        options.ifPresent(val -> {
            val.setStyle(theme);
            person.setOptions(JsonConverter.toString(options));
        });
        return SquigglyUtils.objectify(Squiggly.init(new ObjectMapper(), FILTER_USER_INFO),
                personDao.save(person),
                Person.class);
    }

    @GetMapping(value = "/findByEmail/{email:.+}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Person findByEmail(@PathVariable(value = "email") final String email) {
        final Optional<Person> result = personDao.findByEmail(email);
        result.orElseThrow(() -> new PersonNotFoundException(email));
        return SquigglyUtils.objectify(Squiggly.init(new ObjectMapper(), FILTER_JSON),
                result.get(),
                Person.class);
    }

    @GetMapping(value = "/getUserInfo/{email:.+}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Person getUserInfo(@PathVariable(value = "email") final String email) {
        final Optional<Person> result = personDao.findByEmail(email);
        result.orElseThrow(() -> new PersonNotFoundException(email));
        return SquigglyUtils.objectify(Squiggly.init(new ObjectMapper(), FILTER_USER_INFO),
                result.get(),
                Person.class);
    }

    @PostMapping(value = "/getToken", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getToken(@RequestParam final String username, @RequestParam final String password) {

        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        final Authentication auth = authenticationManager.authenticate(authenticationToken);

        final PersonUserDetails principal = (PersonUserDetails) auth.getPrincipal();

        final DateTime dateTime = new DateTime().plusMinutes(jwtConfig.getExpiration());

        final String token = JWT.create()
                .withSubject(principal.getUsername())
                .withClaim("Authorities", principal.getPerson().getTeam().getAuthorities())
                .withExpiresAt(dateTime.toDate())
                .sign(Algorithm.HMAC512(jwtConfig.getSecret().getBytes()));

        return jwtConfig.getPrefix() + token;
    }

    @GetMapping("/getPrincipal")
    @ResponseBody
    public String getPrincipal(final HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Principle: {}", authentication.getPrincipal());
        log.info("Authorities: {}", authentication.getAuthorities());
        log.info("Header: {}", request.getHeader("Authorization"));
        return authentication.getPrincipal().toString();
    }

}
