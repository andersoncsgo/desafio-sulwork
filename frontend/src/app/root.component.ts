import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  template: `
  <nav>
    <a routerLink="/lista" routerLinkActive="active">Lista por Data</a>
    <a routerLink="/colaborador" routerLinkActive="active">Cadastro Colaborador</a>
    <a routerLink="/opcao" routerLinkActive="active">Cadastro Opção</a>
  </nav>
  <router-outlet></router-outlet>`,
  styles: []
})
export class AppComponent {}
