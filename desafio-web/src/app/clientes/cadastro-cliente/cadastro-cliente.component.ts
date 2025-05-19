import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Router} from "@angular/router";
import { ActivatedRoute, Route } from '@angular/router';
import {MessageService} from "primeng/api";
import {LoadingService} from "../../loading.service";
import {ClienteService} from "../clientes.service";

@Component({
  selector: 'app-cadastro-cliente',
  templateUrl: './cadastro-cliente.component.html',
  styleUrls: ['./cadastro-cliente.component.scss']
})
export class CadastroClienteComponent implements OnInit {

  cliente = {
    id: null,
    name: '',
    email: '',
    phoneNumber: '',
    age: null
  }

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private clienteService: ClienteService,
    private messageService: MessageService,
    private loadingService: LoadingService
  ) { }

  ngOnInit() {

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.clienteService.getClienteById(+id).subscribe({
        next: (cliente) => {
          console.log('Cliente recebido do backend:', cliente); // 👈 Aqui
          this.cliente = cliente; // Preenche o formulário com os dados do cliente
          this.tituloFormulario = 'Editar Cliente'; // Altera o título do formulário
          this.tituloBotao = 'Confirmar'; // Altera o titulo do botao
        },
        error: () => {
          this.messageService.add({
            severity: 'error',
            summary: 'Erro',
            detail: 'Erro ao carregar os dados do cliente.',
            life: 3000
          });
        }
      });
    }
  }

  tituloBotao: string = 'Adicionar';
  tituloFormulario: string = 'Adicionar Cliente';  // Inicializa com o título padrão

  handleClick() {

    this.loadingService.show(); // Mostra o spinner

    this.clienteService.checkEmailExists(this.cliente.email, this.cliente.id).subscribe({
      next: (emailExists) => {
        if (emailExists) {
          this.messageService.add({
            severity: 'error',
            summary: 'Erro',
            detail: 'Este email já está em uso por outro cliente.',
            life: 3000
          });
        } else {
          if (this.cliente.id) {
            // Atualiza cliente
            this.clienteService.updateClient(this.cliente).subscribe({
              next: () => {
                this.resetForm();
                this.tituloFormulario = 'Editar Cliente';
                this.messageService.add({
                  severity: 'success',
                  summary: 'Atualizado',
                  detail: 'Cliente atualizado com sucesso!',
                  life: 3000
                });
                this.router.navigate(['/clientes']);
                this.loadingService.hide();
              },
              error: () => {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Erro',
                  detail: 'Erro ao atualizar o cliente.',
                  life: 3000
                });
                this.loadingService.hide();
                this.router.navigate(['/clientes']);
              }
            });
          } else {
            // Adiciona cliente
            this.clienteService.addCliente(this.cliente).subscribe({
              next: () => {
                this.messageService.add({
                  severity: 'success',
                  summary: 'Adicionado',
                  detail: 'Cliente adicionado com sucesso!',
                  life: 3000
                });
                this.loadingService.hide();
                this.router.navigate(['/clientes']);
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
    this.cliente = {
      id: null,
      name: '',
      email: '',
      phoneNumber: '',
      age: null
    }
  }

  isFormValid(): boolean {
    return this.cliente.name.trim() !== '' && this.cliente.email.trim() !== '' && this.cliente.phoneNumber.trim() !== '';
  }

  onCancelar() {
    this.router.navigate(['/clientes']);
  }
}
