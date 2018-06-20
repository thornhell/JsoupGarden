package com.ms.jsoupgarden;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Application {


    public static final String url = "http://pogodynka.pl";

    public static void main(String[] args) throws Exception {

        Application parser = new Application();
        Iterator iterator = parser.checkBaseLinks().iterator();
        while (iterator.hasNext()) {
            Object element = iterator.next();
            String secondLink = url + element;
            parser.checkNextLink(secondLink);
        }
    }


    public List checkBaseLinks() throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements elements = document.select("a.aktu_a[href*=/wiadomosci/]");

        List list = new ArrayList();
        for (Element e : elements) {
            String baseLink = e.attr("href");
            System.out.println("Base link: = " + baseLink);
            list.add(baseLink);
        }
        return list;
    }


    public List checkNextLink(String secondLink) throws IOException {
        Document document = Jsoup.connect(secondLink).get();
        Elements elements = document.select("div#subpage_left > p > a[href*=/http/]");
        List list = new ArrayList();
        for (Element e : elements) {
            String nextLink = e.attr("href");
            System.out.println("Next link: = " + nextLink);
            list.add(nextLink);
            download(url + nextLink);
        }
        return list;
    }


    void download(String link) throws IOException {
        URL url = new URL(link);
        URLConnection urlConnection = url.openConnection();
        int contentLength = urlConnection.getContentLength();
        InputStream raw = urlConnection.getInputStream();
        InputStream in = new BufferedInputStream(raw);
        byte[] data = new byte[contentLength];
        int bytesRead = 0;
        int offset = 0;
        while (offset < contentLength) {
            bytesRead = in.read(data, offset, data.length - offset);
            if (bytesRead == -1)
                break;
            offset += bytesRead;
        }
        in.close();
        if (offset != contentLength) {
            throw new IOException("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
        }
        String filename = link.substring(link.lastIndexOf('/') + 1, link.length());
        FileOutputStream out = new FileOutputStream(filename);
        out.write(data);
        out.flush();
        out.close();
    }
}
