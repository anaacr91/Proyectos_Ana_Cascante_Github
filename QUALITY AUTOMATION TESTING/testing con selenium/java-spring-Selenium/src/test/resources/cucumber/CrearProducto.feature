Feature: Crear nuevo producto
Desde el ProductList se debe poder navegar a la pantalla de creación de un nuevo producto

Scenario: Navegar a la pantalla de creación de un nuevo producto
Given que estoy en la pantalla de ProductList
When hago click en el botón de crear nuevo producto
Then debo ver la pantalla de creación de un nuevo producto