package com.cine.reservas.cine_reservas.dto;

import lombok.Data;

@Data
public class BookingDTO {
    private Long id;
    private String customerName;
    private String movieName;
    private String roomName;
    private String date;



    public BookingDTO(Long id, String customerName, String movieName, String roomName, String date) {
        this.id = id;
        this.customerName = customerName;
        this.movieName = movieName;
        this.roomName = roomName;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
