    About the app:

I have treated this task more as a prototype app than production-grade code. Following this philosophy, I wanted to
have a cleanly designed app that is decoupled and open for extension, but only provides minimal, required, functionality.

This approach is also applicable to testing - I have written some sanity tests for the main logic, places where stuff can go wrong
and some integration tests for the database. I've tried to come up with test cases that could break the app,
but please don't expect 100% coverage and a bulletproof app, as that would take a lot of time and I felt it wouldn't
really make sense.

I implemented all the functionalities required in the pdf file (TouK_recruitment_task_ticket_booking_app_basic).
I assumed that reserving tickets is different from buying tickets, so the app provides 3 states for a seat -
AVAILABLE, RESERVED, BOUGHT. Reservations are valid for 24 hours. Every day at 2AM, a task runner deletes outdated
reservations from the database and frees the reserved seats.

As for the database - I have used a relational db (MySQL, but this is no personal preference). The entities are pretty
heavily constrained and I don't think there's much redundancy, but some logic is not enforced by the db - for instance,
there is a RoomRowEntity which has a numberOfSeats field, but the number of actual seats referencing that RoomRow isn't
bound by that value. In a production app this should of course be changed. I've put some indexes on columns that are
frequently looked up.


    Usage:
The app exposes 3 rest endpoints -

/screening/find-screenings/{date} - get
/seat/get-seats/{screeningId} - get
/tickets/book-tickets/ - post

The user calls the screenings endpoint with a date and time and receives a sorted list of screenings in the time period
(date - 30mins, date + 4h).

The user then calls the /get-seats/ endpoint with a particular screeningId and receives a list of seats for this particular
screening - as there cannot be a single place left over in a row between two already reserved places, the user receives
full list - available and unavailable seats.

The user then calls the post /book-tickets/ endpoint with a json list of tickets. Tickets are of the format:
{
  "name":"John",
  "surname":"Doe",
  "screeningId":1,
  "screeningDate":"2023-11-02 04:15:00",
  "seatId":1,
  "type":"ADULT"
}

I have wondered a bit about how to realize that part - I've thought about implementing some simple session mechanism or mock
that would enable the user to book tickets in a timeframe, but, as the pdf requirements stated, operations were to be
exposed as REST, so that would be in conflict. It would also take a significant amount of work to provide some security
user-based session mechanisms and test them. In the end I've chosen a completely stateless approach where the whole
request is processed in one server call.

As the business use case states that the user chooses a particular screening, and I think it makes sense to only book for
one screening, all tickets have to be for the same screening.

Tickets can have different holders - it makes sense in a real world scenario (someone can pick up his ticket without
the person making the reservation) and was cleaner to implement :).

The holder's name and surname should obey the specification provided in the .pdf file. If the surname is two-part,
dashed, both parts must obey the minimum 3 letter length and other rules.

If the tickets are valid, the system logs them into the db, stamps a reservation expiration date and returns a .json
Reservation object of the form List<Ticket>, ticketCostSum, expirationDate.

I've also provided a
screenings/find-screenings/{movie}/{date} - get endpoint
to find screenings of a particular movie, seemed natural, but I didn't test that very much.

Please read how_to_run.txt for running manual.
