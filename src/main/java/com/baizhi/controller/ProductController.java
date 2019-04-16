package com.baizhi.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Product;
import com.baizhi.jedisutil.JedisUtil;
import com.baizhi.luceneutil.LuceneUtil;
import com.baizhi.service.ProductService;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryTermScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.wltea.analyzer.lucene.IKAnalyzer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.lang.Integer.valueOf;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService productService;


    @RequestMapping("/showProduct")
    private List<Product> showProduct(String context)throws Exception{
        List<Product> products = new ArrayList<>();
        //easypoi 导出数据库数据                                  Excel文件            文件名，              sheet名    哪个类       从数据库查出的数据集合
        Workbook workbook = ExcelExportUtil.exportBigExcel(new ExportParams("商品文件","商品信息"),Product.class,products);
        //写出文件                                          excel 文件存放位置
        workbook.write(new FileOutputStream(new File("D:/index")));
        workbook.close();

        //Jedis操作redis
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //设置连接池最大空闲连接数
        jedisPoolConfig.setMaxIdle(2);
        //设置连接池最大连接数
        jedisPoolConfig.setMaxTotal(10);
        //设置连接池最打等待连接数
        jedisPoolConfig.setMaxWaitMillis(100);
        //创建连接池
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,"192.168.50.128",6379);
        //获取连接redis对象
        Jedis jedis = jedisPool.getResource();
        Jedis jedis1 = JedisUtil.getJedis();
        //jedisApi与redis中命令大同小异
        String name = jedis.get("name");
        System.out.println(name);


        //查询的页数和条数
        Integer pageSize = 1;
        Integer pageCount = 5;
        //按照那个索引查询
        String[] strs = {"title","content","description"};
        //按条件创建query对象
        MultiFieldQueryParser parse = new MultiFieldQueryParser(Version.LUCENE_44,strs,new IKAnalyzer());
        Query query = parse.parse(context);
        IndexSearcher indexSearcher = LuceneUtil.getIndexSearcher();
        //创建添加的标签对象
        Formatter formatter = new SimpleHTMLFormatter("<font color='red'>","</font>");
        //获取需要高亮的字段
        QueryTermScorer scorer = new QueryTermScorer(query);
        Highlighter highlighter = new Highlighter(formatter,scorer);

        TopDocs topDocs = indexSearcher.search(query, pageSize*pageCount);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        System.out.println(scoreDocs.length);
        for (int i = 0; i <scoreDocs.length ; i++) {
            ScoreDoc scoreDoc = scoreDocs[i];
            int doc = scoreDoc.doc;
            Document document = indexSearcher.doc(doc);
            String price = document.get("price");
            Double d = Double.valueOf(price);
            String uptime = document.get("uptime");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(uptime);
            System.out.println(date);
            String status = document.get("status");
            Integer value = valueOf(status);
            Product product = new Product(document.get("id"), document.get("name"), d, document.get("description"), document.get("img_path"), value, date, document.get("city"));
            products.add(product);
        }
        return products;
    }


    @RequestMapping("/add")
    public String addProduct(Product product, MultipartFile picture)throws Exception{
        System.out.println(product);
        String id = UUID.randomUUID().toString().replace("-","");
        String fileName = picture.getOriginalFilename();
        File file = new File("D:/index/picture/"+fileName);
        System.out.println(file.getAbsolutePath());
        picture.transferTo(file);
        product.setId(id);
        product.setImg_path(fileName);
        productService.add(product);





        return "index";
    }

}
