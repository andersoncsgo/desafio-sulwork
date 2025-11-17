import { cpf11DigitsValidator } from './cpf.validator';

describe('CPF Validator', () => {
  it('valida 11 dígitos', () => {
    const ctrl: any = { value: '12345678901' };
    expect(cpf11DigitsValidator(ctrl)).toBeNull();

    const bad: any = { value: '123' };
    expect(cpf11DigitsValidator(bad)).toEqual({ cpf11digits: true });
  });
});
