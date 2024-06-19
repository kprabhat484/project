package com.oracle.pdfreader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class RSSReader {

    public static void main(String[] args) throws Exception {
        String rssFile = "C://genai//rss.xml"; // Change to your RSS file path
        String outputFilePath = "C://genai//output.txt";
        List<String> links = extractLinksFromRSS(rssFile);

        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<String>> futures = new ArrayList<>();

        for (String link : links) {
            futures.add(executor.submit(new FetchContentTask(link)));
        }

        executor.shutdown();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (Future<String> future : futures) {
                writer.write(future.get());
                writer.write("\n\n");
            }
        }

        System.out.println("Content written to output.txt");
    }

    private static List<String> extractLinksFromRSS(String rssFile) throws Exception {
        List<String> links = new ArrayList<>();
        File file = new File(rssFile);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        org.w3c.dom.Document doc = dBuilder.parse(file);;
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("item");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String link = eElement.getElementsByTagName("link").item(0).getTextContent();
                links.add(link);
            }
        }
        return links;
    }

//    private static String fetchContentFromLink(String link) {
//        try {
//            Document doc = Jsoup.connect(link).get();
//            return doc.body().text();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "Failed to fetch content from " + link;
//        }
//    }

    static class FetchContentTask implements Callable<String> {
        private final String link;

        public FetchContentTask(String link) {
            this.link = link;
        }

        @Override
        public String call() {
            try {
                Document doc = Jsoup.connect(link).get();
                return doc.body().text();
            } catch (IOException e) {
                e.printStackTrace();
                return "Failed to fetch content from " + link;
            }
        }
    }
}