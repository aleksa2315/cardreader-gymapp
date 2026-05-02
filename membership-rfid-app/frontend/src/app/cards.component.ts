import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService, Card, Member } from './services';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-cards',
    standalone: true,
    imports: [CommonModule, FormsModule],
    template: `
        <div class="top">
            <div>
                <h1>RFID kartice</h1>
                <p class="muted">Dodela kartica članovima</p>
            </div>
        </div>

        <div class="card">
            <h3>Nova kartica</h3>

            <div class="form-grid two">
                <div>
                    <label>Card ID</label>
                    <input [(ngModel)]="form.cardId" placeholder="Unesi ID kartice">
                </div>

                <div>
                    <label>Član</label>
                    <select [(ngModel)]="form.memberId">
                        <option [ngValue]="null">Izaberi člana</option>
                        <option *ngFor="let m of members" [ngValue]="m.id">
                            {{m.firstName}} {{m.lastName}}
                        </option>
                    </select>
                </div>
            </div>

            <button class="btn" style="margin-top:12px" (click)="create()">
                Dodaj karticu
            </button>
        </div>

        <div class="card desktop-table">
            <table>
                <thead>
                <tr>
                    <th>Kartica</th>
                    <th>Član</th>
                    <th>Aktivna</th>
                    <th>Izdato</th>
                    <th></th>
                </tr>
                </thead>

                <tbody>
                <tr *ngFor="let c of cards">
                    <td>{{c.cardId}}</td>
                    <td>{{c.memberName}}</td>
                    <td>
          <span class="badge" [class.ok]="c.active" [class.bad]="!c.active">
            {{c.active ? 'Da' : 'Ne'}}
          </span>
                    </td>
                    <td>{{c.issuedAt || '-'}}</td>
                    <td>
                        <button class="btn danger small" [disabled]="!c.active" (click)="deactivate(c)">
                            Deaktiviraj
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="mobile-list">
            <div class="mobile-item" *ngFor="let c of cards">
                <div class="mobile-item-title">
                    Kartica: {{c.cardId}}
                </div>

                <div class="mobile-item-row">
                    <span class="mobile-item-label">Član</span>
                    <span class="mobile-item-value">{{c.memberName || '-'}}</span>
                </div>

                <div class="mobile-item-row">
                    <span class="mobile-item-label">Aktivna</span>
                    <span class="mobile-item-value">
        <span class="badge" [class.ok]="c.active" [class.bad]="!c.active">
          {{c.active ? 'Da' : 'Ne'}}
        </span>
      </span>
                </div>

                <div class="mobile-item-row">
                    <span class="mobile-item-label">Izdato</span>
                    <span class="mobile-item-value">{{c.issuedAt || '-'}}</span>
                </div>

                <div class="mobile-actions">
                    <button class="btn danger small" [disabled]="!c.active" (click)="deactivate(c)">
                        Deaktiviraj
                    </button>
                </div>
            </div>
        </div>
    `
})
export class CardsComponent implements OnInit {
    cards: Card[] = [];
    members: Member[] = [];
    form: any = {
        cardId: '',
        memberId: null
    };

    constructor(private api: ApiService) {}

    ngOnInit() {
        this.load();

        this.api.members().subscribe(x => {
            this.members = x;
            this.form.memberId = x[0]?.id ?? null;
        });
    }

    load() {
        this.api.cards().subscribe(x => this.cards = x);
    }

    create() {
        if (!this.form.cardId || !this.form.memberId) {
            alert('Unesi Card ID i izaberi člana.');
            return;
        }

        this.api.createCard(this.form).subscribe(() => {
            this.form.cardId = '';
            this.load();
        });
    }

    deactivate(c: Card) {
        if (!c.id) return;

        if (!confirm(`Da li želiš da deaktiviraš karticu ${c.cardId}?`)) {
            return;
        }

        this.api.deactivateCard(c.id).subscribe(() => this.load());
    }
}