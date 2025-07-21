package br.com.laghettohoteis.api_biglietto.main.domain.modification;

import br.com.laghettohoteis.api_biglietto.main.domain.guest.Guest;
import br.com.laghettohoteis.api_biglietto.main.domain.hotel.Hotel;
import br.com.laghettohoteis.api_biglietto.main.domain.reservation.Reservation;
import br.com.laghettohoteis.api_biglietto.main.domain.reservation.ReservationPension;
import br.com.laghettohoteis.api_biglietto.main.domain.reservation.ReservationStatus;
import br.com.laghettohoteis.api_biglietto.main.domain.reservation.ReservationType;
import br.com.laghettohoteis.api_biglietto.main.domain.ticket.Ticket;
import br.com.laghettohoteis.api_biglietto.main.domain.ticket.TicketType;
import br.com.laghettohoteis.api_biglietto.main.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "modification")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Modification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Timestamp date;

    @NotNull
    private String operation;

    @NotNull
    @Column(name = "table_name")
    private String tableName;

    @NotNull
    private String field;

    @NotNull
    private String valueBefore;

    @NotNull
    private String valueAfter;


    @ManyToOne
    private Guest guest;

    @ManyToOne
    private Hotel hotel;

    @ManyToOne
    private Reservation reservation;

    @ManyToOne
    private ReservationPension pension;

    @ManyToOne
    private ReservationStatus status;

    @ManyToOne
    private ReservationType reservationType;

    @ManyToOne
    private TicketType ticketType;

    @ManyToOne
    private Ticket ticket;

    @ManyToOne
    private User user;

    @ManyToOne
    private User userModified;

    public Modification(Timestamp date, String operation, String tableName, String field, String valueBefore, String valueAfter, User userModified) {
        this.date = date;
        this.operation = operation;
        this.tableName = tableName;
        this.field = field;
        this.valueBefore = valueBefore;
        this.valueAfter = valueAfter;
        this.userModified = userModified;
    }

    public Modification(Timestamp date, String operation, String tableName, String field, String valueBefore, String valueAfter, User user, User userModified) {
        this.date = date;
        this.operation = operation;
        this.tableName = tableName;
        this.field = field;
        this.valueBefore = valueBefore;
        this.valueAfter = valueAfter;
        this.user = user;
        this.userModified = userModified;
    }

    public Modification(Timestamp date, String operation, String tableName, String field, String valueBefore, String valueAfter, Hotel hotel, User userModified) {
        this.date = date;
        this.operation = operation;
        this.tableName = tableName;
        this.field = field;
        this.valueBefore = valueBefore;
        this.valueAfter = valueAfter;
        this.hotel = hotel;
        this.userModified = userModified;
    }

    public Modification(Timestamp date, String operation, String tableName, String field, String valueBefore, String valueAfter, ReservationType reservationType, User userModified) {
        this.date = date;
        this.operation = operation;
        this.tableName = tableName;
        this.field = field;
        this.valueBefore = valueBefore;
        this.valueAfter = valueAfter;
        this.reservationType = reservationType;
        this.userModified = userModified;
    }

    public Modification(Timestamp date, String operation, String tableName, String field, String valueBefore, String valueAfter, ReservationPension pension, User userModified) {
        this.date = date;
        this.operation = operation;
        this.tableName = tableName;
        this.field = field;
        this.valueBefore = valueBefore;
        this.valueAfter = valueAfter;
        this.pension = pension;
        this.userModified = userModified;
    }

    public Modification(Timestamp date, String operation, String tableName, String field, String valueBefore, String valueAfter, ReservationStatus status, User userModified) {
        this.date = date;
        this.operation = operation;
        this.tableName = tableName;
        this.field = field;
        this.valueBefore = valueBefore;
        this.valueAfter = valueAfter;
        this.status = status;
        this.userModified = userModified;
    }

    public Modification(Timestamp date, String operation, String tableName, String field, String valueBefore, String valueAfter, Reservation reservation, User userModified) {
        this.date = date;
        this.operation = operation;
        this.tableName = tableName;
        this.field = field;
        this.valueBefore = valueBefore;
        this.valueAfter = valueAfter;
        this.reservation = reservation;
        this.userModified = userModified;
    }

    public Modification(Timestamp date, String operation, String tableName, String field, String valueBefore, String valueAfter, Ticket ticket, User userModified) {
        this.date = date;
        this.operation = operation;
        this.tableName = tableName;
        this.field = field;
        this.valueBefore = valueBefore;
        this.valueAfter = valueAfter;
        this.ticket = ticket;
        this.userModified = userModified;
    }

    public Modification(Timestamp date, String operation, String tableName, String field, String valueBefore, String valueAfter, TicketType ticketType, User userModified) {
        this.date = date;
        this.operation = operation;
        this.tableName = tableName;
        this.field = field;
        this.valueBefore = valueBefore;
        this.valueAfter = valueAfter;
        this.ticketType = ticketType;
        this.userModified = userModified;
    }

    public Modification(Timestamp date, String operation, String tableName, String field, String valueBefore, String valueAfter, Guest guest, User userModified) {
        this.date = date;
        this.operation = operation;
        this.tableName = tableName;
        this.field = field;
        this.valueBefore = valueBefore;
        this.valueAfter = valueAfter;
        this.guest = guest;
        this.userModified = userModified;
    }
}
