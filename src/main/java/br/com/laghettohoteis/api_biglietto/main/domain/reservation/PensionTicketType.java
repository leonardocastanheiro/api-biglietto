package br.com.laghettohoteis.api_biglietto.main.domain.reservation;


import br.com.laghettohoteis.api_biglietto.main.domain.ticket.TicketType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "reservation_pension_x_ticket_type",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"type_id", "pension_id"})
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class PensionTicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private TicketType type;

    @ManyToOne
    @JoinColumn(name = "pension_id", nullable = false)
    private ReservationPension pension;

    private Long arrivalDay;
    private Long commonDay;
    private Long departureDay;

    public PensionTicketType(TicketType type, ReservationPension pension, Long arrivalDay, Long commonDay, Long departureDay) {
        this.type = type;
        this.pension = pension;
        this.arrivalDay = arrivalDay;
        this.commonDay = commonDay;
        this.departureDay = departureDay;
    }
}
