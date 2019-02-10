package com.luvizatnika1501512.utsmobprog.myojs.model;

public class Register {

    String namalengkap;
    String afiliasi;
    String negara;
    String email;
    String namapengguna;


    public Register(){}

    public Register(String namalengkap, String afiliasi, String negara, String email, String namapengguna) {
        this.namalengkap = namalengkap;
        this.afiliasi = afiliasi;
        this.negara = negara;
        this.email = email;
        this.namapengguna = namapengguna;
    }

    public String getNamalengkap() {
        return namalengkap;
    }

    public void setNamalengkap(String namalengkap) {
        this.namalengkap = namalengkap;
    }

    public String getAfiliasi() {
        return afiliasi;
    }

    public void setAfiliasi(String afiliasi) {
        this.afiliasi = afiliasi;
    }

    public String getNegara() {
        return negara;
    }

    public void setNegara(String negara) {
        this.negara = negara;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNamapengguna() {
        return namapengguna;
    }

    public void setNamapengguna(String namapengguna) {
        this.namapengguna = namapengguna;
    }

}
