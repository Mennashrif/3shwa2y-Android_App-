package com.example.a3shwa2y;

public class history {
   private String name_Of_pieces;
   private int Price;
   private int Points;

    public history(String name_Of_pieces, int price, int points) {
        this.name_Of_pieces = name_Of_pieces;
        Price = price;
        Points = points;
    }

    public String getName_Of_pieces() {
        return name_Of_pieces;
    }

    public void setName_Of_pieces(String name_Of_pieces) {
        this.name_Of_pieces = name_Of_pieces;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getPoints() {
        return Points;
    }

    public void setPoints(int points) {
        Points = points;
    }
}
