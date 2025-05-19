import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { ClientesComponent } from './clientes.component';
import { ClienteService } from './clientes.service';
import {ProgressSpinnerModule} from "primeng/primeng";
import { CadastroClienteComponent } from './cadastro-cliente/cadastro-cliente.component';

@NgModule({
  declarations: [
    ClientesComponent,
    CadastroClienteComponent,
  ],
    imports: [
        CommonModule,
        FormsModule,
        ButtonModule,
        InputTextModule,
        TableModule,
        ToastModule,
        BrowserAnimationsModule,
        ProgressSpinnerModule
    ],
  providers: [ClienteService, MessageService],
  exports: [ClientesComponent]
})
export class ClienteModule { }
