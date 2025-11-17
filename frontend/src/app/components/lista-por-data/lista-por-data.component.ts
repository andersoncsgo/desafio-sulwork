import { Component } from '@angular/core';
import { Opcao, OpcaoService } from '../../services/opcao.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-lista-por-data',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './lista-por-data.component.html',
  styleUrls: ['./lista-por-data.component.css']
})
export class ListaPorDataComponent {
  data = new Date().toISOString().substring(0,10);
  lista: Opcao[] = [];
  msg = '';
  todayStr = new Date().toISOString().substring(0,10);

  constructor(private service: OpcaoService) {
    this.buscar();
  }

  buscar() {
    if (!this.data) return;
    this.service.listarPorData(this.data).subscribe({
      next: list => this.lista = list,
      error: () => this.msg = 'Erro ao carregar lista'
    });
  }

  podeMarcar(): boolean {
    return this.data === this.todayStr;
  }

  marcar(o: Opcao, val: boolean) {
    this.service.marcarTrouxe(o.id, val).subscribe({
      next: () => { this.msg = 'Atualizado'; this.buscar(); },
      error: (err) => this.msg = err.error?.messages?.[0] || 'Erro ao atualizar'
    });
  }

  getTrouxeClass(trouxe: boolean | null): string {
    if (trouxe === null) return 'status-pending';
    return trouxe ? 'status-yes' : 'status-no';
  }
}
