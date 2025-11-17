import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Colaborador {
  id: number;
  nome: string;
  cpf: string;
}

@Injectable({ providedIn: 'root' })
export class ColaboradorService extends ApiService {
  constructor(http: HttpClient) { super(http); }

  criar(nome: string, cpf: string): Observable<Colaborador> {
    return this.http.post<Colaborador>(`${this.base}/colaboradores`, { nome, cpf });
  }

  listar(nome?: string, cpf?: string): Observable<Colaborador[]> {
    let params = new HttpParams();
    if (nome) params = params.set('nome', nome);
    if (cpf) params = params.set('cpf', cpf);
    return this.http.get<Colaborador[]>(`${this.base}/colaboradores`, { params });
  }

  excluir(id: number) {
    return this.http.delete(`${this.base}/colaboradores/${id}`);
  }
}
