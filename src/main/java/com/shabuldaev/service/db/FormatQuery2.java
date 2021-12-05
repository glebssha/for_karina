package com.shabuldaev.service.db;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;

@AllArgsConstructor
public class FormatQuery2 {
    private String city;
    private String Cancelled_num;

    public static final ArrayList<String> headers
            = new ArrayList<>(Arrays.asList("City", "Number of cancelled flights"));

    public ArrayList<Object> getItems() {
        return new ArrayList<>(Arrays.asList(city, Cancelled_num));
    }

    public void printItems() {
        System.out.println(city + " " + Cancelled_num);
    }
}
