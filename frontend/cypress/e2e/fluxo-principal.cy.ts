// frontend/cypress/e2e/fluxo-principal.cy.ts
/// <reference types="cypress" />

describe('Fluxo principal', () => {
  it('cadastra colaborador e opção e bloqueia duplicidade de item por data', () => {
    // Supondo app rodando local em http://localhost:8080 com proxy /api
    cy.visit('http://localhost:8080');

    // cadastra colaborador
    cy.contains('Cadastro Colaborador').click();
    cy.get('input[formcontrolname="nome"]').type('Maria');
    cy.get('input[formcontrolname="cpf"]').type('99988877766');
    cy.contains('Salvar').click();
    cy.contains('Colaborador cadastrado!').should('exist');

    // cadastra opção
    cy.contains('Cadastro Opção').click();
    cy.get('select[formcontrolname="colaboradorId"]').select(1); // pode variar id
    const future = new Date(Date.now()+86400000*5).toISOString().substring(0,10);
    cy.get('input[formcontrolname="dataDoCafe"]').type(future);
    cy.get('input[formcontrolname="item"]').type('Queijo');
    cy.contains('Adicionar').click();
    cy.contains('Opção adicionada!').should('exist');

    // tenta duplicar item
    cy.get('input[formcontrolname="item"]').clear().type('Queijo');
    cy.contains('Adicionar').click();
    cy.contains('Item já escolhido nessa data.').should('exist');
  });
});