package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming (PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String name;
    private String password;
    private List<Ticket> ticketBooked;
    private String userId;

    public User(){}

    public User(String name,String password,List<Ticket> ticketBooked,String userId){
        this.name = name;
        this.password = password;
        this.ticketBooked = ticketBooked;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and Setter for ticketBooked
    public List<Ticket> getticketBooked() {
        return ticketBooked;
    }

    public void setticketBooked(List<Ticket> ticketBooked) {
        this.ticketBooked = ticketBooked;
    }

    // Getter and Setter for userId
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void printTickets(){
        for(int i = 0; i < ticketBooked.size(); i++) {
            System.out.println(ticketBooked.get(i).getTicketInfo());
        }
    }
}

