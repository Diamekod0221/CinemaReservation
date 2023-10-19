package com.ramcel.cinema.reservation.ticket;

import com.ramcel.cinema.reservation.entity.ScreeningEntity;
import com.ramcel.cinema.reservation.entity.SeatEntity;
import com.ramcel.cinema.reservation.entity.TicketEntity;
import com.ramcel.cinema.reservation.repositories.ScreeningRepository;
import com.ramcel.cinema.reservation.repositories.SeatRepository;
import com.ramcel.cinema.reservation.repositories.TicketRepository;
import com.ramcel.cinema.reservation.reservation.Reservation;
import com.ramcel.cinema.reservation.screening.Room;
import com.ramcel.cinema.reservation.seat.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMapper mapper;

    @Autowired
    private TicketValidator validator;


    @Override
    public Reservation bookTicket(Ticket ticket) {
        validator.validateRowPosition(ticket);



    }

    @Override
    public Reservation bookTicket(List<Ticket> ticketList) {
        return null;
    }



}
