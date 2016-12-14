package ru.aviaj.model;

@SuppressWarnings({"ConstantNamingConvention", "unused"})
public class PlaneModel {
    private long id;
    private String name;
    private String source;

    public PlaneModel(long id, String name, String source) {
        this.id = id;
        this.name = name;
        this.source = source;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSource() {
        return this.source;
    }
}
