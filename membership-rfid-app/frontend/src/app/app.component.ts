import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from './services';

@Component({
  selector:'app-root',
  standalone:true,
  imports:[CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  template:`
    <ng-container *ngIf="auth.isLoggedIn(); else guest">
      <div class="layout">
        <aside class="sidebar">
          <div class="brand">Тeretana</div>
          <nav class="nav">
            <a routerLink="/" routerLinkActive="active" [routerLinkActiveOptions]="{exact:true}">Dashboard</a>
            <a routerLink="/members" routerLinkActive="active">Članovi</a>
            <a routerLink="/plans" routerLinkActive="active">Tipovi članarina</a>
            <a routerLink="/cards" routerLinkActive="active">Kartice</a>
            <a routerLink="/logs" routerLinkActive="active">Ulazi</a>
          </nav>
          <button class="btn secondary" (click)="auth.logout()">Odjava</button>
        </aside>
        <main class="content"><router-outlet/></main>
      </div>
    </ng-container>
    <ng-template #guest><router-outlet/></ng-template>
  `
})
export class AppComponent {
  constructor(public auth: AuthService) {}
}