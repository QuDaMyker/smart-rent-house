package com.example.renthouse.OOP;

public class City {
    private String name;
    private String slug;
    private String type;
    private String name_with_type;
    private String code;

    public City(String name, String slug, String type, String name_with_type, String code) {
        this.name = name;
        this.slug = slug;
        this.type = type;
        this.name_with_type = name_with_type;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName_with_type() {
        return name_with_type;
    }

    public void setName_with_type(String name_with_type) {
        this.name_with_type = name_with_type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
