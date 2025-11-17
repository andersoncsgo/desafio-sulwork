import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ColaboradorFormComponent } from './colaborador-form.component';
import { ColaboradorService } from '../../services/colaborador.service';
import { of, throwError } from 'rxjs';

describe('ColaboradorFormComponent', () => {
  let component: ColaboradorFormComponent;
  let fixture: ComponentFixture<ColaboradorFormComponent>;
  let service: ColaboradorService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ColaboradorFormComponent, ReactiveFormsModule, HttpClientTestingModule]
    }).compileComponents();

    fixture = TestBed.createComponent(ColaboradorFormComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(ColaboradorService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have invalid form when empty', () => {
    expect(component.form.valid).toBeFalsy();
  });

  it('should validate nome with minimum 2 characters', () => {
    const nome = component.form.controls.nome;
    nome.setValue('A');
    expect(nome.invalid).toBeTruthy();
    
    nome.setValue('João');
    expect(nome.valid).toBeTruthy();
  });

  it('should validate cpf with exactly 11 digits', () => {
    const cpf = component.form.controls.cpf;
    cpf.setValue('123');
    expect(cpf.invalid).toBeTruthy();
    
    cpf.setValue('12345678901');
    expect(cpf.valid).toBeTruthy();
  });

  it('should call service.criar on submit with valid form', () => {
    spyOn(service, 'criar').and.returnValue(of({ id: 1, nome: 'João', cpf: '12345678901' }));
    
    component.form.setValue({ nome: 'João', cpf: '12345678901' });
    component.submit();
    
    expect(service.criar).toHaveBeenCalledWith('João', '12345678901');
    expect(component.msg).toBe('Colaborador cadastrado!');
  });

  it('should show error message on submit failure', () => {
    spyOn(service, 'criar').and.returnValue(
      throwError(() => ({ error: { messages: ['CPF já cadastrado'] } }))
    );
    
    component.form.setValue({ nome: 'João', cpf: '12345678901' });
    component.submit();
    
    expect(component.msg).toBe('CPF já cadastrado');
  });
});
