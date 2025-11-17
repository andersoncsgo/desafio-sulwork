import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Opcao {
  id: number;
  colaboradorId: number;
  colaboradorNome: string;
  cpf: string;
  dataDoCafe: string;
  item: string;
  trouxe: boolean | null;
}

@Injectable({ providedIn: 'root' })
export class OpcaoService extends ApiService {
  constructor(http: HttpClient) { super(http); }

  criar(colaboradorId: number, dataDoCafe: string, item: string): Observable<Opcao> {
    return this.http.post<Opcao>(`${this.base}/opcoes`, { colaboradorId, dataDoCafe, item });
  }

  listarPorData(data: string): Observable<Opcao[]> {
    const params = new HttpParams().set('data', data);
    return this.http.get<Opcao[]>(`${this.base}/opcoes`, { params });
  }

  marcarTrouxe(id: number, trouxe: boolean) {
    return this.http.patch(`${this.base}/opcoes/${id}/marcar-trouxe`, { trouxe });
  }

  excluir(id: number) { return this.http.delete(`${this.base}/opcoes/${id}`); }
}
