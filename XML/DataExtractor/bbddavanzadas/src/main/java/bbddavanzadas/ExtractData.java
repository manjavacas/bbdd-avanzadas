package bbddavanzadas;

import java.io.IOException;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ExtractData {

    final static String ROUTE = "/home/antonio/UCLM/XML/data/FullOct2007.xml";
    
    final static String[] CATEGORIES = { "Travel", "Beauty & Style", "Society & Culture", "Computers & Internet",
            "Cars & Transportation", "Food & Drink" };

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        DefaultHandler handler = new MyHandler(Arrays.asList(CATEGORIES));
        parser.parse(ROUTE, handler);
    }
}