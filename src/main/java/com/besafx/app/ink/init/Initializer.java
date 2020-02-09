package com.besafx.app.ink.init;

import com.besafx.app.ink.dao.CompanyDao;
import com.besafx.app.ink.dao.ContactDao;
import com.besafx.app.ink.dao.PersonDao;
import com.besafx.app.ink.dao.TeamDao;
import com.besafx.app.ink.dto.CompanyOptions;
import com.besafx.app.ink.dto.PersonOptions;
import com.besafx.app.ink.model.Company;
import com.besafx.app.ink.model.Contact;
import com.besafx.app.ink.model.Person;
import com.besafx.app.ink.model.Team;
import com.besafx.app.ink.util.JsonConverter;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {

    private final static Logger LOG = LoggerFactory.getLogger(Initializer.class);

    @Autowired
    private PersonDao personDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private ContactDao contactDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        startup();

    }

    private void startup() {

        if (Lists.newArrayList(personDao.findAll()).isEmpty()) {
            final Person person = new Person();
            final Contact contact = new Contact();
            contact.setNickname("Eng. ");
            contact.setName("Bassam Almahdy");
            contact.setPhoto("");
            contact.setQualification("Web Developer");
            person.setContact(contactDao.save(contact));
            person.setEmail("admin@gmail.com");
            person.setPassword(passwordEncoder.encode("admin"));
            person.setHiddenPassword("admin");
            person.setEnabled(true);
            person.setTokenExpired(false);
            person.setActive(false);
            person.setTechnicalSupport(true);
            final Team team = new Team();
            team.setName("Technical Support");
            team.setAuthorities(String.join(",", "ROLE_COMPANY_UPDATE",
                    "ROLE_CONTACT_CREATE",
                    "ROLE_CONTACT_UPDATE",
                    "ROLE_CONTACT_DELETE",

                    "ROLE_CITY_CREATE",
                    "ROLE_CITY_UPDATE",
                    "ROLE_CITY_DELETE",

                    "ROLE_COUNTRY_CREATE",
                    "ROLE_COUNTRY_UPDATE",
                    "ROLE_COUNTRY_DELETE",

                    "ROLE_PERSON_CREATE",
                    "ROLE_PERSON_UPDATE",
                    "ROLE_PERSON_DELETE",

                    "ROLE_TEAM_CREATE",
                    "ROLE_TEAM_UPDATE",
                    "ROLE_TEAM_DELETE"
            ));
            person.setTeam(teamDao.save(team));
            person.setOptions(JsonConverter.toString(PersonOptions.builder()
                    .lang("ar")
                    .style("default")
                    .dateType("G")
                    .build())
            );
            final Company company = new Company();
            company.setOptions(
                    JsonConverter.toString(
                            CompanyOptions.builder()
                                    .vatFactor(0.0)
                                    .logo("")
                                    .background("")
                                    .reportTitle("")
                                    .reportSubTitle("")
                                    .reportFooter("")));
            person.setCompany(companyDao.save(company));
            personDao.save(person);
        }

    }
}
