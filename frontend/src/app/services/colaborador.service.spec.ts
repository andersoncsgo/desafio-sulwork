import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ColaboradorService, Colaborador } from './colaborador.service';

describe('ColaboradorService', () => {
  let service: ColaboradorService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ColaboradorService]
    });
    service = TestBed.inject(ColaboradorService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should create colaborador via POST', () => {
    const mockColaborador: Colaborador = {
      id: 1,
      nome: 'João Silva',
      cpf: '12345678901'
    };

    service.criar('João Silva', '12345678901').subscribe(colaborador => {
      expect(colaborador).toEqual(mockColaborador);
    });

    const req = httpMock.expectOne(`${service['base']}/colaboradores`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ nome: 'João Silva', cpf: '12345678901' });
    req.flush(mockColaborador);
  });

  it('should list colaboradores via GET', () => {
    const mockList: Colaborador[] = [
      { id: 1, nome: 'João', cpf: '11111111111' },
      { id: 2, nome: 'Maria', cpf: '22222222222' }
    ];

    service.listar().subscribe(list => {
      expect(list.length).toBe(2);
      expect(list).toEqual(mockList);
    });

    const req = httpMock.expectOne(`${service['base']}/colaboradores`);
    expect(req.request.method).toBe('GET');
    req.flush(mockList);
  });

  it('should delete colaborador via DELETE', () => {
    service.excluir(1).subscribe();

    const req = httpMock.expectOne(`${service['base']}/colaboradores/1`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });
});
