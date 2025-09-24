import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = '';

  constructor(private http: HttpClient) {}

  login(user: { nome: string; senha: string }): Observable<any> {
    return this.http.post('http://localhost:8080/auth/login', user);
  }

  register(user: { nome: string; email: string; senha: string }): Observable<any> {
    return this.http.post('http://localhost:8080/usuarios', user);
  }
}
