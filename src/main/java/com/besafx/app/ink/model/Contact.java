package com.besafx.app.ink.model;

import com.besafx.app.ink.config.Auditable;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact extends Auditable<String> implements Serializable {

    private final static Logger log = LoggerFactory.getLogger(Contact.class);

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "contactSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "CONTACT_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "contactSequenceGenerator")
    private Long id;

    private String name;

    private String nameEn;

    private String nickname;

    @Formula(value = "concat_ws(' ',nickname, name)")
    private String shortName;

    @Formula(value = "concat_ws(' ',nickname, name_en)")
    private String shortNameEn;

    private String address;

    private String mobile;

    private String phone;

    private String nationality;

    private String identityNumber;

    private String identityLocation;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String photo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date identityStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

    private String birthLocation;

    private String qualification;

    @JsonCreator
    public static Contact Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Contact contact = mapper.readValue(jsonString, Contact.class);
        return contact;
    }
}
