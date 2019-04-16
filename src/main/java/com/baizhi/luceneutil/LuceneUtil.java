package com.baizhi.luceneutil;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class LuceneUtil {

    private static Directory dir;
    private static Analyzer analyzer;
    private static IndexWriterConfig indexWriterConfig;
    private static IndexReader indexReader;

    static{
        try {
            dir = FSDirectory.open(new File("D:/index"));
            analyzer = new IKAnalyzer();
            indexWriterConfig = new IndexWriterConfig(Version.LUCENE_44,analyzer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static IndexWriter getIndexWriter(){
        try {
            IndexWriter indexWriter = new IndexWriter(dir,indexWriterConfig);
            return indexWriter;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static IndexSearcher getIndexSearcher(){
        try {
            indexReader = DirectoryReader.open(dir);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            return indexSearcher;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void commit(IndexWriter indexWriter){
        try {
            indexWriter.commit();
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void rollback(IndexWriter indexWriter){
        try {
            indexWriter.rollback();
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
