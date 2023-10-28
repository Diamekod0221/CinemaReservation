package com.ramcel.cinema.reservation.service.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Slf4j
@Sql(scripts = {"/import_movies.sql", "/import_rooms.sql", "/import_screenings.sql"})
public class SeatServiceIntegrationTest {
}
