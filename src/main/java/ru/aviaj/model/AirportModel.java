package ru.aviaj.model;


@SuppressWarnings("unused")
public class AirportModel {
    private long id;
    private String title;
    private String source;

    public AirportModel(long id, String name, String source) {
        this.id = id;
        this.title = name;
        this.source = source;
    }

    public long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSource() {
        return this.source;
    }
}
