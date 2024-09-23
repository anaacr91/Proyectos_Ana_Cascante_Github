/// <reference types="cypress" />
const neatCSV = require('neat-csv')
let productName
describe('JWT Session Test', ()=>{
    it('is logged in thorugh local storage', ()=>{

        cy.LoginAPI().then(function(){ //This is how we call the custom command to login to the API
            cy.visit('https://rahulshettyacademy.com/client',{ //This is how we visit the page doing something before
                onBeforeLoad: function(window){
                    window.localStorage.setItem('token', Cypress.env('token')) //This is how we set the token in the session cookie
                }
            })
            cy.get('.card-body b').eq(1).then(function(element){
                productName= element.text()
            })
            cy.get('.card-body button:last-of-type').eq(1).click()
            cy.get('[routerlink*="cart"]').click() //This is how we click on the cart we use a partial attribute match
            cy.contains('Checkout').click() //This is how we click on the checkout button using the text of the button
            cy.get('[placeholder*="Country"]').type('ind')
            cy.get('.ta-results button').each(($el, $index, $list)=>{
                if($el.text() === ' India'){
                    cy.wrap($el).click() //This is how we click on the country we use the wrap command to wrap the element
                }
            })
            cy.get('.action__submit').click()
            cy.wait(3000)
            cy.contains('CSV').click() //This is how we click on the CSV button using the text of the button

            //We want to read snd process the data of the file
            cy.readFile(Cypress.config("fileServerFolder")+"/cypress/downloads/order-invoice_bernesimobosch.csv").then(async function(text){ //This is how we read the CSV file using the readFile command
                const csv= await neatCSV(text) //This is how we read the CSV file using the neatCSV library
                console.log(csv)
                const actualProductCSV= csv[0]['Product Name']
                expect(productName).to.equal(actualProductCSV) //This is how we compare the product name in the CSV file with the product name in the application
                
            })
            
        })
    })
})