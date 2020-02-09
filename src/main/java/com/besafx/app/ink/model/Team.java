package com.besafx.app.ink.model;

import com.besafx.app.ink.config.Auditable;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Team extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "teamSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "TEAM_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "teamSequenceGenerator")
    private Long id;

    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String authorities;

    @OneToMany(mappedBy = "team")
    private List<Person> persons = new ArrayList<>();

    @JsonCreator
    public static Team Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Team team = mapper.readValue(jsonString, Team.class);
        return team;
    }
}
