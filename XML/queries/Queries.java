package bbddavanzadas.queries;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Queries {

	final static String URI; // URI
	final static String[] CATEGORIES = { "Travel", "Beauty & Style", "Society & Culture", "Computers & Internet",
			"Cars & Transportation", "Food & Drink" };
	final static String[] MONTH = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto",
			"Septiembre", "Octubre", "Noviembre", "Diciembre" };
	final static String[] WEEKDAY = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY" };

	public static void main(String[] args) {
		MongoClient mongoClient = MongoClients.create(URI);
		MongoDatabase database = mongoClient.getDatabase("yahoo_answers");

		// Buscar la categoria con mayor numero de preguntas
		popularCategory(database);

		// Dado un year devuelve el numero de publicaciones por meses y por categoria
		int year = 2005;
		System.out.println("Publicaciones por meses y categoria para " + year);
		publicationMonth(database, year);

		// Encontrar el year con mas preguntas
		int[] years = { 2005, 2006, 2007 };
		mostQuestionYear(database, years);

		// Encontrar el dia de la semana con mas preguntas para un tema dado
		String category = CATEGORIES[2];
		System.out.println("Dia de la semana con mas preguntas para " + category]);
		dayOfWeek(database, category);

	}

	static void dayOfWeek(MongoDatabase database, String category) {
		System.out.println("Para la categoria: " + category);
		for (int i = 0; i < WEEKDAY.length; i++) {
			MongoCollection<Document> collection = database.getCollection(category);
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("weekday", WEEKDAY[i]);
			MongoCursor<Document> cursor = collection.find(whereQuery).iterator();
			int count = 0;
			while (cursor.hasNext()) {
				cursor.next();
				count++;
			}
			System.out.println("El numero de preguntas para el dia de la semana: " + WEEKDAY[i] + " es: " + count);
		}
	}

	static void mostQuestionYear(MongoDatabase database, int[] years) {
		for (int i = 0; i < years.length; i++) {
			int count = 0;
			for (int j = 0; j < CATEGORIES.length; j++) {
				MongoCollection<Document> collection = database.getCollection(CATEGORIES[j]);
				BasicDBObject whereQuery = new BasicDBObject();
				whereQuery.put("year", years[i]);
				MongoCursor<Document> cursor = collection.find(whereQuery).iterator();
				while (cursor.hasNext()) {
					cursor.next();
					count++;
				}
			}
			System.out.println("El numero de preguntas para " + years[i] + " es: " + count);
		}
	}

	static void publicationMonth(MongoDatabase database, int year) {
		for (int i = 0; i < CATEGORIES.length; i++) {
			MongoCollection<Document> collection = database.getCollection(CATEGORIES[i]);
			System.out.println("La categoria: " + CATEGORIES[i] + ". Para : " + year + ". Tiene:");
			for (int j = 0; j < MONTH.length; j++) {
				BasicDBObject andQuery = new BasicDBObject();
				List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
				obj.add(new BasicDBObject("year", year));
				obj.add(new BasicDBObject("month", j + 1));
				andQuery.put("$and", obj);
				MongoCursor<Document> cursor = collection.find(andQuery).iterator();
				int count = 0;
				while (cursor.hasNext()) {
					cursor.next();
					count++;
				}
				System.out.println(count + " Preguntas para el mes: " + MONTH[j]);
			}
		}
	}
	
	static void popularCategory(MongoDatabase database) {
		for (int i = 0; i < CATEGORIES.length; i++) {
			MongoCollection<Document> collection = database.getCollection(CATEGORIES[i]);
			long num = collection.countDocuments();
			System.out.println("La categoria: " + CATEGORIES[i] + " tiene: " + num + " preguntas.");
		}
	}
}
