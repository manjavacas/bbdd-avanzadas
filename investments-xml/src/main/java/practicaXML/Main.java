package practicaXML;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Main {

	final static String ROUTE_2017 = "./input/data_2017_T1.XML";
	final static String ROUTE_2018 = "./input/data_2018_T1.XML";
	final static String ROUTE_2019 = "./input/data_2019_T1.XML";

	final static String NAME = "ACCIONES|Dragerwerk";
	final static String DESTINATION = "./output/";

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		
		System.out.print("Parsing 2017...");
		DefaultHandler handler = new MyHandler(NAME, 2017, DESTINATION);
		parser.parse(ROUTE_2017, handler);
		System.out.println(" done!");
		
		System.out.print("Parsing 2018...");
		handler = new MyHandler(NAME, 2018, DESTINATION);
		parser.parse(ROUTE_2018, handler);
		System.out.println(" done!");
		
		System.out.print("Parsing 2019...");
		handler = new MyHandler(NAME, 2019, DESTINATION);
		parser.parse(ROUTE_2019, handler);
		System.out.println(" done!");
	}
}
