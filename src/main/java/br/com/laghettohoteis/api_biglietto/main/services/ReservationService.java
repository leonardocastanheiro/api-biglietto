package br.com.laghettohoteis.api_biglietto.main.services;


import br.com.laghettohoteis.api_biglietto.main.dtos.reservation.reservation.ReservationCreate;
import br.com.laghettohoteis.api_biglietto.main.repositories.PensionRepository;
import br.com.laghettohoteis.api_biglietto.main.repositories.ReservationRepository;
import br.com.laghettohoteis.api_biglietto.main.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    TypeRepository typeRepository;

    @Autowired
    PensionRepository pensionRepository;

    public ResponseEntity<?> create(ReservationCreate data) {

    }
}
