package com.example.renthouse.OOP;

public class Reports {
    private AccountClass account;
    private String idUser;
    private String title;
    private String content;
    private String ngayBaoCao;

    public Reports() {
    }

    public Reports(AccountClass account, String idUser, String title, String content, String ngayBaoCao) {
        this.account = account;
        this.idUser = idUser;
        this.title = title;
        this.content = content;
        this.ngayBaoCao = ngayBaoCao;
    }

    public AccountClass getAccount() {
        return account;
    }

    public void setAccount(AccountClass account) {
        this.account = account;
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

    public String getNgayBaoCao() {
        return ngayBaoCao;
    }

    public void setNgayBaoCao(String ngayBaoCao) {
        this.ngayBaoCao = ngayBaoCao;
    }
}
