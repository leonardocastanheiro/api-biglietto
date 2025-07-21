package br.com.laghettohoteis.api_biglietto.main.services;

import br.com.laghettohoteis.api_biglietto.main.domain.hotel.Hotel;
import br.com.laghettohoteis.api_biglietto.main.domain.reservation.PensionTicketType;
import br.com.laghettohoteis.api_biglietto.main.domain.reservation.ReservationPension;
import br.com.laghettohoteis.api_biglietto.main.domain.ticket.TicketType;
import br.com.laghettohoteis.api_biglietto.main.dtos.reservation.pension.PensionTicketTypeCreate;
import br.com.laghettohoteis.api_biglietto.main.dtos.reservation.pension.PensionTicketTypeGet;
import br.com.laghettohoteis.api_biglietto.main.repositories.HotelRepository;
import br.com.laghettohoteis.api_biglietto.main.repositories.PensionRepository;
import br.com.laghettohoteis.api_biglietto.main.repositories.PensionTicketTypeRepository;
import br.com.laghettohoteis.api_biglietto.main.repositories.TicketTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class PensionTicketTypeService {

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    TicketTypeRepository ticketTypeRepository;

    @Autowired
    PensionRepository pensionRepository;

    @Autowired
    PensionTicketTypeRepository pensionTicketTypeRepository;


    public void create(PensionTicketTypeCreate data) {
        if(pensionTicketTypeRepository.existsByTypeIdAndPensionId(data.ticketTypeId(),data.pensionId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma interligação entre essa Pensão e esse tipo de Ticket.");
        }

        Optional<ReservationPension> pensionOptional = pensionRepository.findByIdAndHotelId(data.pensionId(),data.hotelId());

        if(pensionOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pensão não encontrada para esse hotel.");
        }

        ReservationPension pension = pensionOptional.get();

        Optional<TicketType> ticketTypeOptional = ticketTypeRepository.findByIdAndHotelId(data.ticketTypeId(), data.hotelId());

        if(ticketTypeOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de Ticket não encontrado para esse hotel.");
        }

        TicketType ticketType = ticketTypeOptional.get();

        if(data.arrivalDay() < 0 || data.commonDay() < 0 || data.departureDay() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor de Ticket por dia menor que 0.");
        }

        PensionTicketType pensionTicketType = new PensionTicketType(ticketType,pension,data.arrivalDay(),data.commonDay(),data.departureDay());

        pensionTicketTypeRepository.save(pensionTicketType);
    }

    public PensionTicketTypeGet edit(PensionTicketTypeGet data) {
        Optional<PensionTicketType> pensionTicketTypeOptional = pensionTicketTypeRepository.findByIdAndTypeIdAndPensionId(data.id(),data.ticketTypeId(),data.pensionId());

        if(pensionTicketTypeOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma interligação de pensão e tipo de ticket encontrada com esses ID's.");
        }

        PensionTicketType pensionTicketType = pensionTicketTypeOptional.get();

        if(data.arrivalDay() < 0 || data.commonDay() < 0 || data.departureDay() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor de Ticket por dia menor que 0.");
        }

        pensionTicketType.setArrivalDay(data.arrivalDay());
        pensionTicketType.setCommonDay(data.commonDay());
        pensionTicketType.setDepartureDay(data.departureDay());

        pensionTicketTypeRepository.save(pensionTicketType);

        return data;
    }

    public void delete(String id) {
        pensionTicketTypeRepository.deleteById(id);
    }
}
