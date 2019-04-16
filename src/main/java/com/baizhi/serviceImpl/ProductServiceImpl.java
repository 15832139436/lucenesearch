package com.baizhi.serviceImpl;

import com.baizhi.dao.ProductMapper;
import com.baizhi.entity.Product;
import com.baizhi.luceneutil.LuceneUtil;
import com.baizhi.service.ProductService;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;



    @Override
    public void add(Product product) {
        //添加商品
        productMapper.insert(product);

        IndexWriter indexWriter = LuceneUtil.getIndexWriter();
        try {
            //添加索引
            //转换商品中的日期格式为字符串
            Date uptime = product.getUptime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = dateFormat.format(uptime);
            //创键添加索引对象
            Document document = new Document();
            document.add(new StringField("id",product.getId(), Field.Store.YES));
            document.add(new StringField("name",product.getName(), Field.Store.YES));
            document.add(new StringField("price",String.valueOf(product.getPrice()), Field.Store.YES));
            document.add(new TextField("description",product.getDescription(), Field.Store.YES));
            document.add(new StringField("img_path",product.getImg_path(), Field.Store.YES));
            document.add(new StringField("status",product.getStatus()+"", Field.Store.YES));
            document.add(new StringField("uptime",date, Field.Store.YES));
            document.add(new StringField("city",product.getCity(),Field.Store.YES));
            //添加索引
            System.out.println(document);
            indexWriter.addDocument(document);
            //提交并关流
            LuceneUtil.commit(indexWriter);
        } catch (IOException e) {
            e.printStackTrace();
            //回滚
            LuceneUtil.rollback(indexWriter);
        }


    }
}
