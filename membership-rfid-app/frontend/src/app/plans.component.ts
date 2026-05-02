import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService, Plan } from './services';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-plans',
    standalone: true,
    imports: [CommonModule, FormsModule],
    template: `
        <div class="top">
            <div>
                <h1>Članarine</h1>
                <p class="muted">Dnevna, nedeljna, polumesečna i mesečna članarina</p>
            </div>

            <button class="btn" (click)="newPlan()">Novi paket</button>
        </div>

        <div class="card" *ngIf="editing">
            <h3>{{form.id ? 'Izmena paketa' : 'Novi paket'}}</h3>

            <div class="form-grid">
                <div>
                    <label>Naziv</label>
                    <input [(ngModel)]="form.name">
                </div>

                <div>
                    <label>Kod</label>
                    <input [(ngModel)]="form.code">
                </div>

                <div>
                    <label>Trajanje dana</label>
                    <input type="number" [(ngModel)]="form.durationDays">
                </div>

                <div>
                    <label>Cena</label>
                    <input type="number" [(ngModel)]="form.price">
                </div>

                <div>
                    <label>Aktivan</label>
                    <select [(ngModel)]="form.active">
                        <option [ngValue]="true">Da</option>
                        <option [ngValue]="false">Ne</option>
                    </select>
                </div>
            </div>

            <div class="actions" style="margin-top:12px">
                <button class="btn" (click)="save()">Sačuvaj</button>
                <button class="btn secondary" (click)="editing=false">Otkaži</button>
            </div>
        </div>

        <div class="card desktop-table">
            <table>
                <thead>
                <tr>
                    <th>Naziv</th>
                    <th>Kod</th>
                    <th>Dana</th>
                    <th>Cena</th>
                    <th>Aktivan</th>
                    <th></th>
                </tr>
                </thead>

                <tbody>
                <tr *ngFor="let p of plans">
                    <td>{{p.name}}</td>
                    <td>{{p.code}}</td>
                    <td>{{p.durationDays}}</td>
                    <td>{{p.price}}</td>
                    <td>
          <span class="badge" [class.ok]="p.active" [class.bad]="!p.active">
            {{p.active ? 'Da' : 'Ne'}}
          </span>
                    </td>
                    <td>
                        <button class="btn secondary small" (click)="edit(p)">
                            Izmeni
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="mobile-list">
            <div class="mobile-item" *ngFor="let p of plans">
                <div class="mobile-item-title">
                    {{p.name}}
                </div>

                <div class="mobile-item-row">
                    <span class="mobile-item-label">Kod</span>
                    <span class="mobile-item-value">{{p.code}}</span>
                </div>

                <div class="mobile-item-row">
                    <span class="mobile-item-label">Trajanje</span>
                    <span class="mobile-item-value">{{p.durationDays}} dana</span>
                </div>

                <div class="mobile-item-row">
                    <span class="mobile-item-label">Cena</span>
                    <span class="mobile-item-value">{{p.price}}</span>
                </div>

                <div class="mobile-item-row">
                    <span class="mobile-item-label">Aktivan</span>
                    <span class="mobile-item-value">
        <span class="badge" [class.ok]="p.active" [class.bad]="!p.active">
          {{p.active ? 'Da' : 'Ne'}}
        </span>
      </span>
                </div>

                <div class="mobile-actions">
                    <button class="btn secondary small" (click)="edit(p)">
                        Izmeni
                    </button>
                </div>
            </div>
        </div>
    `
})
export class PlansComponent implements OnInit {
    plans: Plan[] = [];
    editing = false;

    form: Plan = {
        name: '',
        code: '',
        durationDays: 30,
        price: 0,
        active: true
    };

    constructor(private api: ApiService) {}

    ngOnInit() {
        this.load();
    }

    load() {
        this.api.plans().subscribe(x => this.plans = x);
    }

    newPlan() {
        this.editing = true;
        this.form = {
            name: '',
            code: '',
            durationDays: 30,
            price: 0,
            active: true
        };
    }

    edit(p: Plan) {
        this.editing = true;
        this.form = { ...p };
    }

    save() {
        this.api.savePlan(this.form).subscribe(() => {
            this.editing = false;
            this.load();
        });
    }
}