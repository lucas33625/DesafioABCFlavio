import { Component } from '@angular/core';
import { LoadingService } from './loading.service';
import { MenuItem } from 'primeng/api';
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'desafio-web';
  property: any;
  isLoading = false;
  items: MenuItem[];

  constructor(
    private loadingService: LoadingService,
    private router: Router
  ) {
    this.loadingService.loading$.subscribe(loading => {
      this.isLoading = loading;
    });
  }

  ngOnInit() {
    this.items = [
      {
        label: 'Home',
        items: [
          { label: 'home', icon: 'pi pi-home', routerLink:['/clientes'] }
        ]
      },
      {
        label: 'Clientes',
        items: [
          {label: 'Novo Cliente', icon: 'pi pi-fw pi-user-plus', routerLink: ['/clientes/cadastro']}
        ]
      },
      {
        label: 'Dashboard',
        items: [
          { label: 'Dash bar', icon: 'pi pi-chart-bar', routerLink: ['/dashboard-bar'] },
          { label: 'Dash line', icon: 'pi pi-chart-line', routerLink: ['/dashboard-line'] },
          { label: 'Dash pie', icon: 'pi pi-chart-pie', routerLink: ['/dashboard-pie'] },
          { label: 'Dash Doughnut', icon: 'pi pi-chart-scatter', routerLink: ['/dashboard-doughnut'] },
        ]
      }
    ]
  }



}
