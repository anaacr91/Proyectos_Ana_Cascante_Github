/// <reference types="cypress" />
/// <reference types="cypress-iframe" />
import 'cypress-iframe'

describe('Frames, child windows & Calendars', function(){
    it('Frames, child windows & Calendars', function(){

        //Frames   -->WE have to install the cypress-iframe plugin: 'npm install -D cypress-iframe' in terminal, then import it in the test
        cy.visit('https://rahulshettyacademy.com/AutomationPractice/')
        cy.frameLoaded('#courses-iframe') //load the iframe object
        cy.iframe().find('a[href*="mentorship"]').eq(0).click() //find the element in the iframe and click on it. We find more than 1 element, so we select the first one (its the one we want).
        cy.iframe().find('h1[class*="pricing-title"]').should('have.length', 2) //check if there are 2 elements with the class pricing-title
        

        //Calendars
        cy.visit('https://rahulshettyacademy.com/sleniumPractise/#/offers')

        //we want to select a date in the calendar
        const day='15'
        const month='6'
        const year='2027'
        const expectedList=[month, day, year]
        cy.get('.react-date-picker_inputGroup').click()  //click to open the calendar
        cy.get('.react-calendar_navigation_label').click()  //click to change the month using partial classname
        cy.get('.react-calendar_navigation_label').click() //again
        cy.contains('button',year).click() //click on the year
        cy.get('.react-calendar_year-view_months_month').eq(Number(month)-1).click() //click on the 6th month(june) that we selected
        cy.contains('abbr',day).click() //click on the day we selected
        //Assertions to check if the date is correct: we have to watch element by element of the date. not he whole date
        cy.get('.react-date-picker_inputGroup_input').each(($el,index)=>{
            cy.wrap($el).invoke('value').should('eq',expectedList[index])
        })
    })
})