class HomePage{

    getEditBox(){
        return cy.get('input[name="name"]:nth-child(2)')
    }
    getTwoWayDataBinding(){
        return cy.get(':nth-child(4) > .ng-untouched')
    }
    getGender(){
        return cy.get('select')
    }
    getEntrepreneur(){
        return cy.get('#inlineRadio3')
    }
    getShopTab(){
        return cy.get(':nth-child(2) > .nav-link')
    }

};

export default HomePage; //This way all tests will have access to this class