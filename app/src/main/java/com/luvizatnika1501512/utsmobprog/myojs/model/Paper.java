package com.luvizatnika1501512.utsmobprog.myojs.model;

public class Paper {

    String judul;
    String author;
    String foto;
    String tahun;
    String jumlah;
    String bahasa;
    String deskripsi;

    public Paper(){}

    public Paper(String judul, String author, String foto, String tahun, String jumlah, String bahasa, String deskripsi) {
        this.judul = judul;
        this.author = author;
        this.foto = foto;
        this.tahun = tahun;
        this.jumlah = jumlah;
        this.bahasa = bahasa;
        this.deskripsi = deskripsi;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getBahasa() {
        return bahasa;
    }

    public void setBahasa(String bahasa) {
        this.bahasa = bahasa;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
