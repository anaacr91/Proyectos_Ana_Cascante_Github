Feature: borrar un producto
Como usuario quiero borrar un producto para que no aparezca en la lista de productos

Scenario: Borrar un producto
Given que estoy en la pantalla de ProductList
When hago click en el botón de borrar producto
# Then debo ver la pantalla de confirmación de borrado
# When hago click en el botón de confirmación de borrado
Then debo ver la pantalla de ProductList
And debo ver la lista de productos sin el producto borrado