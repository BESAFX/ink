package com.besafx.app.ink.controller;

import com.besafx.app.ink.dao.TeamDao;
import com.besafx.app.ink.exception.TeamNotFoundException;
import com.besafx.app.ink.model.Team;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(value = "/api/team")
@Slf4j
public class TeamController {

    private final static String FILTER_JSON = "**,persons[**,-team]";

    @Autowired
    private TeamDao teamDao;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional
    @PreAuthorize("hasAuthority('ROLE_TEAM_CREATE')")
    public Team post(@RequestBody final Team team) {
        return SquigglyUtils.objectify(Squiggly.init(new ObjectMapper(), FILTER_JSON),
                teamDao.save(team),
                Team.class);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional
    @PreAuthorize("hasAuthority('ROLE_TEAM_UPDATE')")
    public Team put(@RequestBody final Team team) {
        Optional<Team> result = teamDao.findById(team.getId());
        if (result.isPresent()) {
            return SquigglyUtils.objectify(Squiggly.init(new ObjectMapper(), FILTER_JSON),
                    teamDao.save(team),
                    Team.class);
        } else {
            throw new TeamNotFoundException(team.getId());
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    @Transactional
    @PreAuthorize("hasAuthority('ROLE_TEAM_DELETE')")
    public void delete(@PathVariable final Long id) {
        Optional<Team> result = teamDao.findById(id);
        result.ifPresent(val -> teamDao.delete(val));
        result.orElseThrow(() -> new TeamNotFoundException(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Team> all() {
        return SquigglyUtils.listify(Squiggly.init(new ObjectMapper(), FILTER_JSON),
                teamDao.findAll(),
                Team.class);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Team get(@PathVariable final Long id) {
        Optional<Team> result = teamDao.findById(id);
        result.orElseThrow(() -> new TeamNotFoundException(id));
        return SquigglyUtils.objectify(Squiggly.init(new ObjectMapper(), FILTER_JSON),
                result.get(),
                Team.class);
    }

}
