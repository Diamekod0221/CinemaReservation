package com.ramcel.cinema.reservation.service.integration;

import com.ramcel.cinema.reservation.db.repositories.SeatRepository;
import com.ramcel.cinema.reservation.db.repositories.TicketRepository;
import com.ramcel.cinema.reservation.functionalities.exception.IllegalReservationException;
import com.ramcel.cinema.reservation.functionalities.reservation.Reservation;
import com.ramcel.cinema.reservation.functionalities.ticket.*;
import com.ramcel.cinema.reservation.functionalities.ticket.validators.TicketValidator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"/sql_scripts/import_movies.sql", "/sql_scripts/import_rooms.sql", "/sql_scripts/import_screenings.sql", "/sql_scripts/import_seats.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/delete_test_data.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TicketServiceIntegrationTest {


    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketMapper mapper;

    @Autowired
    private TicketValidator validator;


    @Test
    public void bookValidTicketDB(){
        Ticket input = buildValidTestTicket();

        LocalDateTime expirationDate = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MINUTES);
        BigDecimal price = input.getType().getPrice();

        Reservation expected = new Reservation(List.of(input), price, expirationDate);
        Reservation actual = ticketService.bookTicket(input);

        assertEquals(expected, actual);
    }

    private static Ticket buildValidTestTicket() {
        return Ticket.builder()
                .name("John")
                .surname("Doe")
                .screeningId(2L)
                .screeningDate(LocalDateTime.of(2023,11,1,11,0,0))
                .seatId(7L)
                .type(TicketType.STUDENT)
                .build();
    }

    private static Ticket buildValidTestTicket2() {
        return Ticket.builder()
                .name("Papa")
                .surname("Japa")
                .screeningId(2L)
                .screeningDate(LocalDateTime.of(2023,11,1,11,0,0))
                .seatId(9L)
                .type(TicketType.ADULT)
                .build();
    }


    //book single to get a hole in rows in screening1
    private static Ticket buildValidTestTicket3() {
        return Ticket.builder()
                .name("Papa")
                .surname("Japa")
                .screeningId(1L)
                .screeningDate(LocalDateTime.of(2023,11,2,4,15,0))
                .seatId(1L)
                .type(TicketType.ADULT)
                .build();
    }


    private static Ticket buildInvalidTestTicketSeatTaken() {
        return Ticket.builder()
                .name("John")
                .surname("Doe")
                .screeningId(2L)
                .screeningDate(LocalDateTime.of(2023,11,1,11,0,0))
                .seatId(8L)
                .type(TicketType.STUDENT)
                .build();
    }

    @Test
    public void bookValidTicketList(){
        Ticket ticket1 = buildValidTestTicket();
        Ticket ticket2 = buildValidTestTicket2();
        List<Ticket> input = List.of(ticket1,ticket2);

        BigDecimal price = BigDecimal.valueOf(25+18);
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MINUTES);

        Reservation expected = new Reservation(input, price, expirationDate);
        Reservation actual = ticketService.bookTicket(input);

        assertEquals(expected, actual);
    }

    @Test
    public void bookInvalidTicket(){
        Ticket input = buildInvalidTestTicketSeatTaken();

        assertThrowsExactly(IllegalReservationException.class, ()-> ticketService.bookTicket(input));
    }

    @Test
    public void bookInvalidTicketWithHoles(){
        Ticket ticket1 = buildValidTestTicket3();

        List<Ticket> input = List.of(ticket1);

        assertThrows(IllegalReservationException.class, () -> ticketService.bookTicket(input));
    }



}
