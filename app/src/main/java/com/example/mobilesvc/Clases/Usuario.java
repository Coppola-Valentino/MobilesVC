package com.example.mobilesvc.Clases;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int IDUser, telefono, edad;
    private String nombre, rol, password, direccion, genero, email, dni;
    public Usuario() {}
    public Usuario(int IDUser, int telefono, int edad, String nombre, String dni, String email, String rol, String password, String direccion, String genero) {
        this.IDUser = IDUser;
        this.telefono = telefono;
        this.edad = edad;
        this.nombre = nombre;
        this.rol = rol;
        this.email = email;
        this.password = password;
        this.dni = dni;
        this.direccion = direccion;
        this.genero = genero;
    }
    public int getIDUser() {
        return IDUser;
    }
    public void setIDUser(int IDUser) {
        this.IDUser = IDUser;
    }
    public int getTelefono() {
        return telefono;
    }
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "Rol='" + rol + '\'' +
                "Nombre='" + nombre + '\'' +
                "Edad='" + edad + '\'' +
                "Telefono='" + telefono + '\'' +
                "Genero='" + genero + '\'' +
                "Direccion='" + direccion + '\'' +
                "Email='" + email + '\'' +
                '}';
    }
}
