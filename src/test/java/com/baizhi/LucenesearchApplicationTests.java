package com.baizhi;

import com.baizhi.dao.ProductMapper;
import com.baizhi.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LucenesearchApplicationTests {

    @Resource
    private ProductMapper productMapper;

    @Test
    public void contextLoads() {
        List<Product> products = productMapper.selectAll();
        for (Product product : products) {
            System.out.println(product);
            System.out.println("+++");
        }
        System.out.println("+++++++++++");

    }

}
