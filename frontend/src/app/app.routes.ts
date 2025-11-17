import { Routes } from '@angular/router';
import { ColaboradorFormComponent } from './components/colaborador-form/colaborador-form.component';
import { OpcaoFormComponent } from './components/opcao-form/opcao-form.component';
import { ListaPorDataComponent } from './components/lista-por-data/lista-por-data.component';

export const routes: Routes = [
  { path: '', redirectTo: 'lista', pathMatch: 'full' },
  { path: 'colaborador', component: ColaboradorFormComponent },
  { path: 'opcao', component: OpcaoFormComponent },
  { path: 'lista', component: ListaPorDataComponent }
];
