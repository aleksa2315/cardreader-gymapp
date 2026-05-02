import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from './services';

@Component({
    selector: 'app-logs',
    standalone: true,
    imports: [CommonModule],
    template: `
        <div class="top">
            <div>
                <h1>Log ulaza</h1>
                <p class="muted">Poslednjih 50 pokušaja ulaza preko RFID-a</p>
            </div>

            <button class="btn" (click)="load()">Osveži</button>
        </div>

        <div class="card desktop-table">
            <table>
                <thead>
                <tr>
                    <th>Vreme</th>
                    <th>Kartica</th>
                    <th>Član</th>
                    <th>Allowed</th>
                    <th>Razlog</th>
                </tr>
                </thead>

                <tbody>
                <tr *ngFor="let l of logs">
                    <td>{{l.requestTime}}</td>
                    <td>{{l.cardId}}</td>
                    <td>{{l.memberName || '-'}}</td>
                    <td>
          <span class="badge" [class.ok]="l.allowed" [class.bad]="!l.allowed">
            {{l.allowed ? 1 : 0}}
          </span>
                    </td>
                    <td>{{l.reason}}</td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="mobile-list">
            <div class="mobile-item" *ngFor="let l of logs">
                <div class="mobile-item-title">
                    Kartica: {{l.cardId}}
                </div>

                <div class="mobile-item-row">
                    <span class="mobile-item-label">Allowed</span>
                    <span class="mobile-item-value">
        <span class="badge" [class.ok]="l.allowed" [class.bad]="!l.allowed">
          {{l.allowed ? 1 : 0}}
        </span>
      </span>
                </div>

                <div class="mobile-item-row">
                    <span class="mobile-item-label">Član</span>
                    <span class="mobile-item-value">{{l.memberName || '-'}}</span>
                </div>

                <div class="mobile-item-row">
                    <span class="mobile-item-label">Vreme</span>
                    <span class="mobile-item-value">{{l.requestTime}}</span>
                </div>

                <div class="mobile-item-row">
                    <span class="mobile-item-label">Razlog</span>
                    <span class="mobile-item-value">{{l.reason}}</span>
                </div>
            </div>
        </div>
    `
})
export class LogsComponent implements OnInit {
    logs: any[] = [];

    constructor(private api: ApiService) {}

    ngOnInit() {
        this.load();
    }

    load() {
        this.api.logs().subscribe(x => this.logs = x);
    }
}