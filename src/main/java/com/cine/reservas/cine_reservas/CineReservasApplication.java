package com.cine.reservas.cine_reservas;

import com.cine.reservas.cine_reservas.model.MovieGenreEnum;
import com.cine.reservas.cine_reservas.repository.BookingRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class CineReservasApplication {
	/*@Autowired
	private BookingRepository bookingRepository;*/
	public static void main(String[] args) {
		SpringApplication.run(CineReservasApplication.class, args);
	}
	/*@PostConstruct
	public void testQuery() {
		var results = bookingRepository.findHorrorMovieBookings(MovieGenreEnum.HORROR, LocalDate.now().minusDays(7), LocalDate.now());
		results.forEach(booking -> System.out.println("Reserva: " + booking.getId()));
	}*/
}
