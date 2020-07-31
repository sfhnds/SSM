package com.kuang.amqmbrd.mapper;

import com.kuang.amqmbrd.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;
@Mapper
@Repository
public interface ProductMapper {
    List<Product> queryProductList();
    Product queryProductById(int id);
    int addProduct(Product product);
    int updateProduct(Product product);
    int deleteProduct(int id);
}
