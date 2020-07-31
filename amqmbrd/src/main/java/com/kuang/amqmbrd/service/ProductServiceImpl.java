package com.kuang.amqmbrd.service;

import com.kuang.amqmbrd.mapper.ProductMapper;
import com.kuang.amqmbrd.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.sound.midi.SoundbankResource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    public List<Product> queryProductList(){
        List<Product> list = productMapper.queryProductList();
        return list;
    }

    //先从缓存获取，没有的话在从数据库
    public Product queryProductById(int id){
        String key = "product" +id;
      ValueOperations<String,Product> operations= redisTemplate.opsForValue();
      boolean hasKey = redisTemplate.hasKey(key);
      if(hasKey){
          Product product = operations.get(key);
          System.out.println("缓存读取");
          System.out.println(product.getId()+product.getName()+product.getPrice());
        return product;
      }
      else
      {
          Product product = productMapper.queryProductById(id);
          System.out.println("数据库读取");
          System.out.println(product.getId()+product.getName()+product.getPrice());
          operations.set(key,product,5, TimeUnit.HOURS);
          return product;
      }
    }
    //更新策略：先更新数据库，然后删除原来缓存，在更新缓存
    public int updateProduct(Product product){

        ValueOperations<String,Product> operations = redisTemplate.opsForValue();
        int result = productMapper.updateProduct(product);
        if(result!=0){
            String key = "product" +product.getId();
            boolean haskey = redisTemplate.hasKey(key);
            if(haskey){
                redisTemplate.delete(key);
                System.out.println("删除缓存值"+key);
            }
            Product productNew = productMapper.queryProductById(product.getId());
            if(productNew!=null){
                operations.set(key,productNew,3,TimeUnit.HOURS);
            }
        }
        return result;
    }

    //删除数据库，在删除缓存
    public int deleteProduct(int id){
        int result =productMapper.deleteProduct(id);
        String key = "product" +id;
        if(result!=0) {
            boolean haskey = redisTemplate.hasKey(key);
            if (haskey) {
                redisTemplate.delete(key);
                System.out.println("缓存删除" + key);
            }
        }
            return result;
    }
    //添加到数据库
    public String addProduct(){

        productMapper.addProduct(new Product(5,"iphone 6",99.99));
        return "Prodcut ADD";
    }
}
