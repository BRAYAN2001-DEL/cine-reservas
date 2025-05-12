package com.cine.reservas.cine_reservas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "seats")
public class SeatEntity extends BaseEntity {

    @Column(nullable = false)
    private short number;

    @Column(nullable = false)
    private short rowNumber;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    public short getNumber() {
        return number;
    }

    public void setNumber(short number) {
        this.number = number;
    }

    public short getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(short rowNumber) {
        this.rowNumber = rowNumber;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }
}