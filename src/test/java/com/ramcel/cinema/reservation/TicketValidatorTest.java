package com.ramcel.cinema.reservation;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.ramcel.cinema.reservation.db.entity.SeatEntity;
import com.ramcel.cinema.reservation.db.repositories.SeatRepository;
import com.ramcel.cinema.reservation.functionalities.exception.IllegalReservationException;
import com.ramcel.cinema.reservation.functionalities.seat.SeatStatus;
import com.ramcel.cinema.reservation.functionalities.ticket.Ticket;
import com.ramcel.cinema.reservation.functionalities.ticket.TicketType;
import com.ramcel.cinema.reservation.functionalities.ticket.validators.TicketValidator;
import com.ramcel.cinema.reservation.objects.SeatEntityMockValid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TicketValidatorTest {

    private TicketValidator ticketValidator;
    private SeatRepository seatRepository;

    @BeforeEach
    public void setup() {
        seatRepository = mock(SeatRepository.class);
        ticketValidator = new TicketValidator(seatRepository);

        when(seatRepository.findById(anyLong())).thenReturn(Optional.of(new SeatEntityMockValid()));
        when(seatRepository.findSeatsByRoomRowAndScreeningID(anyLong(), anyLong())).thenReturn(List.of(new SeatEntityMockValid()));
    }

    @Test
    public void isValidWithValidTicket() throws IllegalReservationException {
        Ticket validTicket = buildValidTestTicket();

        boolean isValid = ticketValidator.isValid(validTicket);

        Assertions.assertTrue(isValid);
    }



    private static Ticket buildValidTestTicket() {
        return Ticket.builder()
                .name("John")
                .surname("Doe")
                .screeningId(1L)
                .screeningDate(LocalDateTime.now().plusDays(2))
                .seatId(2L)
                .type(TicketType.STUDENT)
                .build();
    }


    @Test
    public void isValidWithInvalidTicketHolder() {
        Ticket invalidTicket = TicketValidatorTest.buildInvalidTestTicketHolderInvalid();

        boolean isValid = ticketValidator.isValid(invalidTicket);

        Assertions.assertFalse(isValid);
    }

    private static Ticket buildInvalidTestTicketHolderInvalid(){
        return Ticket.builder()
                .name("as")
                .surname("as")
                .screeningId(1L)
                .screeningDate(LocalDateTime.now().plusDays(2))
                .seatId(2L)
                .type(TicketType.STUDENT)
                .build();
    }

    @Test
    public void isValidWithInvalidScreeningDate() {
        Ticket invalidTicket = TicketValidatorTest.buildInvalidTestTicketScreeningDateInvalid();

        boolean isValid = ticketValidator.isValid(invalidTicket);

        Assertions.assertFalse(isValid);
    }

    private static Ticket buildInvalidTestTicketScreeningDateInvalid(){
        return Ticket.builder()
                .name("John")
                .surname("Doe")
                .screeningId(1L)
                .screeningDate(LocalDateTime.now())
                .seatId(2L)
                .type(TicketType.STUDENT)
                .build();
    }

    @Test
    public void isValidWithInvalidHolderSurname() {
        Ticket invalidTicket = TicketValidatorTest.buildInvalidTestTicketHolderSurnameInvalid();

        boolean isValid = ticketValidator.isValid(invalidTicket);

        Assertions.assertFalse(isValid);
    }

    private static Ticket buildInvalidTestTicketHolderSurnameInvalid(){
        return Ticket.builder()
                .name("John")
                .surname("Bi-ni")
                .screeningId(1L)
                .screeningDate(LocalDateTime.now().plusDays(2))
                .seatId(2L)
                .type(TicketType.STUDENT)
                .build();
    }


    @Test
    public void isValidWithMultipleScreenings() {
        // Arrange
        Ticket validTicket1 = Ticket.builder()
                .screeningId(1L)
                .seatId(2L)
                .build();
        Ticket validTicket2 = Ticket.builder()
                .screeningId(2L)
                .seatId(3L)
                .build();
        List<Ticket> ticketList = Arrays.asList(validTicket1, validTicket2);

        assertThrows(IllegalReservationException.class, () -> ticketValidator.isValid(ticketList));
    }

}
