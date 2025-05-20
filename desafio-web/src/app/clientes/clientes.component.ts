import { Component, OnInit } from '@angular/core';
import { ClienteService } from './clientes.service';
import { MessageService } from 'primeng/api';
import { saveAs } from 'file-saver';
import {Router} from "@angular/router";
import {LoadingService} from "../loading.service";
import {AuthService} from "../AuthService.service";

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.scss'],
})
export class ClientesComponent implements OnInit {
  clientes: any[] = [];
  clientesFiltrados: any [] = [];
  nomePesquisa: string = '';

  constructor(
    private router: Router,
    private clienteService: ClienteService,
    private messageService: MessageService,
    private loadingService: LoadingService,
    protected auth: AuthService
  ) {}

  ngOnInit() {
    this.loadingService.show()
    setTimeout (() => {
      this.list();
    }, 2000);

  }

  list() {
    this.clienteService.getClientes().subscribe(data => {
      this.clientes = data;
      this.loadingService.hide()
    });
  }

  onClienteSalvo(_: any) {
    this.list(); // Atualiza a lista de clientes
    this.mostrarFormulario = false; // Fecha o formulário, se necessário
  }

  mostrarFormulario = false;

  novoCliente() {
    this.router.navigate(['/clientes/cadastro']);
  }

  updateClient(cliente: any) {
    this.router.navigate(['/clientes/editar', cliente.id]);
  }

  deleteCliente(cliente: any) {
    this.clienteService.deleteCliente(cliente.id).subscribe(() => {
      this.list();
      this.messageService.add({
        severity: 'warn',
        summary: 'Removido',
        detail: 'Cliente excluído com sucesso!',
        life: 3000
      });
    });
  }

  gerarPDF() {
    this.loadingService.show(); // ativa o loading

    this.clienteService.gerarPDF().subscribe({
      next: (blob) => {
        saveAs(blob, 'relatorio_clientes.pdf');
        this.messageService.add({
          severity: 'success',
          summary: 'PDF gerado',
          detail: 'Relatório geral baixado com sucesso!',
          life: 3000
        });
        this.loadingService.hide(); // desativa o loading no sucesso
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Falha ao gerar relatório PDF',
          life: 3000
        });
        this.loadingService.hide(); // desativa o loading também no erro
      }
    });
  }


  gerarDOCX(clienteId: number) {
    this.loadingService.show();
    this.clienteService.gerarDOCX(clienteId).subscribe({
      next: (blob) => {
        this.downloadArquivo(blob, `cliente_${clienteId}.docx`);
        this.messageService.add({
          severity: 'success',
          summary: 'DOCX gerado',
          detail: `Relatório do cliente ${clienteId} baixado com sucesso!`,
          life: 3000
        });
        this.loadingService.hide();
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: `Erro ao gerar relatório do cliente ${clienteId}`,
          life: 3000
        });
        this.loadingService.hide();
      }
    });
  }

  private downloadArquivo(blob: Blob, nomeArquivo: string) {
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = nomeArquivo;
    a.click();
    window.URL.revokeObjectURL(url);
  }

  removerAcentos(str: string): string {
    return str.normalize('NFD').replace(/[\u0300-\u036f]/g, '');
  }

  pesquisar() {

    if (this.nomePesquisa && this.nomePesquisa.trim() !== '') {
      const nomePesquisaSemAcento = this.removerAcentos(this.nomePesquisa.toLowerCase());

      this.clientesFiltrados = this.clientes.filter(cliente =>
        this.removerAcentos(cliente.name.toLowerCase()).includes(nomePesquisaSemAcento)
      );

      if (this.clientesFiltrados.length === 0) {
       this.messageService.add({
          severity: 'info',
          summary: 'Nenhum resultado encontrado',
          detail: 'Nenhum cliente encontrado com esse nome.',
          life: 3000
        });
      }
    } else {
      this.clientesFiltrados = [];
    }
  }

  limparPesquisa() {
    this.nomePesquisa = '';
    this.clientesFiltrados = [];
  }

}
