package com.example.demo.DTO;


public class userDTO {
    private int id;
    private String name;

    //direct use without lombok

    public userDTO() {
    }

    public userDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }


    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

}
