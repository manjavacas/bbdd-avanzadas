package bbddavanzadas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.bson.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MyHandler extends DefaultHandler {

    final static String DESTINATION = "/home/antonio/UCLM/XMLdata/output/";
    final static String URI = "mongodb+srv://mms:mms123@bdadatawarehouse-qalqb.mongodb.net/test?retryWrites=true&w=majority";

    private List<String> categories;
    private Question question;
    private StringBuilder data;

    // Flags
    private boolean flagUri;
    private boolean flagSubject;
    private boolean flagMaincat;
    private boolean flagCat;
    private boolean flagDate;

    public MyHandler(List<String> categories) {

        this.categories = categories;
        this.question = new Question();

        this.flagUri = false;
        this.flagSubject = false;
        this.flagMaincat = false;
        this.flagCat = false;
        this.flagDate = false;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("vespaadd")) {
            question = new Question();
        } else if (qName.equalsIgnoreCase("uri")) {
            flagUri = true;
        } else if (qName.equalsIgnoreCase("subject")) {
            flagSubject = true;
        } else if (qName.equalsIgnoreCase("cat")) {
            flagCat = true;
        } else if (qName.equalsIgnoreCase("maincat")) {
            flagMaincat = true;
        } else if (qName.equalsIgnoreCase("date")) {
            flagDate = true;
        }

        data = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (flagUri) {
            question.setUri(data.toString());
            flagUri = false;
        } else if (flagSubject) {
            question.setSubject(data.toString());
            flagSubject = false;
        } else if (flagCat) {
            question.setCat(data.toString());
            flagCat = false;
        } else if (flagMaincat) {
            question.setMaincat(data.toString());
            flagMaincat = false;
        } else if (flagDate) {
            question.setDate(data.toString());
            flagDate = false;
        }

        if (qName.equalsIgnoreCase("vespaadd")) {
            if (categories.contains(question.getMaincat()))
                try {
                    // save(question);
                    saveFile(question);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }

    private void saveFile(Question question) throws IOException {

        String path = DESTINATION + question.getMaincat() + ".txt";
        File file = new File(path);

        PrintWriter out = null;

        if (file.exists() && !file.isDirectory()) {
            out = new PrintWriter(new FileOutputStream(new File(path), true));
        } else {
            out = new PrintWriter(path);
        }

        out.append(question.toString());
        out.close();

    }

    private void save(Question question) throws IOException {

        MongoClient mongoClient = MongoClients.create(URI);
        MongoDatabase database = mongoClient.getDatabase("yahoo_answers");
        MongoCollection<Document> collection = database.getCollection(question.getMaincat());

        Document doc = new Document("uri", question.getUri()).append("subject", question.getSubject())
                .append("cat", question.getCat()).append("maincat", question.getMaincat())
                .append("date", question.getDate()).append("weekday", question.getDate().getDayOfWeek().toString())
                .append("month", question.getDate().getMonthValue()).append("year", question.getDate().getYear());

        collection.insertOne(doc);
        mongoClient.close();

    }

}