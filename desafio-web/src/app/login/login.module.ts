import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login.component';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { ToastModule } from 'primeng/toast';
import {RouterModule} from "@angular/router"; // Importando o ToastModule

@NgModule({
  declarations: [LoginComponent], // Declarando o LoginComponent
  imports: [
    CommonModule,               // Importando CommonModule
    ButtonModule,               // Importando o módulo do botão
    InputTextModule,            // Importando o módulo do InputText
    FormsModule,                // Importando o módulo de formulários
    ToastModule,
    RouterModule,
    // Importando o ToastModule para notificações
  ],
  exports: [LoginComponent]      // Exportando para ser usado fora do módulo
})
export class LoginModule { }
