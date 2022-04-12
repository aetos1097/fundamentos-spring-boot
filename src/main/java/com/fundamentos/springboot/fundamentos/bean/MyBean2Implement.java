package com.fundamentos.springboot.fundamentos.bean;

public class MyBean2Implement implements MyBean{
    @Override
    public void print() {
        System.out.println("Hi from my own second bean implementation ");
    }
}
