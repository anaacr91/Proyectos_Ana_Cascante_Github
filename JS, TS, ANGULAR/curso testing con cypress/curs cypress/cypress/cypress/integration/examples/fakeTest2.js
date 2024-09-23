/// <reference types="cypress" />

describe('My first test mocking https petitions', function(){
    
    it ('My second test mocking https petitions', function(){

        cy.visit("https://rahulshettyacademy.com/angularAppdemo/")

        cy.intercept('GET', 'https://rahulshettyacademy.com/Library/GetBook.php?AuthorName=shetty', (req)=>{  //We intercept the call to the URL to change the request
            req.url='https://rahulshettyacademy.com/Library/GetBook.php?AuthorName=malhotra'  //we change the URL of the request
            req.continue((res)=>{ //we continue with the request
                //expect(res.statusCode).toequal(403) //we expect the status code to be 403
            })
        }).as('dummyURL') //we give a name to the interception

        cy.get("button[class='btn btn-primary']").click() //we click on the button to do the call
        cy.wait('@dummyURL') //we wait for the interception to happen to resolve the promise
    })
})