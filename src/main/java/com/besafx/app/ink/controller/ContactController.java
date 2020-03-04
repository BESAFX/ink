package com.besafx.app.ink.controller;

import com.besafx.app.ink.dao.ContactDao;
import com.besafx.app.ink.exception.ContactNotFoundException;
import com.besafx.app.ink.model.Contact;
import com.besafx.app.ink.ws.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/contact")
@Slf4j
public class ContactController {

    private final static Logger LOG = LoggerFactory.getLogger(ContactController.class);

    private final static String FILTER_JSON = "**";

    @Autowired
    private ContactDao contactDao;

    @Autowired
    private NotificationService notificationService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional
    @PreAuthorize("hasAuthority('ROLE_CONTACT_CREATE')")
    public Contact post(@RequestBody Contact contact) {
        contact = contactDao.save(contact);
        notificationService.postContact(contact);
        return SquigglyUtils.objectify(Squiggly.init(new ObjectMapper(), FILTER_JSON),
                contact,
                Contact.class);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional
    @PreAuthorize("hasAuthority('ROLE_CONTACT_UPDATE')")
    public Contact put(@RequestBody final Contact contact) {
        final Optional<Contact> result = contactDao.findById(contact.getId());
        if (result.isPresent()) {
            return SquigglyUtils.objectify(Squiggly.init(new ObjectMapper(), FILTER_JSON),
                    contactDao.save(contact),
                    Contact.class);
        } else {
            throw new ContactNotFoundException(contact.getId());
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    @Transactional
    @PreAuthorize("hasAuthority('ROLE_CONTACT_DELETE')")
    public void delete(@PathVariable final Long id) {
        final Optional<Contact> result = contactDao.findById(id);
        result.ifPresent(val -> contactDao.delete(val));
        result.orElseThrow(() -> new ContactNotFoundException(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Contact> all() {
        return SquigglyUtils.listify(Squiggly.init(new ObjectMapper(), FILTER_JSON),
                contactDao.findAll(),
                Contact.class);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Contact get(@PathVariable final Long id) {
        final Optional<Contact> result = contactDao.findById(id);
        result.orElseThrow(() -> new ContactNotFoundException(id));
        return SquigglyUtils.objectify(Squiggly.init(new ObjectMapper(), FILTER_JSON),
                result.get(),
                Contact.class);
    }

}
