import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ClientesComponent } from './clientes/clientes.component';
import {CadastroClienteComponent} from "./clientes/cadastro-cliente/cadastro-cliente.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {GraficoLineComponent} from "./dashboard/grafico-line/grafico-line.component";
import {GraficoPieComponent} from "./dashboard/grafico-pie/grafico-pie.component";
import {GraficoDoughnutComponent} from "./dashboard/grafico-doughnut/grafico-doughnut.component";


const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'clientes', component: ClientesComponent } ,
  { path: 'clientes/cadastro', component: CadastroClienteComponent },
  { path: 'clientes/editar/:id', component: CadastroClienteComponent },
  { path: 'dashboard-bar', component: DashboardComponent },
  { path: 'dashboard-line', component: GraficoLineComponent },
  { path: 'dashboard-pie', component: GraficoPieComponent },
  { path: 'dashboard-doughnut', component: GraficoDoughnutComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
