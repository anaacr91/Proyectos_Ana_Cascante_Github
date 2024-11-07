/// <reference types="Cypress" />

describe('Handling Child Windows',()=>{
    it('Should Handle child windows',()=>{
        cy.visit("https://rahulshettyacademy.com/AutomationPractice/")

        //Cypress cant work in a new tab,so we have to open at the same tab
        cy.get('#opentab').invoke('removeAttr','target').click();
        
        //We have to tell cypress thet we change the origin directory, and we will work there.
        cy.origin('https://www.qaclickacademy.com',()=>{
            cy.get("#navbarSupportedContent a[href*='about']").click();
            cy.get(".mt-50 h2").should('contain','QAClick Academy');
        })
    })
})