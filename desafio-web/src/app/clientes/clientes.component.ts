import { Component, OnInit } from '@angular/core';
import { ClienteService } from './clientes.service';
import { MessageService } from 'primeng/api';
import { saveAs } from 'file-saver';


@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.scss'],
  providers: [MessageService]
})
export class ClientesComponent implements OnInit {
  clientes: any[] = [];
  nome: string = '';
  email: string = '';
  telefone: string = '';
  clienteId: number | null = null;

  constructor(
    private clienteService: ClienteService,
    private messageService: MessageService
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

  handleClick() {
    const cliente = {
      id: this.clienteId,
      name: this.nome,
      email: this.email,
      phoneNumber: this.telefone
    };

    if (this.clienteId) {
      // Atualiza o cliente existente
      this.clienteService.updateClient(cliente).subscribe(() => {
        this.list();
        this.resetForm();
        this.messageService.add({
          severity: 'success',
          summary: 'Atualizado',
          detail: 'Cliente atualizado com sucesso!',
          life: 3000
        });
      });
    } else {
      // Adiciona um novo cliente
      this.clienteService.addCliente(cliente).subscribe(() => {
        this.list();
        this.resetForm();
        this.messageService.add({
          severity: 'success',
          summary: 'Adicionado',
          detail: 'Cliente adicionado com sucesso!',
          life: 3000
        });
      });
    }
  }

  resetForm() {
    this.clienteId = null;
    this.nome = '';
    this.email = '';
    this.telefone = '';
  }

  gerarPDF() {
    this.clienteService.gerarPDF().subscribe({
      next: (blob) => {
        saveAs(blob, 'relatorio_clientes.pdf');
        this.messageService.add({
          severity: 'success',
          summary: 'PDF gerado',
          detail: 'Relatório geral baixado com sucesso!',
          life: 3000
        });
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Falha ao gerar relatório PDF',
          life: 3000
        });
      }
    });
  }

  gerarDOCX(clienteId: number) {
    this.clienteService.gerarDOCX(clienteId).subscribe({
      next: (blob) => {
        this.downloadArquivo(blob, `cliente_${clienteId}.docx`);
        this.messageService.add({
          severity: 'success',
          summary: 'DOCX gerado',
          detail: `Relatório do cliente ${clienteId} baixado com sucesso!`,
          life: 3000
        });
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: `Erro ao gerar relatório do cliente ${clienteId}`,
          life: 3000
        });
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
}
