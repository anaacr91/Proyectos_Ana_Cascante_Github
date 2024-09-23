/// <reference types="cypress" />
describe('Tables, mouse hover', function(){
    it('Tables, mouse hover', function(){
        cy.visit('https://rahulshettyacademy.com/AutomationPractice/')

        //Tables  - We have to iterate the column we want the information
        cy.get('tr td:nth-child(2)').each(($e1, index, $list) => {
            const text=$e1.text()
            if(text.includes('Python')){
                cy.get('tr td:nth-child(2)').eq(index).next().then(function(price){ //get the element again to apply the next() function to access next column
                    const priceText=price.text()
                    expect(priceText).to.equal('25')
                })
            }
        })


        //Mouse hover
        cy.get('#mousehover').invoke('show')  //invoke show form jQuery to show the hidden elements of the menu
        cy.contains('Top').click()  //click on the Top option
        cy.url().should('include','top') //check if the url contains the word top

        //cy.contains('Top').click({force:true}) //Another option: force the click to a hidden element (not recomended)
    })
})