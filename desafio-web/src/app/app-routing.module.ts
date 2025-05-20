import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ClientesComponent } from './clientes/clientes.component';
import {CadastroClienteComponent} from "./clientes/cadastro-cliente/cadastro-cliente.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {GraficoLineComponent} from "./dashboard/grafico-line/grafico-line.component";
import {GraficoPieComponent} from "./dashboard/grafico-pie/grafico-pie.component";
import {GraficoDoughnutComponent} from "./dashboard/grafico-doughnut/grafico-doughnut.component";
import {AuthGuard} from "./AuthGuard.service";


const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'clientes', component: ClientesComponent, canActivate: [AuthGuard]  } ,
  { path: 'clientes/cadastro', component: CadastroClienteComponent, canActivate: [AuthGuard]  },
  { path: 'clientes/editar/:id', component: CadastroClienteComponent, canActivate: [AuthGuard]  },
  { path: 'dashboard-bar', component: DashboardComponent, canActivate: [AuthGuard]  },
  { path: 'dashboard-line', component: GraficoLineComponent, canActivate: [AuthGuard]  },
  { path: 'dashboard-pie', component: GraficoPieComponent, canActivate: [AuthGuard]  },
  { path: 'dashboard-doughnut', component: GraficoDoughnutComponent, canActivate: [AuthGuard]  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
