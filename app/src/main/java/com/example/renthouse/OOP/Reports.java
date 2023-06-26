package com.example.renthouse.OOP;

public class Reports {
    private String email;
    private String idUser;
    private String title;
    private String content;

    public Reports() {
    }

    public Reports(String email, String idUser, String title, String content) {
        this.email = email;
        this.idUser = idUser;
        this.title = title;
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
