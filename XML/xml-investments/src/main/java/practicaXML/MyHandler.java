package practicaXML;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.bson.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MyHandler extends DefaultHandler {

	private final String URI = "mongodb+srv://mms:mms123@bdadatawarehouse-qalqb.mongodb.net/test?retryWrites=true&w=majority";
	private final String DATABASE_NAME = "magallanes_nokia";
	
	private String name;
	private String destination;
	private int year;

	StringBuilder data;
	Record record;

	// Flags
	private boolean flagRecord;
	private boolean flagDescription;
	private boolean flagCurrentEuros;
	private boolean flagCurrentPercent;
	private boolean flagPreviousEuros;
	private boolean flagPreviousPercent;

	public MyHandler(String name, int year, String destination) {

		this.name = name;
		this.year = year;
		this.destination = destination;

		this.flagRecord = false;
		this.flagDescription = false;
		this.flagCurrentEuros = false;
		this.flagCurrentPercent = false;
		this.flagPreviousEuros = false;
		this.flagPreviousPercent = false;

	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if (qName.equals("iic-com:InversionesFinancierasRVCotizada")) {
			record = new Record();
			flagRecord = true;
		} else if (qName.equals("iic-com:InversionesFinancierasDescripcion")) {
			flagDescription = true;
		} else if (flagRecord) {
			if (qName.equals("iic-com:InversionesFinancierasValor")) {
				if (attributes.getValue("contextRef").equals("FIM_T1" + year + "_II0004841_ia")) {
					flagCurrentEuros = true;
				} else if (attributes.getValue("contextRef").equals("FIM_T1" + year + "_II0004841_ipy")) {
					flagPreviousEuros = true;
				}
			} else if (qName.equals("iic-com:InversionesFinancierasPorcentaje")) {
				if (attributes.getValue("contextRef").equals("FIM_T1" + year + "_II0004841_ia")) {
					flagCurrentPercent = true;
				} else if (attributes.getValue("contextRef").equals("FIM_T1" + year + "_II0004841_ipy")) {
					flagPreviousPercent = true;
				}
			}
		}

		data = new StringBuilder();
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (flagRecord) {
			if (flagDescription) {
				record.setDescription(data.toString());
				flagDescription = false;
			} else if (flagCurrentEuros) {
				record.setCurrentEuros(Double.parseDouble(data.toString()));
				flagCurrentEuros = false;
			} else if (flagCurrentPercent) {
				record.setCurrentPercent(Double.parseDouble(data.toString()));
				flagCurrentPercent = false;
			} else if (flagPreviousEuros) {
				record.setPreviousEuros(Double.parseDouble(data.toString()));
				flagPreviousEuros = false;
			} else if (flagPreviousPercent) {
				record.setPreviousPercent(Double.parseDouble(data.toString()));
				flagPreviousPercent = false;
			}
			if (qName.equals("iic-com:InversionesFinancierasRVCotizada")) {
				flagRecord = false;
				if (record.getDescription().equals(name))
					try {
						save(record);
						// saveFile(record);
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}

	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		data.append(new String(ch, start, length));
	}

	private void save(Record record) throws IOException {

		MongoClient mongoClient = MongoClients.create(URI);
		MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
		MongoCollection<Document> collection = database.getCollection("" + year);

		Document doc = new Document("description", record.getDescription())
				.append("current_euros", record.getCurrentEuros()).append("current_percent", record.getCurrentPercent())
				.append("previous_euros", record.getPreviousEuros())
				.append("previous_percent", record.getPreviousPercent());

		collection.insertOne(doc);
		mongoClient.close();

	}

	@SuppressWarnings("unused")
	private void saveFile(Record record) throws IOException {

		String path = destination + "out_" + year + ".txt";
		File file = new File(path);

		PrintWriter out = null;

		if (file.exists() && !file.isDirectory()) {
			out = new PrintWriter(new FileOutputStream(new File(path), true));
		} else {
			out = new PrintWriter(path);
		}

		out.append(record.toString());
		out.close();

	}

}