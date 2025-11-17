
import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Colaborador, ColaboradorService } from '../../services/colaborador.service';
import { OpcaoService } from '../../services/opcao.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-opcao-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './opcao-form.component.html',
  styleUrls: ['./opcao-form.component.css']
})
export class OpcaoFormComponent implements OnInit {
  msg = '';
  colaboradores: Colaborador[] = [];
  itensJaEscolhidos: Set<string> = new Set();
    get itensJaEscolhidosStr(): string {
      return Array.from(this.itensJaEscolhidos).join(', ');
    }

  form = this.fb.group({
    colaboradorId: [null as number | null, [Validators.required]],
    dataDoCafe: ['', [Validators.required]],
    item: ['', [Validators.required, Validators.minLength(2)]]
  });

  constructor(private fb: FormBuilder,
              private colabService: ColaboradorService,
              private opcaoService: OpcaoService) {}

  ngOnInit(): void {
    this.colabService.listar().subscribe(list => this.colaboradores = list);
    this.form.controls.dataDoCafe.valueChanges.subscribe(dateStr => {
      this.itensJaEscolhidos.clear();
      if (dateStr) {
        this.opcaoService.listarPorData(dateStr).subscribe(list => {
          list.forEach(o => this.itensJaEscolhidos.add(o.item));
        });
      }
    });
  }

  submit() {
    if (this.form.invalid) return;
    const { colaboradorId, dataDoCafe, item } = this.form.value;
    const normalized = (item || '').trim().toLowerCase();
    if (this.itensJaEscolhidos.has(normalized)) {
      this.msg = 'Item já escolhido nessa data.';
      return;
    }
    this.opcaoService.criar(colaboradorId!, dataDoCafe!, item!).subscribe({
      next: () => { this.msg = 'Opção adicionada!'; this.form.controls.item.reset(); },
      error: (err) => this.msg = err.error?.messages?.[0] || 'Erro ao cadastrar opção'
    });
  }
}