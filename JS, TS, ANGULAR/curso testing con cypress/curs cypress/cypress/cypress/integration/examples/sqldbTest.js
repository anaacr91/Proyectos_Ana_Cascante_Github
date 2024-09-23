/// <reference types="cypress" />

context('Window',()=>{
    it('Database INteraction', ()=>{
        cy.sqlServer('SELECT * FROM db.persons').then(function(result){
            console.log(result[0]) //prints the first row of the result
            console.log(result[0].firstname) //prints the name of the first row
        })
    })
})