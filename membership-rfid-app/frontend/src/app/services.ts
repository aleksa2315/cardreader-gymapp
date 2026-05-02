import { Injectable } from '@angular/core';
import { HttpClient, HttpInterceptorFn } from '@angular/common/http';
import { Router } from '@angular/router';
import { tap } from 'rxjs';

export const API = 'https://cardreader-gymapp.onrender.com/api';

export interface Member { id?:number; firstName:string; lastName:string; phone?:string; email?:string; note?:string; status?:string; createdAt?:string; }
export interface Plan { id?:number; name:string; code:string; durationDays:number; price:number; active:boolean; }
export interface Card { id?:number; cardId:string; memberId:number; memberName?:string; active?:boolean; issuedAt?:string; deactivatedAt?:string; }
export interface Subscription { id?:number; memberId:number; memberName?:string; planId:number; planName?:string; startDate:string; endDate?:string; price?:number; status?:string; }

@Injectable({providedIn:'root'})
export class AuthService {
  constructor(private http: HttpClient, private router: Router) {}
  login(username:string,password:string){return this.http.post<any>(`${API}/auth/login`,{username,password}).pipe(tap(r=>localStorage.setItem('token',r.token)));}
  logout(){localStorage.removeItem('token');this.router.navigateByUrl('/login');}
  token(){return localStorage.getItem('token');}
  isLoggedIn(){return !!this.token();}
}

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('token');
  if (token) req = req.clone({ setHeaders: { Authorization: `Bearer ${token}` }});
  return next(req);
};

@Injectable({providedIn:'root'})
export class ApiService {
  constructor(private http:HttpClient){}
  dashboard(){return this.http.get<any>(`${API}/dashboard`);}
  members(q=''){return this.http.get<Member[]>(`${API}/members`,{params:q?{q}:{}});}
  saveMember(m:Member){return m.id?this.http.put<Member>(`${API}/members/${m.id}`,m):this.http.post<Member>(`${API}/members`,m);}
  plans(){return this.http.get<Plan[]>(`${API}/plans`);}
  savePlan(p:Plan){return p.id?this.http.put<Plan>(`${API}/plans/${p.id}`,p):this.http.post<Plan>(`${API}/plans`,p);}
  cards(){return this.http.get<Card[]>(`${API}/cards`);}
  createCard(c:{cardId:string;memberId:number}){return this.http.post<Card>(`${API}/cards`,c);}
  deactivateCard(id:number){return this.http.put<Card>(`${API}/cards/${id}/deactivate`,{});}
  subscriptions(memberId:number){return this.http.get<Subscription[]>(`${API}/subscriptions/member/${memberId}`);}
  createSubscription(s:{memberId:number;planId:number;startDate:string;price?:number}){return this.http.post<Subscription>(`${API}/subscriptions`,s);}
  logs(){return this.http.get<any[]>(`${API}/access-logs`);}
}
