package com.example.sweater.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//Hibernate automatically translates the entity into a table.
@Getter
@Setter
@Entity
public class Message {
    public Message() {
    }

    public Message(String text, String tag) {
        this.text = text;
        this.tag = tag;
    }


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String text;
    private String tag;


}
