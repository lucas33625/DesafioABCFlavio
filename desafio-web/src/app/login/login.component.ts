import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { MessageService } from 'primeng/api';
import {AuthService} from "../AuthService.service";

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
    private auth: AuthService,
    private router: Router,
    private messageService: MessageService
    ) {}

  onLogin() {
    this.auth.login({ usuario: this.usuario, senha: this.senha}).subscribe({
      next: (res) => {
        this.auth.salvarToken(res.token);
        this.messageService.add({
          severity: 'success',
          summary: 'Login Realizado',
          detail: 'Login bem-sucedido!',
          life: 3000
        })
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
    })
  }
}
