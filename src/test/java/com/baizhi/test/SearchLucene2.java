package com.baizhi.test;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class SearchLucene2 {

    public static void main(String[] args) {

        Integer pageSize = 3;
        Integer currentPage = 1;

        Directory dir = null;
        try {
            dir = FSDirectory.open(new File("D:/index"));

            IndexReader indexReader = DirectoryReader.open(dir);

            IndexSearcher indexSearcher = new IndexSearcher(indexReader);

            String[] str = {"id","title","content"};

            MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_44,str,new IKAnalyzer());

            Query query = parser.parse("背影，橘子");


            Formatter formatter = new SimpleHTMLFormatter("<font color='red'>","</font>");

            Scorer scorer = new QueryTermScorer(query);

            Highlighter highlighter = new Highlighter(formatter,scorer);

            TopDocs topDocs = indexSearcher.search(query, pageSize*currentPage);

            ScoreDoc[] scoreDocs = topDocs.scoreDocs;

            for (int i = (currentPage - 1)*pageSize; i < scoreDocs.length; i++) {
                ScoreDoc scoreDoc = scoreDocs[i];
                int doc = scoreDoc.doc;
                Document document = indexSearcher.doc(doc);

                System.out.println(document.get("id"));
                System.out.println(document.get("title"));
                System.out.println(document.get("content"));

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


}
