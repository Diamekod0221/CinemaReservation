package com.ramcel.cinema.reservation.functionalities.ticket;

import com.ramcel.cinema.reservation.db.entity.ScreeningEntity;
import com.ramcel.cinema.reservation.db.entity.SeatEntity;
import com.ramcel.cinema.reservation.db.entity.TicketEntity;
import com.ramcel.cinema.reservation.db.repositories.ScreeningRepository;
import com.ramcel.cinema.reservation.db.repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TicketMapper {

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private ScreeningRepository screeningRepository;


    public TicketEntity mapToEntity(Ticket ticket) throws IllegalArgumentException {
        TicketEntity returnEntity = buildBasicEntity(ticket);

        Optional<SeatEntity> mappedSeat = seatRepository.findById(ticket.getSeatId());
        Optional<ScreeningEntity> mappedScreening = screeningRepository.findById(ticket.getScreeningId());

        if(mappedSeat.isPresent() && mappedScreening.isPresent()){
            returnEntity.setSeat(mappedSeat.get());
            returnEntity.setScreening(mappedScreening.get());
            return returnEntity;
        }
        else {
            throw new IllegalArgumentException("Couldn't find foreign keys in db for screening date:"
                    + ticket.getScreeningDate()
                    + " seat: "
                    + ticket.getSeatId());
        }
    }

    private static TicketEntity buildBasicEntity(Ticket ticket) {
        return TicketEntity.builder()
                        .name(ticket.getName())
                        .surname(ticket.getSurname())
                        .screeningDate(ticket.getScreeningDate())
                        .type(ticket.getType())
                        .build();
    }

    public TicketEntity mapToReservedEntity(Ticket ticket) throws IllegalArgumentException{
        TicketEntity baseEntity = mapToEntity(ticket);
        baseEntity.reserve();
        return baseEntity;
    }
}
