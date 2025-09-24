import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private apiUrl = 'http://localhost:8080/produtos';

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os produtos do sistema
   * (usando o token salvo no sessionStorage para autenticação)
   */
  getAllProducts(): Observable<any> {
    // Recupera o token salvo após o login
    const token = sessionStorage.getItem('token');

    // Monta os headers com o token JWT
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    // Faz a chamada GET com os headers de autenticação
    return this.http.get(this.apiUrl, { headers });
  }

  /**
   * Lista apenas os produtos do usuário logado
   * (se você tiver esse endpoint no backend)
   */
  getMyProducts(): Observable<any> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.get(`${this.apiUrl}/me`, { headers });
  }
}
