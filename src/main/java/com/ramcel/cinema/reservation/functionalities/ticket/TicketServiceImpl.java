package com.ramcel.cinema.reservation.functionalities.ticket;

import com.ramcel.cinema.reservation.db.repositories.TicketRepository;
import com.ramcel.cinema.reservation.functionalities.reservation.Reservation;
import com.ramcel.cinema.reservation.functionalities.ticket.validators.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMapper mapper;

    @Autowired
    private TicketValidator validator;


    @Override
    public Reservation bookTicket(Ticket ticket) throws InvalidSeatException{
        if(validator.isValid(ticket)){
            ticketRepository.save(mapper.mapToEntity(ticket));

            return new Reservation(List.of(ticket), ticket.getType().getPrice(), ticket.getScreeningDate());
        }
        else{
            throw new InvalidSeatException("Ticket: " + ticket + " is not valid.");
        }
    }

    @Override
    public Reservation bookTicket(List<Ticket> ticketList) {


    }



}
