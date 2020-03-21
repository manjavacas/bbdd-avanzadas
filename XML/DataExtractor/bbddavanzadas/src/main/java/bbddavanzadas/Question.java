package bbddavanzadas;

public class Question {

    private String uri;
    private String subject;
    private String cat;
    private String maincat;
    private String date;

    public Question() {
    }

    public Question(String uri, String subject, String cat, String maincat, String date) {
        this.uri = uri;
        this.subject = subject;
        this.cat = cat;
        this.maincat = maincat;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "{ \"question\": { \"cat\" : \"" + cat + "\", \"date\" : \"" + date + "\", \"maincat\" : \"" + maincat
                + "\", \"subject\" : \"" + subject + "\", \"uri\" : \"" + uri + "\"} }\n";
    }

}