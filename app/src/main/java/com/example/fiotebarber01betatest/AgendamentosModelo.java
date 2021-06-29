package com.example.fiotebarber01betatest;

public class AgendamentosModelo{
    String Nome;
    String Sobrenome;
    String Email;
    String Celular;
    String Data;
    String Hora;

    public AgendamentosModelo(){}

    public String getNome(String nome){ return Nome; }

    public void setNome(String nome){
        this.Nome = nome;
    }

    public String getSobrenome(){
        return Sobrenome;
    }

    public void setSobrenome(String sobrenome){
        this.Sobrenome = sobrenome;
    }

    public String getLogin(){
        return Email;
    }

    public void setLogin(String email){
        this.Email = email;
    }

    public String getCelular(){ return Celular; }

    public void setCelular(String celular){
        this.Celular = celular;
    }

    public String getData() { return Data; }

    public void setData(String data) { this.Data = data; }

    public String getHora() { return Hora; }

    public void setHora(String hora) { this.Hora = hora; }
}
