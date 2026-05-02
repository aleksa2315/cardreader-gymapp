import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from './services';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [CommonModule, FormsModule],
    template: `
        <div class="login-wrap">
            <div class="card login">
                <h2>Prijava</h2>
                <p class="muted">Admin panel za članarine i RFID pristup</p>

                <label>Korisničko ime</label>
                <input [(ngModel)]="username" autocomplete="username">

                <label style="margin-top:12px">Lozinka</label>
                <input
                        type="password"
                        [(ngModel)]="password"
                        autocomplete="current-password"
                        (keyup.enter)="login()"
                >

                <p *ngIf="error" class="badge bad" style="margin-top:12px">
                    {{error}}
                </p>

                <button class="btn" style="width:100%;margin-top:16px" (click)="login()">
                    Uloguj se
                </button>
            </div>
        </div>
    `
})
export class LoginComponent {
    username = 'admin';
    password = 'admin123';
    error = '';

    constructor(
        private auth: AuthService,
        private router: Router
    ) {}

    login() {
        this.error = '';

        this.auth.login(this.username, this.password).subscribe({
            next: () => this.router.navigateByUrl('/'),
            error: () => this.error = 'Pogrešan login'
        });
    }
}