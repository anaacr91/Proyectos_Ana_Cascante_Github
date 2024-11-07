/// <reference types="cypress" />

describe('My first Api Test', function(){

    it('Test the api petitions', function(){

        //cy.request(method, url, body)
        cy.request('POST', 'http://216.10.245.166/Library/Addbook.php', { //We create the petition to the URL
            "name": "Learn Appium Automation with Java",
            "isbn": "bcggd",
            "aisle": "227",
            "author": "John foe"
        }).then(function(response){
            expect(response.body).to.have.property('Msg', 'successfully added') //we validate the response
            expect(response.status).to.equal(200) //we validate the status of the response
        })
    })
})