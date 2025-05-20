// auth.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {MessageService} from "primeng/api";
import {Router} from "@angular/router";

@Injectable({ providedIn: 'root' })
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient,
              private messageService: MessageService,
              private router: Router) {}

  login(credentials: { usuario: string, senha: string }) {
    return this.http.post<{ token: string }>(`${this.baseUrl}/login`, credentials);
  }

  salvarToken(token: string) {
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
    this.messageService.add({
      severity: 'success',
      summary: 'Logout realizado',
      detail: 'Você foi desconectado com sucesso!',
      life: 3000
    });
    this.router.navigate(['/login']);
  }

  isLogado(): boolean {
    return !!this.getToken();
  }
}
