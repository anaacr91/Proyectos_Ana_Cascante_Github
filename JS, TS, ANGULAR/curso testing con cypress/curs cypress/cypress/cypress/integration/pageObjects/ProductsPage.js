class ProductPage{

    getCheckOutButton(){
        return cy.get('#navbarResponsive > .navbar-nav > .nav-item > .nav-link')
    }

}

export default ProductPage; //export the class to be used in other files