package com.ramcel.cinema.reservation.functionalities.screening;

import java.util.List;

public record Room(long id, int numberOfRows, List<RoomRow> roomRows) {

}
