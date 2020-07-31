package com.kuang.amqmbrd.controller;

import com.kuang.amqmbrd.mapper.ProductMapper;
import com.kuang.amqmbrd.pojo.Product;
import com.kuang.amqmbrd.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

public class ProductController {
    @Autowired
    private ProductServiceImpl productServiceImpl;
    @ResponseBody
    @RequestMapping("/queryProductList")
    public List<Product> queryUserList(){
        List<Product> productList= productServiceImpl.queryProductList();
        return productList;
    }
    @ResponseBody
    @RequestMapping("/queryProductById")
        public Map<String, Object> findUserById(@RequestParam int id){
            Product product =productServiceImpl.queryProductById(id);
            Map<String, Object> result = new HashMap<>();
            result.put("id", product.getId());
            result.put("name", product.getName());
            result.put("price", product.getPrice());
            return result;
        }

    @ResponseBody
    @RequestMapping("/updateProduct")
    public String updateProduct(){
        Product product = new Product();
        product.setId(8);
        product.setName("Huawei Mate9");
        product.setPrice(399.99);
        int result = productServiceImpl.updateProduct(product);
        if(result!=0){
            return "Successfully update";
        }
        return "fail";
    }
    @RequestMapping("/deleteProduct")
    public String deleteProduct(){
        productServiceImpl.deleteProduct(2);
        return "Delete Successful";
    }


//    @RequestMapping("/addProduct")
//    public String addProduct(){
//        productMapper.addProduct(new Product(5,"iphone 6",99.99));
//        return "Prodcut ADD";
//    }
}
