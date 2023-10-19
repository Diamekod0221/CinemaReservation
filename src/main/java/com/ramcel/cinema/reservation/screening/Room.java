package com.ramcel.cinema.reservation.screening;

import com.ramcel.cinema.reservation.db.RoomRowEntity;
import jakarta.persistence.OneToMany;

import java.util.List;

public record Room(long id, int numberOfRows, List<RoomRow> roomRows) {

}
