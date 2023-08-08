package com.example.demo.Enums;

public enum ContentType {
    POST("Post",0),
    COMMENT("Comment",1);

    private final String name;
    private final Integer id;

     ContentType(String name, Integer id){
         this.id = id;
         this.name = name;
    }
}
