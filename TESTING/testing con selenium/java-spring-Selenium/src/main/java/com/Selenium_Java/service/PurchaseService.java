package com.Selenium_Java.service;

import com.Selenium_Java.model.Product;
import com.Selenium_Java.model.Purchase;
import com.Selenium_Java.repository.ProductRepository;
import com.Selenium_Java.repository.PurchaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor//-> genera el constructor con los atributos final
public class PurchaseService {

    private final PurchaseRepository purchaseRepository; // Repository instance operaciones crud PurchaseService
    private final ProductRepository productRepository; // Repository instance operaciones crud ProductRepository

    /*Constructor manual-> sin @RequiredArgsConstructor de lombok
    public PurchaseService(PurchaseRepository purchaseRepository, ProductRepository productRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
    }*/

    public Purchase getPurchaseById (Long id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Compra no encontrada."));
    }
    public List<Purchase> getAllPurchases() {//devuelve todas las compras
        return purchaseRepository.findAll(); // Fetch all purchases
    }
    public List<Purchase> getPurchasesByEmail(String email) {//devuelve cosass filtradas por email
        return purchaseRepository.findByEmail(email); // Fetch purchases by email
    }

    @Transactional
    //hacer que el metodo completo se ejecute como 1 transaccion: o se ejecuta todas las operaciones de forma exitosa o ninguna de ellas se aplique
    //que no hagamos cosas mal que no podamos continuar, y lo hecho se aplique-> por ello debe de ser o tod-o o nada
    public Purchase makePurchase(String email, Long productId, Integer quantity) {
        //validaciones-> 1º: validar que la cantidad sea positiva
        if (quantity<=0){
        throw new IllegalArgumentException("La cantidad debe ser positiva.");
        }
        //buscar el producto por id
        Product product = productRepository.findById(productId)//2ª verificar si existe
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado."));
        // 3ª Verificar si hay suficiente stock
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Stock insuficiente para el producto seleccionado.");
        }
        //si existe y hay stock y es positivo-> calcular el precio total
        Double totalPrice = product.getPrice() * quantity;
        //aplicar dto 10% si la cantidad es mayor a 10
        if (quantity >= 10){
            totalPrice *= 0.9;
        }
        //crear la compra
        Purchase purchase = Purchase.builder().email(email).product(product).quantity(quantity)
                .totalPrice(totalPrice).purchaseDate(LocalDateTime.now()).build();

        //guardar la compra en BDD para poder devolverla de la BD luego
        Purchase savedPurchaseBD = purchaseRepository.save(purchase);

        //actualizar el stock del producto actual, restando la cantidad comprada al stock actual
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);//guarda los cambios en el producto actual del stock/quantity
        //devolver la compra
        return savedPurchaseBD;
    }

}
