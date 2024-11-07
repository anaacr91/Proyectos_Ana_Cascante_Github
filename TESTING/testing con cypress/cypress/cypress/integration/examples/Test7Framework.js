/// <reference types="cypress" />
import HomePage from '../pageObjects/HomePage';
import ProductPage from '../pageObjects/ProductsPage';

describe('Test7Framework', () => {
    before(function(){
        //access de data
        cy.fixture('example').then(function(data){
            this.data = data  //this.data is a global variable for the whole class
        })
    })

    it('accessing data from fixtures folder and class Pages(good metthods)', function(){

        

        const homePage = new HomePage(); //we create an object of the class HomePage to get to framework standard
        const productPage = new ProductPage(); //Same here

        cy.visit(Cypress.env('url')+'/angularpractice/') //getting the url from the environment variables
        homePage.getEditBox().type(this.data.name)
        homePage.getGender().select(this.data.gender)
        homePage.getTwoWayDataBinding().should('have.value', this.data.name)
        homePage.getEditBox().should('have.attr', 'minLength','2') //assert one atribute
        homePage.getEntrepreneur().should('be.disabled') //assert atribute disabled in a radio button
        //cy.pause() //pause the execution for debugging

        //we go to the next page
        homePage.getShopTab().click()
        //we use the custom command selectProduct
        //cy.selectProduct('Blackberry') //I'ts like a function. the functoin is situated at support>commands.js
        this.data.productName.forEach(element => { //we use the data from the fixture
            cy.selectProduct(element)
        });
        var sum=0
        cy.get('tr td:nth-child(4) strong').each(($el, index, $list) => {
            const actualText=$el.text()
            var res = actualText.split(' ') //we split the text to get the price
            res = res[1].trim() //we get the price
            sum=Number(sum)+Number(res)
        }).then(function(){cy.log(sum)})
        cy.get('h3 strong').then(function(element){
            const actualText=element.text()
            var res = actualText.split(' ') //we split the text to get the price
            var total = res[1].trim() //we get the price
            expect(Number(total)).to.equal(sum) //we compare the total price with the sum of the prices
        })
        productPage.getCheckOutButton().click()
        cy.contains('Checkout').click()
        cy.get('#country').type('India')
        Cypress.config('defaultCommandTimeout', 8000) //we change the default time for the commands to 8 seconds
        cy.get('.suggestions > ul > li > a').click()
        cy.get('#checkbox2').click({force: true}) //we use force true to click on the checkbox to solutionate the warning of the element being covereed by another
        cy.get('input[type="submit"]').click()
        //cy.get('.alert').should('have.text', 'Success! Thank you! Your order will be delivered in next few weeks :-).')
        cy.get('.alert').then(function(element){ //we use then to get the text of the element because of an assertion issue
            const actualText=element.text()
            expect(actualText.includes('Success')).to.be.true //verufy a text in the element
        })
    })
});