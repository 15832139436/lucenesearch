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

public class CreateLucene2 {

    public static void main(String[] args) {

        Directory dir = null;
        try {
            dir = FSDirectory.open(new File("D:/index"));

            IKAnalyzer ikAnalyzer = new IKAnalyzer();

            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_44,ikAnalyzer);

            IndexWriter indexWriter = new IndexWriter(dir,indexWriterConfig);

            Document document = new Document();

            document.add(new StringField("id","1", Field.Store.YES));
            document.add(new StringField("title","背影", Field.Store.YES));
            document.add(new TextField("content","你就站在此地不要走动，我去买几个橘子", Field.Store.YES));
            indexWriter.addDocument(document);

            indexWriter.commit();
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
