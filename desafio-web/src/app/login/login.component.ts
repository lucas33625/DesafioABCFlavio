import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['login.component.scss']
})
export class LoginComponent {
  usuario = '';
  senha = '';

  // Injetando o MessageService no construtor
  constructor(
    private http: HttpClient,
    private router: Router,
    private messageService: MessageService // 👈 Aqui
  ) {}

  login() {
    console.log('Enviando login:', this.usuario, this.senha);

    this.http.post('http://localhost:8080/api/auth/login', {
      usuario: this.usuario,
      senha: this.senha
    }).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Login realizado',
          detail: 'Login bem-sucedido!',
          life: 3000
        });
        this.router.navigate(['/clientes']);
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erro de login',
          detail: 'Usuário ou senha inválidos',
          life: 3000
        });
      }
    });
  }
}
