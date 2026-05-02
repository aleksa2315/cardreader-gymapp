import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService, Plan } from './services';
import { CommonModule } from '@angular/common';

@Component({selector:'app-plans',standalone:true,imports:[CommonModule, FormsModule],template:`
<div class="top"><div><h1>Članarine</h1><p class="muted">Dnevna, nedeljna, polumesečna i mesečna članarina</p></div><button class="btn" (click)="newPlan()">Novi paket</button></div>
<div class="card" *ngIf="editing"><h3>Paket</h3><div class="form-grid"><div><label>Naziv</label><input [(ngModel)]="form.name"></div><div><label>Kod</label><input [(ngModel)]="form.code"></div><div><label>Trajanje dana</label><input type="number" [(ngModel)]="form.durationDays"></div><div><label>Cena</label><input type="number" [(ngModel)]="form.price"></div><div><label>Aktivan</label><select [(ngModel)]="form.active"><option [ngValue]="true">Da</option><option [ngValue]="false">Ne</option></select></div></div><div class="actions" style="margin-top:12px"><button class="btn" (click)="save()">Sačuvaj</button><button class="btn secondary" (click)="editing=false">Otkaži</button></div></div>
<div class="card"><table><tr><th>Naziv</th><th>Kod</th><th>Dana</th><th>Cena</th><th>Aktivan</th><th></th></tr><tr *ngFor="let p of plans"><td>{{p.name}}</td><td>{{p.code}}</td><td>{{p.durationDays}}</td><td>{{p.price}}</td><td>{{p.active?'Da':'Ne'}}</td><td><button class="btn secondary" (click)="edit(p)">Izmeni</button></td></tr></table></div>`})
export class PlansComponent implements OnInit{plans:Plan[]=[];editing=false;form:Plan={name:'',code:'',durationDays:30,price:0,active:true};constructor(private api:ApiService){}ngOnInit(){this.load();}load(){this.api.plans().subscribe(x=>this.plans=x);}newPlan(){this.editing=true;this.form={name:'',code:'',durationDays:30,price:0,active:true};}edit(p:Plan){this.editing=true;this.form={...p};}save(){this.api.savePlan(this.form).subscribe(()=>{this.editing=false;this.load();});}}
