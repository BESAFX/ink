package com.besafx.app.ink.controller;

import com.besafx.app.ink.dao.CompanyDao;
import com.besafx.app.ink.exception.CompanyNotFoundException;
import com.besafx.app.ink.model.Company;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
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
@RequestMapping(value = "/api/company")
public class CompanyController {

    private final static Logger LOG = LoggerFactory.getLogger(CompanyController.class);

    private final static String FILTER_JSON = "**,-persons";

    @Autowired
    private CompanyDao companyDao;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional
    @PreAuthorize("hasAuthority('ROLE_CONTACT_CREATE')")
    public Company post(@RequestBody final Company company) {
        return SquigglyUtils.objectify(Squiggly.init(new ObjectMapper(), FILTER_JSON),
                companyDao.save(company),
                Company.class);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional
    @PreAuthorize("hasAuthority('ROLE_CONTACT_UPDATE')")
    public Company put(@RequestBody final Company company) {
        final Optional<Company> result = companyDao.findById(company.getId());
        if (result.isPresent()) {
            return SquigglyUtils.objectify(Squiggly.init(new ObjectMapper(), FILTER_JSON),
                    companyDao.save(company),
                    Company.class);
        } else {
            throw new CompanyNotFoundException(company.getId());
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    @Transactional
    @PreAuthorize("hasAuthority('ROLE_CONTACT_DELETE')")
    public void delete(@PathVariable final Long id) {
        final Optional<Company> result = companyDao.findById(id);
        result.ifPresent(val -> companyDao.delete(val));
        result.orElseThrow(() -> new CompanyNotFoundException(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Company> all() {
        return SquigglyUtils.listify(Squiggly.init(new ObjectMapper(), FILTER_JSON),
                companyDao.findAll(),
                Company.class);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Company get(@PathVariable final Long id) {
        final Optional<Company> result = companyDao.findById(id);
        result.orElseThrow(() -> new CompanyNotFoundException(id));
        return SquigglyUtils.objectify(Squiggly.init(new ObjectMapper(), FILTER_JSON),
                result.get(),
                Company.class);
    }

}
