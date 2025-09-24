import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PedidoService {
  private apiUrl = 'http://localhost:8080/pedidos';

  constructor(private http: HttpClient) {}

  criarPedido(pedido: { produtoId: number; valor: number }): Observable<any> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.http.post(this.apiUrl, pedido, { headers });
  }

  listarPedidos(): Observable<any> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.get(this.apiUrl, { headers });
  }
}
