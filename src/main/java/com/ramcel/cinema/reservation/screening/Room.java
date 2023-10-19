package com.ramcel.cinema.reservation.screening;

import java.util.List;

public record Room(long id, int numberOfRows, List<RoomRow> roomRows) {

}
