import { AbstractControl, ValidationErrors } from '@angular/forms';

export function cpf11DigitsValidator(control: AbstractControl): ValidationErrors | null {
  const v = (control.value || '').toString();
  if (!/^\d{11}$/.test(v)) return { cpf11digits: true };
  return null;
}
