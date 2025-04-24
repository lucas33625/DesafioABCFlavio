import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { HttpClientModule } from '@angular/common/http';
import { TableModule } from 'primeng/table';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {FormsModule} from "@angular/forms";
import { ClientesComponent } from './clientes/clientes.component';
import { ClienteService } from './clientes/clientes.service';
import {ClienteModule} from "./clientes/cliente.module";
import {ToastModule} from "primeng/toast";
import { LoginComponent } from './login/login.component';
import {PaginatorModule} from "primeng/paginator";
import {MessageService} from "primeng/api";
import { LoginModule } from "./login/login.module";


@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ClienteModule,
    PaginatorModule,
    ButtonModule,
    InputTextModule,
    ToastModule,
    LoginModule,
  ],
  providers: [ClienteService, MessageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
