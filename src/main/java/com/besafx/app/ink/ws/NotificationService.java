package com.besafx.app.ink.ws;

import com.besafx.app.ink.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void postContact(Contact contact) {
        messagingTemplate.convertAndSend("/topic/notifications/contact/post", contact);
    }
}
