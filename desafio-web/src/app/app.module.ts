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
import {MenuModule} from 'primeng/menu';
import { NgxMaskModule } from 'ngx-mask';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { DashboardComponent } from './dashboard/dashboard.component';
import {ChartModule} from "primeng/chart";
import { GraficoLineComponent } from './dashboard/grafico-line/grafico-line.component';
import { GraficoPieComponent } from './dashboard/grafico-pie/grafico-pie.component';
import { GraficoDoughnutComponent } from './dashboard/grafico-doughnut/grafico-doughnut.component';



@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    GraficoLineComponent,
    GraficoPieComponent,
    GraficoDoughnutComponent,
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
    ProgressSpinnerModule,
    MenuModule,
    BrowserAnimationsModule,
    NgxMaskModule.forRoot(),
    ChartModule
  ],
  providers: [ClienteService, MessageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
