import { Component } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { ColaboradorService } from '../../services/colaborador.service';
import { cpf11DigitsValidator } from '../../validators/cpf.validator';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-colaborador-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './colaborador-form.component.html',
  styleUrls: ['./colaborador-form.component.css']
})
export class ColaboradorFormComponent {
  msg = '';
  form = this.fb.group({
    nome: ['', [Validators.required, Validators.minLength(2)]],
    cpf: ['', [Validators.required, cpf11DigitsValidator]]
  });

  constructor(private fb: FormBuilder, private service: ColaboradorService) {}

  submit() {
    if (this.form.invalid) return;
    const { nome, cpf } = this.form.value;
    this.service.criar(nome!, cpf!).subscribe({
      next: () => { this.msg = 'Colaborador cadastrado!'; this.form.reset(); },
      error: (err) => this.msg = err.error?.messages?.[0] || 'Erro ao cadastrar'
    });
  }
}
