package com.example.demo;

import java.io.Serializable;

public class HelloMessage implements Serializable {
	private static final long serialVersionUID = -8734267496113443645L;
	
	private String name;

    public HelloMessage() {
    }

    public HelloMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
