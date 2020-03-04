import{SocketClientService}from'./../_services/socket/socket-client.service';
import {ContactService}from './../_services/rest/contact.service';
import { Component } from '@angular/core';

import {Contact}from './../_model/contact';
import {prepareObject }from './../_utils/helpers';

@Component({
selector: 'ink-app',
template: '< router-outlet></router-outlet>',
})
export class AppComponent {

protected contact: Contact = new Contact();

constructor(private client: SocketClientService,
              private contactService: ContactService) {
    client
      .onMessage('/topic/notifications/contact/post')
      .subscribe(contact => console.info(contact));
  }

  postContact() {
    this.contact.name = 'Bassam Almahdy';
    this.contactService.post(prepareObject(this.contact)).subscribe(data => console.info(data));
  }

}
