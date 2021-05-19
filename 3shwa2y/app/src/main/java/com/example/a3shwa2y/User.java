package com.example.a3shwa2y;

import java.io.Serializable;

public class User implements Serializable {
    private int ID;
    private String U_name;
    private String U_Email;
    private String U_password;
    private int U_status;
    private int U_points;
    private String encodedImage;
    private String U_image;
    User(String U_name, String U_Email, String U_password){
        this.U_name=U_name;
        this.U_Email=U_Email;
        this.U_password=U_password;
        this.U_status=0;
        this.U_points=0;
    }

    public User() {
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getU_points() {
        return U_points;
    }

    public void setU_points(int u_points) {
        U_points += u_points;
    }

    public String getU_name() {
        return U_name;
    }

    public void setU_name(String u_name) {
        U_name = u_name;
    }

    public String getU_Email() {
        return U_Email;
    }

    public void setU_Email(String u_Email) {
        U_Email = u_Email;
    }

    public String getU_password() { return U_password; }

    public void setU_password(String u_password) {
        U_password = u_password;
    }

    public int getU_status() {
        return U_status;
    }

    public void setU_status(int u_status) {
        U_status = u_status;
    }

    public String getU_image() {
        return U_image;
    }

    public void setU_image(String u_image) {
        U_image = u_image;
    }
}

