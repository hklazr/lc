package com.project.lovecalculator.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Random;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Person {
    
    @Size(min=3, max=25, message="Name must be Between 3 and 25 characters")
    @NotNull
    @NotBlank
    @NotEmpty
    private String sname;
    
    @Size(min=3, max=25, message="Name must be Between 3 and 25 characters")
    private String fname;

    private Integer percentage;
    private String result;
    private String id;

    public Person() {
        this.id = generateId(8);
    }

    public String getSname() {
        return sname;
    }


    public void setSname(String sname) {
        this.sname = sname;
    }


    public String getFname() {
        return fname;
    }


    public void setFname(String fname) {
        this.fname = fname;
    }


    public Integer getPercentage() {
        return percentage;
    }


    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }


    public String getResult() {
        return result;
    }


    public void setResult(String result) {
        this.result = result;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person [sname=" + sname + ", fname=" + fname + ", percentage=" + percentage + ", result=" + result
                + ", id=" + id + "]";
    }

    private synchronized String generateId(int numChars) {
        Random r = new Random();
        StringBuilder strBuilder = new StringBuilder();
        while (strBuilder.length() < numChars) {
            strBuilder.append(Integer.toHexString(r.nextInt()));
        }
        return strBuilder.toString().substring(0, numChars);
    }

//takes the jason string to parse into a JSON object
public static Person create(String json) throws IOException {
    Person p = new Person();
    try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
        JsonReader r = Json.createReader(is);
        JsonObject o = r.readObject();
        // remove encoding chars from API
        String person1 = URLDecoder.decode(o.getString("fname"), "UTF-8");
        String person2 = URLDecoder.decode(o.getString("sname"), "UTF-8");
        p.setFname(person1);
        p.setSname(person2);
        p.setPercentage(Integer.parseInt(o.getString("percentage")));
        p.setResult(o.getString("result"));
    }
    return p;
}



}
    // use the loverresult to string into jSon, create a new loverresult, go thru read/write to string

// public static Person create(String json) throws IOException {
//     Person p = new Person();
//     try(InputStream is = new ByteArrayInputStream(json.getBytes())){
//         JsonReader r = Json.createReader(is);
//         JsonObject o = r.readObject();
//         p.setFname(o.getString("fname"));
//         p.setSname(o.getString("sname"));
//         p.setPercentage(o.getJsonNumber("percentage").intValue());
//         p.setResult(o.getString("result")); 
//     }
//     return p;
// }