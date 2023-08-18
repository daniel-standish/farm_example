package com.standish.farm.controller;

import com.google.gson.Gson;
import com.standish.farm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/store/add")
    @ResponseBody
    public String createProduct(@RequestParam String name, @RequestParam String type, @RequestParam int price) {
        try {
            productService.createProduct(name, type, price);
            return new Gson().toJson("You created the following product: {" + name + ", " + type + ", " + price + "}");
        } catch (Exception e) {
            return "Could not create the following product: {" + name + ", " + type + ", " + price + "}";
        }
    }

    @GetMapping("/store/list")
    @ResponseBody
    public String listProducts() {
        return new Gson().toJson(productService.listProducts());
    }

    @GetMapping("/store/remove")
    @ResponseBody
    public String removeProduct(@RequestParam Long id) {
        try {
            productService.removeProduct(id);
            return "You removed product " + id;
        } catch (Exception e) {
            return "Could not remove product #" + id + ".";
        }
    }

    @GetMapping("/store/update")
    @ResponseBody
    public String updateProductPrice(@RequestParam Long id, @RequestParam int price) {
        try {
            productService.updateProductPrice(id, price);
            return "You updated product " + id + " to a price of " + price;
        } catch (Exception e) {
            return "Could not update product #" + id + ".";
        }
    }

    @GetMapping("/store/purchase")
    @ResponseBody
    public String purchaseProduct(@RequestParam Long id, @RequestParam int quantity) {
        try {
            productService.purchaseProduct(id, quantity);
            return "You purchased " + quantity + " of product #" + id + ".";
        } catch (Exception e) {
            return "Could not purchase product #" + id + ".";
        }
    }

    @GetMapping("/store/sell")
    @ResponseBody
    public String sellProduct(@RequestParam Long id, @RequestParam String type) {
        try {
            int value = productService.sellProduct(id, type);
            return "You sold product #" + id + " for " + value + "!";
        } catch (Exception e) {
            return "Could not sell product #" + id + ".";
        }
    }

}
