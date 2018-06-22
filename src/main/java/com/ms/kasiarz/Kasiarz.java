package com.ms.kasiarz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Kasiarz {

    private static final String NBP = "http://www.nbp.pl/home.aspx?f=/kursy/kursya.html";
    private static final String PKOBP = "https://www.pkobp.pl/waluty/";
    private static final String PEKAO = "https://www.pekao.com.pl/exchange";
    private static final String MBANK = "https://www.mbank.pl/serwis-ekonomiczny/kursy-walut/";

    public static void main(String[] args) throws IOException {

        Kasiarz kasiarz = new Kasiarz();
        kasiarz.sprawdz();
    }


    void sprawdz() throws IOException {
        kursNBP();
        kursPKOBP();
        kursPEKAO();
        kursMBANK();
    }

    void kursNBP() throws IOException {
        System.out.println("\nPobieram kurs NBP ...");
        Document document = Jsoup.connect(NBP).get();
        Elements t = document.select(".pad5");
        Elements tbody = t.select("tr");
        for (int i = 0; i < tbody.size(); i++) {
            Element row = tbody.get(i);
            Elements cols = row.select("td");
            if (i == 2) {
                System.out.println("Kurs NBP: \t\tdolar: " + cols.get(2).text() + "\n");
            }
        }
    }

    void kursPKOBP() throws IOException {
        System.out.println("\nPobieram kurs PKOBP ...");
        Document document = Jsoup.connect(PKOBP).get();
        Element t = document.select(".course__table").get(0);
        Elements tbody = t.select("tbody");
        Element row = tbody.get(0);
        Elements cols = row.select("td");
        System.out.println("Kurs PKOBP: \tdolar: " + cols.get(1).text() + "\n");
    }

    void kursPEKAO() throws IOException {
        System.out.println("\nPobieram kurs PEKAO ...");
        Document document = Jsoup.connect(PEKAO).get();
        Elements t = document.select(".cqTable");
        Elements tbody = t.select("tr");

        for (int i = 0; i < tbody.size(); i++) {
            Element row = tbody.get(i);
            Elements cols = row.select("td");
            if (i == 15) {
                System.out.println("Kurs PEKAO: \tdolar: " + cols.get(3).text() + "\n");
            }
        }
    }

    void kursMBANK() throws IOException {
        System.out.println("\nPobieram kurs MBANK ...");
        Document document = Jsoup.connect(MBANK).get();
        Elements t = document.select(".default");
        Elements tbody = t.select("tbody");
        Element tr = tbody.select("tr").get(0);
        Elements cols = tr.select("td");
        System.out.println("Kurs MBANK: \tdolar: " + cols.get(4).text() + "\n");
    }
        
}
