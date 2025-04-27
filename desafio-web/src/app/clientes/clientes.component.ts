import { Component, OnInit } from '@angular/core';
import { ClienteService } from './clientes.service';
import { MessageService } from 'primeng/api';
import { saveAs } from 'file-saver';
import {Router} from "@angular/router";
import {LoadingService} from "../loading.service";

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.scss'],
})
export class ClientesComponent implements OnInit {
  clientes: any[] = [];
  clientesFiltrados: any [] = [];
  nome: string = '';
  email: string = '';
  telefone: string = '';
  clienteId: number | null = null;
  nomePesquisa: string = '';

  constructor(
    private router: Router,
    private clienteService: ClienteService,
    private messageService: MessageService,
    private loadingService: LoadingService
  ) {}

  ngOnInit() {
    this.list();
  }

  list() {
    this.clienteService.getClientes().subscribe(data => {
      this.clientes = data;
    });
  }

  updateClient(cliente: any) {
    this.clienteId = cliente.id;
    this.nome = cliente.name;
    this.email = cliente.email;
    this.telefone = cliente.phoneNumber;

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

  isFormValid(): boolean {
    return this.nome.trim() !== '' && this.email.trim() !== '' && this.telefone.trim() !== '';
  }

  // isLoading = false;

  handleClick() {

    this.loadingService.show(); // Mostra o spinner

    // Simula algo assíncrono (tipo um HTTP request)
    setTimeout(() => {
      this.loadingService.hide(); // Esconde o spinner
    }, 2000);

    const cliente = {
      id: this.clienteId,
      name: this.nome,
      email: this.email,
      phoneNumber: this.telefone
    };

    this.clienteService.checkEmailExists(cliente.email, cliente.id).subscribe({
      next: (emailExists) => {
        if (emailExists) {
          this.messageService.add({
            severity: 'error',
            summary: 'Erro',
            detail: 'Este email já está em uso por outro cliente.',
            life: 3000
          });
        } else {
          if (this.clienteId) {
            // Atualiza cliente
            this.clienteService.updateClient(cliente).subscribe({
              next: () => {
                this.list();
                this.resetForm();
                this.messageService.add({
                  severity: 'success',
                  summary: 'Atualizado',
                  detail: 'Cliente atualizado com sucesso!',
                  life: 3000
                });
              },
              error: () => {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Erro',
                  detail: 'Erro ao atualizar o cliente.',
                  life: 3000
                });
              }
            });
          } else {
            // Adiciona cliente
            this.clienteService.addCliente(cliente).subscribe({
              next: () => {
                this.list();
                this.resetForm();
                this.messageService.add({
                  severity: 'success',
                  summary: 'Adicionado',
                  detail: 'Cliente adicionado com sucesso!',
                  life: 3000
                });
              },
              error: () => {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Erro',
                  detail: 'Erro ao adicionar o cliente.',
                  life: 3000
                });
              }
            });
          }
        }
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Erro ao verificar email.',
          life: 3000
        });
      }
    });
  }


  resetForm() {
    this.clienteId = null;
    this.nome = '';
    this.email = '';
    this.telefone = '';
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

  logout() {
    localStorage.removeItem('usuario');
    this.messageService.add({
      severity: 'success',
      summary: 'Logout realizado',
      detail: 'Você foi desconectado com sucesso!',
      life: 3000
    });


    this.router.navigate(['/login']);
  }

}
