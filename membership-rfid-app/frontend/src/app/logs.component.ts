import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from './services';

@Component({
    selector:'app-logs',
    standalone:true,
    imports:[CommonModule],
    template:`
<div class="top"><div><h1>Log ulaza</h1><p class="muted">Poslednjih 50 pokušaja ulaza preko RFID-a</p></div><button class="btn" (click)="load()">Osveži</button></div>
<div class="card"><table><tr><th>Vreme</th><th>Kartica</th><th>Član</th><th>Allowed</th><th>Razlog</th></tr><tr *ngFor="let l of logs"><td>{{l.requestTime}}</td><td>{{l.cardId}}</td><td>{{l.memberName}}</td><td><span class="badge" [class.ok]="l.allowed" [class.bad]="!l.allowed">{{l.allowed?1:0}}</span></td><td>{{l.reason}}</td></tr></table></div>`
})
export class LogsComponent implements OnInit {
    logs:any[]=[];

    constructor(private api:ApiService) {}

    ngOnInit() {
        this.load();
    }

    load() {
        this.api.logs().subscribe(x=>this.logs=x);
    }
}