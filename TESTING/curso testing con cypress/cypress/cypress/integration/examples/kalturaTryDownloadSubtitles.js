/// <reference types="cypress" />

describe('Trying to download subtitles from Kaltura', function(){
    it('Download subtitles', function(){
        cy. visit('https://kmc.kaltura.com/index.php/kmcng/login')

        //introduce credentials
        cy.get(':nth-child(3) > .p-inputtext').type('content@digimevo.com')
        cy.get(':nth-child(5) > .p-inputtext').type('Digi@2020')
        cy.get('.kButtonDefault').click()
        
       
        //Next page
        cy.get('body.kOverrideFAIcons.kModal:nth-child(2) kpopupwidget.opened:nth-child(24) div.kPopupWidget > i.kCloseBtn.kIconclose_small.closeBtnInside.ng-star-inserted').click()
        cy.get(':nth-child(2) > :nth-child(3) > .kTitle').click()
        cy.get('ul.ng-star-inserted > :nth-child(7) > div').click()
        cy.get('.kMoreActionsButton').as('btn').click()
        cy.get('@btn').click()
        cy.get('#download').click()
        
    })
})