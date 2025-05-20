import { Component, OnInit } from '@angular/core';
import {ClienteService} from "../clientes/clientes.service";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  clientes: any[] = [];

  data: any

  constructor(
    private clienteService: ClienteService,
    private messageService: MessageService
  ) {
    this.data = {
      labels: ['Criança', 'Adolescente', 'Jovem', 'Adulto', 'Idoso'],
      datasets: [
        {
          label: 'Quantidade de Pessoas em cada faixa',
          backgroundColor:  [
            '#42A5F5', // azul
            '#66BB6A', // verde
            '#FFA726', // laranja
            '#EF5350', // vermelho
            '#AB47BC'  // roxo
          ],
          borderColor: '#1E88E5',
          data: []
        },
      ]
    }
  }

  ngOnInit() {
    setTimeout(() => {
      this.clienteService.getClientes().subscribe(clientes => {
        this.grafico(clientes);
      });
    }, 5000);
  }


  grafico(clientes: any[]) {
    const faixas = {
      Crianca: 0,      // 0-12 anos
      Adolescente: 0,  // 13-17 anos
      Jovem: 0,        // 18-29 anos
      Adulto: 0,       // 30-59 anos
      Idoso: 0         // 60+ anos
    };

    clientes.forEach(cliente => {
      const idade = cliente.age;

      if (idade <= 12) faixas.Crianca++;
      else if (idade <= 17) faixas.Adolescente++;
      else if (idade <= 29) faixas.Jovem++;
      else if (idade <= 59) faixas.Adulto++;
      else faixas.Idoso++;
    });

    this.data.datasets[0].data = [
      faixas.Crianca,
      faixas.Adolescente,
      faixas.Jovem,
      faixas.Adulto,
      faixas.Idoso
    ];
  }
}
