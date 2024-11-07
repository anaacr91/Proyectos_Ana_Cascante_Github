import {Given, When, Then} from "@badeball/cypress-cucumber-preprocessor/steps";
import HomePage from '../../../pageObjects/HomePage';
import ProductPage from '../../../pageObjects/ProductsPage';

Given('I open ecommerce page', () => {
    cy.visit(Cypress.env+'automationPractise/')
})

// When I add items to cart
When('I add items to cart', () => {
    homePage.getShopTab().click()
    this.data.productName.forEach(element => { //we use the data from the fixture
        cy.selectProduct(element)
    });
})

// And I do checkout the price
And('I do checkout the price', () => {
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
})

//Then Select the country, submit and verify Thankyou message
Then('Select the country, submit and verify Thankyou message', () => {
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

//When I fill the form details
When('I fill the form details', () => {
    homePage.getEditBox().type(this.data.name)
    homePage.getGender().select(this.data.gender)
})

//Then Validate the forms behaviour
Then('Validate the forms behaviour', () => {
    homePage.getTwoWayDataBinding().should('have.value', this.data.name)
    homePage.getEditBox().should('have.attr', 'minLength','2')
    homePage.getEntrepreneur().should('be.disabled')
})

//And select the shop page
And('select the shop page', () => {
    homePage.getShopTab().click()
})
