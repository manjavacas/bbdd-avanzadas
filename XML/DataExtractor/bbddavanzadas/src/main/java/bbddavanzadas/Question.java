package bbddavanzadas;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Question {

    private String uri;
    private String subject;
    private String cat;
    private String maincat;
    private LocalDate date;

    public Question() {
    }

    public Question(String uri, String subject, String cat, String maincat, String timestamp) {
        this.uri = uri;
        this.subject = subject;
        this.cat = cat;
        this.maincat = maincat;

        Date d = new Date(Long.parseLong(timestamp) * 1000);
        this.date = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getMaincat() {
        return maincat;
    }

    public void setMaincat(String maincat) {
        this.maincat = maincat;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(String timestamp) {
        Date d = new Date(Long.parseLong(timestamp) * 1000);
        this.date = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public String toString() {
        return "{ \"question\": { \"cat\" : \"" + cat + "\", \"date\" : \"" + date + "\", \"maincat\" : \"" + maincat
                + "\", \"subject\" : \"" + subject + "\", \"uri\" : \"" + uri + "\"} }\n";
    }

}