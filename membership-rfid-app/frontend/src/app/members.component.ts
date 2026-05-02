import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService, Member, Plan, Subscription } from './services';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-members',
    standalone: true,
    imports: [CommonModule, FormsModule],
    template: `
        <div class="top">
            <div>
                <h1>Članovi</h1>
                <p class="muted">Upravljanje članovima i produženje članarina</p>
            </div>

            <button class="btn" (click)="newMember()">Novi član</button>
        </div>

        <div class="card">
            <label>Pretraga</label>
            <input
                    placeholder="Pretraga po imenu, telefonu, email-u"
                    [(ngModel)]="q"
                    (keyup.enter)="load()"
            >

            <button class="btn" style="margin-top:10px" (click)="load()">
                Pretraži
            </button>
        </div>

        <div class="card" *ngIf="editing">
            <h3>{{form.id ? 'Izmena člana' : 'Novi član'}}</h3>

            <div class="form-grid">
                <div>
                    <label>Ime</label>
                    <input [(ngModel)]="form.firstName">
                </div>

                <div>
                    <label>Prezime</label>
                    <input [(ngModel)]="form.lastName">
                </div>

                <div>
                    <label>Status</label>
                    <select [(ngModel)]="form.status">
                        <option>ACTIVE</option>
                        <option>BLOCKED</option>
                        <option>INACTIVE</option>
                    </select>
                </div>

                <div>
                    <label>Telefon</label>
                    <input [(ngModel)]="form.phone">
                </div>

                <div>
                    <label>Email</label>
                    <input [(ngModel)]="form.email">
                </div>

                <div>
                    <label>Napomena</label>
                    <input [(ngModel)]="form.note">
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
                    <th>Član</th>
                    <th>Telefon</th>
                    <th>Email</th>
                    <th>Status</th>
                    <th>Akcije</th>
                </tr>
                </thead>

                <tbody>
                <tr *ngFor="let m of members">
                    <td>{{m.firstName}} {{m.lastName}}</td>
                    <td>{{m.phone || '-'}}</td>
                    <td>{{m.email || '-'}}</td>
                    <td>
          <span class="badge" [class.ok]="m.status === 'ACTIVE'" [class.bad]="m.status !== 'ACTIVE'">
            {{m.status}}
          </span>
                    </td>
                    <td class="actions">
                        <button class="btn secondary small" (click)="edit(m)">Izmeni</button>
                        <button class="btn small" (click)="openSub(m)">Produži</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="mobile-list">
            <div class="mobile-item" *ngFor="let m of members">
                <div class="mobile-item-title">
                    {{m.firstName}} {{m.lastName}}
                </div>

                <div class="mobile-item-row">
                    <span class="mobile-item-label">Telefon</span>
                    <span class="mobile-item-value">{{m.phone || '-'}}</span>
                </div>

                <div class="mobile-item-row">
                    <span class="mobile-item-label">Email</span>
                    <span class="mobile-item-value">{{m.email || '-'}}</span>
                </div>

                <div class="mobile-item-row">
                    <span class="mobile-item-label">Status</span>
                    <span class="mobile-item-value">
        <span class="badge" [class.ok]="m.status === 'ACTIVE'" [class.bad]="m.status !== 'ACTIVE'">
          {{m.status}}
        </span>
      </span>
                </div>

                <div class="mobile-actions">
                    <button class="btn secondary small" (click)="edit(m)">Izmeni</button>
                    <button class="btn small" (click)="openSub(m)">Produži</button>
                </div>
            </div>
        </div>

        <div class="card" *ngIf="selected">
            <h3>Produženje članarine: {{selected.firstName}} {{selected.lastName}}</h3>

            <div class="form-grid">
                <div>
                    <label>Paket</label>
                    <select [(ngModel)]="sub.planId" (ngModelChange)="onPlanChange()">
                        <option *ngFor="let p of plans" [ngValue]="p.id">
                            {{p.name}} - {{p.price}}
                        </option>
                    </select>
                </div>

                <div>
                    <label>Početak</label>
                    <input type="date" [(ngModel)]="sub.startDate">
                </div>

                <div>
                    <label>Cena</label>
                    <input type="number" [(ngModel)]="sub.price">
                </div>
            </div>

            <button class="btn" style="margin-top:12px" (click)="createSub()">
                Kreiraj članarinu
            </button>

            <h4 style="margin-top:20px">Istorija članarina</h4>

            <div class="desktop-table">
                <table>
                    <thead>
                    <tr>
                        <th>Paket</th>
                        <th>Od</th>
                        <th>Do</th>
                        <th>Cena</th>
                        <th>Status</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr *ngFor="let s of subs">
                        <td>{{s.planName}}</td>
                        <td>{{s.startDate}}</td>
                        <td>{{s.endDate}}</td>
                        <td>{{s.price}}</td>
                        <td>{{s.status}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <div class="mobile-list">
                <div class="mobile-item" *ngFor="let s of subs">
                    <div class="mobile-item-title">
                        {{s.planName}}
                    </div>

                    <div class="mobile-item-row">
                        <span class="mobile-item-label">Od</span>
                        <span class="mobile-item-value">{{s.startDate}}</span>
                    </div>

                    <div class="mobile-item-row">
                        <span class="mobile-item-label">Do</span>
                        <span class="mobile-item-value">{{s.endDate}}</span>
                    </div>

                    <div class="mobile-item-row">
                        <span class="mobile-item-label">Cena</span>
                        <span class="mobile-item-value">{{s.price}}</span>
                    </div>

                    <div class="mobile-item-row">
                        <span class="mobile-item-label">Status</span>
                        <span class="mobile-item-value">{{s.status}}</span>
                    </div>
                </div>
            </div>
        </div>
    `
})
export class MembersComponent implements OnInit {
    members: Member[] = [];
    plans: Plan[] = [];
    subs: Subscription[] = [];

    q = '';
    editing = false;

    form: Member = {
        firstName: '',
        lastName: '',
        status: 'ACTIVE'
    };

    selected?: Member;

    sub: any = {};

    constructor(private api: ApiService) {}

    ngOnInit() {
        this.load();

        this.api.plans().subscribe(x => {
            this.plans = x;
        });
    }

    load() {
        this.api.members(this.q).subscribe(x => this.members = x);
    }

    newMember() {
        this.editing = true;
        this.form = {
            firstName: '',
            lastName: '',
            status: 'ACTIVE'
        };
    }

    edit(m: Member) {
        this.editing = true;
        this.form = { ...m };
    }

    save() {
        this.api.saveMember(this.form).subscribe(() => {
            this.editing = false;
            this.load();
        });
    }

    openSub(m: Member) {
        this.selected = m;

        const firstPlan = this.plans[0];

        this.sub = {
            memberId: m.id,
            planId: firstPlan?.id,
            startDate: new Date().toISOString().substring(0, 10),
            price: firstPlan?.price || 0
        };

        if (m.id) {
            this.api.subscriptions(m.id).subscribe(x => this.subs = x);
        }
    }

    onPlanChange() {
        const selectedPlan = this.plans.find(p => p.id === this.sub.planId);
        if (selectedPlan) {
            this.sub.price = selectedPlan.price;
        }
    }

    createSub() {
        if (!this.selected) return;

        this.api.createSubscription(this.sub).subscribe(() => {
            this.openSub(this.selected!);
        });
    }
}