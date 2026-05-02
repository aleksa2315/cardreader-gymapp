import { Component, OnInit } from '@angular/core';
import { ApiService } from './services';

@Component({selector:'app-dashboard',standalone:true,template:`
<div class="top"><div><h1>Dashboard</h1><p class="muted">Pregled članova, aktivnih članarina i ulaza</p></div></div>
<div class="grid">
  <div class="card"><div class="muted">Ukupno članova</div><div class="stat">{{data?.activeMembers || 0}}</div></div>
  <div class="card"><div class="muted">Aktivne članarine</div><div class="stat">{{data?.activeSubscriptions || 0}}</div></div>
  <div class="card"><div class="muted">Danas pokušaja ulaza</div><div class="stat">{{data?.todayAccessAttempts || 0}}</div></div>
</div>`})
export class DashboardComponent implements OnInit{data:any;constructor(private api:ApiService){}ngOnInit(){this.api.dashboard().subscribe(x=>this.data=x);}}
