package com.example.sweater.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//Hibernate automatically translates the entity into a table.
@Getter
@Setter
@Entity
public class Message {
    public Message() {}

    public Message(String text, String tag, User user) {
        this.text = text;
        this.tag = tag;
        author = user;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String text;
    private String tag;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public String getAuthorName(){
        return author != null ? author.getUsername() : "<none>";
    }
}