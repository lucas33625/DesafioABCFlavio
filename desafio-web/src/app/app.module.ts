import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClienteService } from './clientes/clientes.service';
import {ClienteModule} from "./clientes/cliente.module";
import {ToastModule} from "primeng/toast";
import {PaginatorModule} from "primeng/paginator";
import {MessageService} from "primeng/api";
import { LoginModule } from "./login/login.module";
import { ProgressSpinnerModule } from 'primeng/progressspinner';


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
    ProgressSpinnerModule
  ],
  providers: [ClienteService, MessageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
