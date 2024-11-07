Feature: End to end Ecommerce validation with our first cucumber test

    We are gonna test the whole process of buying a product in the ecommerce website.

    Scenario: Ecommerce products purchase
        Given I open ecommerce page
        When I add products to cart
        And I do checkout the price
        Then Select the country, submit and verify Thankyou message

    Scenario: Filling the form to shop
        Given I open the ecommerce page
        When I fill the form details
        Then Validate the forms behaviour
        And select the shop page