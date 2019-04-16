package com.baizhi.test;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

public class SearchLucene {


    public static void main(String[] args) {

        try {
            Directory dir = FSDirectory.open(new File("D:/index"));

            IndexReader indexReader = DirectoryReader.open(dir);

            IndexSearcher indexSearcher = new IndexSearcher(indexReader);

            Query query = new TermQuery(new Term("content","哈哈"));

            TopDocs topDocs = indexSearcher.search(query, 100);

            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (int i = 0; i <scoreDocs.length ; i++) {
                ScoreDoc scoreDoc = scoreDocs[i];

                int doc = scoreDoc.doc;

                Document document = indexSearcher.doc(doc);

                System.out.println(document.get("id"));
                System.out.println(document.get("content"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
