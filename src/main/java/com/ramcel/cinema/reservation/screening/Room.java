package com.ramcel.cinema.reservation.screening;

import com.ramcel.cinema.reservation.db.RoomRowEntity;
import jakarta.persistence.OneToMany;

import java.util.List;

public record Room(int numberOfRows, List<RoomRowEntity> roomRows) {

}
