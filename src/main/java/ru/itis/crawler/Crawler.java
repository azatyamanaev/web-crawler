package ru.itis.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 02.03.2022
 *
 * @author Azat Yamanaev
 */
public class Crawler {

    private static List<String> links;
    private static Integer num = 100;

    static {
        try {
            links = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("./downloads/links.txt"));
            String line = reader.readLine();
            int i = 1;
            while (i <= 100) {
                links.add(line);
                line = reader.readLine();
                i++;
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void main(String[] args) {
        String name;
        int number = 1;
        try {
            BufferedWriter wr = new BufferedWriter(new FileWriter("./index.txt"));
            for (String url : links) {
                Connection connection = Jsoup.connect(url);
                Document document = connection.get();
                name = "out" + number + ".txt";
                BufferedWriter writer = new BufferedWriter(new FileWriter("./downloads/" + name));
                writer.write(document.wholeText());
                writer.close();
                wr.write("document " + number + " - " + url + "\n");
                number++;
            }
            wr.close();
        } catch (IOException e) {
            System.out.println("Error occured during processing of an url");
        }


    }
}
