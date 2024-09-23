beforeEach(()=>{
    cy.fixture('example').then(function(data){  //mocha does'nt support arrow function
        this.data = data  //this.data is a global variable for the whole class
    })
})