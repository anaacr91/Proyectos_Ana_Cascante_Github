package com.company;


import java.util.ArrayList;

public class CascanteAnaAvocadoToast {
    private String bread; //Wheat, Multigrain,
    private String toast; //Lightly toasted, Crunchy,
    private String avocado; //mashed, sliced
    private String topping; //None, Olive oil,
    private String includeSalt;//salt, none
    private String includePepper;//pepper, none
    private double price;

    public CascanteAnaAvocadoToast(String bread, String toast, String avocado, String topping, String includeSalt, String includePepper) {
        this.bread = bread;
        this.toast = toast;
        this.avocado = avocado;
        this.topping = topping;
        this.includeSalt = includeSalt;
        this.includePepper = includePepper;
        this.price = price;
    }

    public CascanteAnaAvocadoToast() {//constructor por defecto
        this.bread = "wheat";
        this.toast = "lightlyToast";
        this.avocado = "mashed";
        this.topping = "noTopping";
        this.includeSalt = "noSalt";
        this.includePepper = "noPepper";
        this.price = 0;
    }

    public void setBread(String bread) {
    this.bread = bread;
    }
    public void setToast(String toast){
    this.toast = toast;
    }
    public void setAvocado(String avocado) {
    this.avocado = avocado;
    }
    public void setTopping (String topping) {
    this.topping = topping;
    }
    public void setIncludeSalt (String includeSalt){
    this.includeSalt = includeSalt;
    }

    public void setIncludePepper (String includePepper){
    this.includePepper = includePepper;
    }

    public void setPrice ( double price){
         this.price = price;
         }

    public void displayInformation () {
          System.out.println("Showing toast Ingredients{" +
          "Bread=" + bread +
          ", Toast='" + this.toast +
          ", avocado=" + avocado +
          ", topping=" + topping +
          ", Salt=" + includeSalt +
          ", Pepper=" + includePepper +
          '}');
                }
    public void calculatePrice () {
            double priceBread = 0;
            if (bread.equalsIgnoreCase("wheat")) {
            priceBread = 2;
            } else if (bread.equalsIgnoreCase("multigrain")) {
            priceBread = 1;
            }
            double priceToast = 0;
            if (this.toast.equalsIgnoreCase("Crunchy")) {
                priceToast = 2;
            } else if (this.toast.equalsIgnoreCase("Ligthly toasted")) {
                priceToast = 1;
            }
            double priceAvocado = 0;
            if (avocado.equalsIgnoreCase("Mashed")) {
                priceAvocado = 2;
            } else if (avocado.equalsIgnoreCase("Sliced")) {
                priceAvocado = 3;
            }
            double priceTopping = 0;
            if (topping.equalsIgnoreCase("None")) {
                priceTopping = 0;
            } else if (topping.equalsIgnoreCase("Olive Oil")) {
                priceTopping = 1;
            }
            double priceSalt = 0;
            if (includeSalt.equalsIgnoreCase("Salt")){
                priceSalt = 0.5;
            } else if (includeSalt.equalsIgnoreCase("None")) {
                priceSalt = 0;
            }
            double pricePepper = 0;
            if (includePepper.equalsIgnoreCase("Pepper")) {
            pricePepper = 0.5;
            } else if (includePepper.equalsIgnoreCase("None")) {
            pricePepper = 0;
            }
            this.price = (priceBread + priceToast + priceAvocado + priceTopping + priceSalt + pricePepper);
                    System.out.println("Showing toast price "
                            +" "+ bread +" "+ priceBread +" "+ toast +" "+ priceToast +" "+ topping +" "+
                            priceTopping +" "+ includeSalt +" "+ priceSalt +" "+ includePepper +" "+ pricePepper +" "+ "Total" +" "+ price);
                }
            }







