package com.besafx.app.ink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableJpaAuditing
@Controller
public class InkApplication {

    public static void main(String[] args) {
        SpringApplication.run(InkApplication.class, args);
    }

    @RequestMapping("/")
    public String index() {
        return "redirect:dist/index";
    }
}
