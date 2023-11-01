-- Populate the room-rows table with test values and specific IDs
INSERT INTO room_rows (id, seats_in_row, cinema_room_id) VALUES
--room 1
(1, 3, 1),
(2, 3, 1),

--room 2
(3,2,2),
(4,2,2),

-- room 3
(5, 1, 3),
(6, 2, 3);


-- Populate the seats table with test values and specific IDs
INSERT INTO seats (id, room_id, room_row_id, seat_number, screening_id, is_occupied, status) VALUES
--room 1 screening 1
(1, 1, 1, 1, 1, false, 'AVAILABLE'),
(2, 1, 1, 2, 1, false, 'AVAILABLE'),
(3, 1, 1, 3, 1, true, 'BOUGHT'),
(4, 1, 2, 1, 1, false, 'AVAILABLE'),
(5, 1, 2, 2, 1, false, 'AVAILABLE'),
(6, 1, 2, 3, 1, true, 'BOUGHT'),

--room 1 screening 4
(14, 1, 1, 1, 4, false, 'AVAILABLE'),
(15, 1, 1, 2, 4, false, 'AVAILABLE'),
(16, 1, 1, 3, 4, true, 'BOUGHT'),
(17, 1, 2, 1, 4, false, 'AVAILABLE'),
(18, 1, 2, 2, 4, false, 'AVAILABLE'),
(19, 1, 2, 3, 4, true, 'BOUGHT'),

--room 2 screening 2
(7, 2, 3, 2, 2, false, 'AVAILABLE'),
(8, 2, 3, 2, 2, true, 'BOUGHT'),
(9, 2, 4, 1, 2, false, 'AVAILABLE'),
(10, 2, 4, 2, 2, false, 'AVAILABLE'),

-- room 2 screening 5
(20, 2, 3, 1, 5, false, 'AVAILABLE'),
(21, 2, 3, 2, 5, false, 'AVAILABLE'),
(22, 2, 4, 1, 5, true, 'BOUGHT'),
(23, 2, 4, 2, 5, false, 'AVAILABLE'),

--room 3 screening 3
(11, 3, 5, 1, 3, false, 'AVAILABLE'),
(12, 3, 6, 2, 3, true, 'BOUGHT'),
(13, 3, 6, 1, 3, false, 'AVAILABLE'),

--room 3 screening 6
(24, 3, 5, 1, 6, true, 'BOUGHT'),
(25, 3, 6, 2, 6, false, 'AVAILABLE'),
(26, 3, 6, 1, 6, false, 'AVAILABLE');



