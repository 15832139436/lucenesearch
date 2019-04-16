package com.baizhi.test;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class CreateLucene {

    public static void main(String[] args) {

        try {
            Directory dir =  FSDirectory.open(new File("D:/index"));

            IKAnalyzer ikAnalyzer = new IKAnalyzer();

            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_44,ikAnalyzer);
            //创建索引写入器
            IndexWriter indexWriter = new IndexWriter(dir,indexWriterConfig);

            Document document = new Document();
            document.add(new StringField("id","1", Field.Store.YES));
            document.add(new TextField("content","哈哈哈哈哈哈或或或或或或", Field.Store.YES));

            indexWriter.addDocument(document);

            indexWriter.commit();
            indexWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
