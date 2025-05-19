import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private apiUrl = 'http://localhost:8080/api/clientes';

  constructor(private http: HttpClient) {}

  buscarClientePorNome(nome: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/clientes/buscar`, {
      params: { nome }
    });
  }

  getClientes(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }

  addCliente(cliente: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, cliente);
  }

  deleteCliente(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  updateClient(cliente: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${cliente.id}`, cliente);
  }

  getClienteById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  // Gera relatório completo em PDF
  gerarPDF(): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/relatorio/pdf`, {
      responseType: 'blob'
    });
  }

  // Gera DOCX de um cliente individual
  gerarDOCX(clienteId: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/${clienteId}/relatorio/docx`, {
      responseType: 'blob'
    });
  }

  checkEmailExists(email: string, id?: number): Observable<boolean> {
    let params = `email=${email}`;
    if (id !== null && id !== undefined) {
      params += `&id=${id}`;
    }
    return this.http.get<boolean>(`${this.apiUrl}/email-existe?${params}`);
  }
}
