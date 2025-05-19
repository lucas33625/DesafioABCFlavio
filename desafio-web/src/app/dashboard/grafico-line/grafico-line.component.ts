import {Component, Input, OnInit} from '@angular/core';
import {MessageService} from "primeng/api";
import {ClienteService} from "../../clientes/clientes.service";

@Component({
  selector: 'app-grafico-line',
  templateUrl: './grafico-line.component.html',
  styleUrls: ['./grafico-line.component.scss']
})
export class GraficoLineComponent implements OnInit {
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
          borderColor: '#4bc0c0',
          data: []
        },
      ]
    }
  }

  ngOnInit() {
    this.clienteService.getClientes().subscribe(clientes => {
      this.grafico(clientes);
    });
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
  selectData(event) {
    this.messageService.add({severity: 'info', summary: 'Data Selected', 'detail': this.data.datasets[event.element._datasetIndex].data[event.element._index]});
  }

}
