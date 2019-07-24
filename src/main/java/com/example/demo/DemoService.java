package com.example.demo;

import com.example.demo.domain.Booking;
import com.example.demo.domain.Customer;
import com.example.demo.domain.Team;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.demo.repository.BookingSearchSpecification.bookingForTeams;
import static java.lang.String.format;
import static org.springframework.data.jpa.domain.Specifications.where;

@Service
@RequiredArgsConstructor
public class DemoService {

    private final CustomerRepository customerRepository;
    private final TeamRepository teamRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    public Customer addCustomer(String name) {
        Customer customer = new Customer();
        customer.setName(name);
        return customerRepository.save(customer);
    }

    @Transactional
    public Team addTeam(String name, String... customerNames) {
        Team team = new Team();
        team.setName(name);

        Arrays.stream(customerNames)
                .map(customerRepository::findOneByName)
                .filter(Objects::nonNull)
                .forEach(customer -> team.getCustomers().add(customer));

        return teamRepository.save(team);
    }

    @Transactional
    public Booking addBooking(String description, String customerName) {
        Customer customer = customerRepository.findOneByName(customerName);
        if (customer == null) {
            throw new IllegalArgumentException(format("Customer not found %s", customerName));
        }

        Booking booking = new Booking();
        booking.setDescription(description);
        booking.setCustomer(customer);

        return bookingRepository.save(booking);
    }

    public List<Booking> findBookingsForTeams(String... teamNames) {
        return bookingRepository.findAll(where(bookingForTeams(Arrays.asList(teamNames))));
    }
}
