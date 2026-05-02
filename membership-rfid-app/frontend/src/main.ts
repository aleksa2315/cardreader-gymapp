import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideRouter, Routes, CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app/app.component';
import { LoginComponent } from './app/login.component';
import { DashboardComponent } from './app/dashboard.component';
import { MembersComponent } from './app/members.component';
import { PlansComponent } from './app/plans.component';
import { CardsComponent } from './app/cards.component';
import { LogsComponent } from './app/logs.component';
import { AuthService, authInterceptor } from './app/services';

const guard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);
  if (auth.isLoggedIn()) return true;
  router.navigateByUrl('/login');
  return false;
};

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: DashboardComponent, canActivate: [guard] },
  { path: 'members', component: MembersComponent, canActivate: [guard] },
  { path: 'plans', component: PlansComponent, canActivate: [guard] },
  { path: 'cards', component: CardsComponent, canActivate: [guard] },
  { path: 'logs', component: LogsComponent, canActivate: [guard] },
  { path: '**', redirectTo: '' }
];

bootstrapApplication(AppComponent, {
  providers: [provideRouter(routes), provideHttpClient(withInterceptors([authInterceptor]))]
}).catch(err => console.error(err));
