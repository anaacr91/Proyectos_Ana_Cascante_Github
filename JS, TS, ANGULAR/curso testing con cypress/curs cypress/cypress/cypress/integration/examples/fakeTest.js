/// <reference types="Cypress" />

describe('Mpcking HTTPS responses', function(){

    it('My first test mocking https responses', function(){

        cy.visit("https://rahulshettyacademy.com/angularAppdemo/")

        //cy.intercept({requestObject}, {responseObject}) //this is the structure of the intercept method
        cy.intercept({
            method:'GET',   //cypres will keep an eye on the call ready to do anything
            url: 'https://rahulshettyacademy.com/Library/GetBook.php?AuthorName=shetty'}, {  //We intercept the call to the URL and we change the response
                statusCode: 200,
                body: [{"book_name": "RestAssured with Java", "isbn": "RSU", "aisle": "2301"}] //we can change the body of the response
            }).as('bookretrievals') //we give a name to the interception
        cy.get("button[class='btn btn-primary']").click() //we click on the button to do the call
        cy.wait('@bookretrievals').should(({request, response})=>{
            cy.get('tr').should('have.lenngth', response.body.length+1)
        })//we wait for the interception to happen to resolve the promise
        cy.get('p').should('have.text', 'Oops only 1 Book available') //we validate the text of the response

        //lentgh of the array = rows of the table

    })
})