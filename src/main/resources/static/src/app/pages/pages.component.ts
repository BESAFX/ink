import{Component, OnInit}from '@angular/core';

@Component({
selector: 'ink-pages',
templateUrl: './pages.component.html',
styleUrls: ['./pages.component.scss']
})
export class PagesComponent implements OnInit {

constructor() { }

  ngOnInit() {
    console.info('Pages Componenet loaded');
  }

}
