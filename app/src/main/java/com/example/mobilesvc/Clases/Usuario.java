package com.example.mobilesvc.Clases;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int IDUser, Telefono, Edad;
    private String Nombre, Rol, Password, Direccion, Genero, Email, Dni;
    public Usuario() {}
    public Usuario(int IDUser, int telefono, int edad, String nombre, String dni, String email, String rol, String password, String direccion, String genero) {
        this.IDUser = IDUser;
        this.Telefono = telefono;
        this.Edad = edad;
        this.Nombre = nombre;
        this.Rol = rol;
        this.Email = email;
        this.Password = password;
        this.Dni = dni;
        this.Direccion = direccion;
        this.Genero = genero;
    }
    public int getIDUser() {
        return IDUser;
    }
    public void setIDUser(int IDUser) {
        this.IDUser = IDUser;
    }
    public int getTelefono() {
        return Telefono;
    }
    public void setTelefono(int telefono) {
        this.Telefono = telefono;
    }
    public int getEdad() {
        return Edad;
    }
    public void setEdad(int edad) {
        this.Edad = edad;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }
    public String getRol() {
        return Rol;
    }
    public void setRol(String rol) {
        this.Rol = rol;
    }
    public String getDni() {
        return Dni;
    }
    public void setDni(String dni) {
        this.Dni = dni;
    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        this.Password = password;
    }
    public String getDireccion() {
        return Direccion;
    }
    public void setDireccion(String direccion) {
        this.Direccion = direccion;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        this.Email = email;
    }
    public String getGenero() {
        return Genero;
    }
    public void setGenero(String genero) {
        this.Genero = genero;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "Rol='" + Rol + '\'' +
                "Nombre='" + Nombre + '\'' +
                "Edad='" + Edad + '\'' +
                "Telefono='" + Telefono + '\'' +
                "Genero='" + Genero + '\'' +
                "Direccion='" + Direccion + '\'' +
                "Email='" + Email + '\'' +
                '}';
    }
}
