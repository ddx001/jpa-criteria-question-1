package com.example.demo;

import com.example.demo.domain.Booking;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

    @Autowired
    private DemoService demoService;

    @Test
    public void queryTest() {
        demoService.addCustomer("John");
        demoService.addCustomer("Mary");
        demoService.addCustomer("Steven");
        demoService.addCustomer("Alissa");
        demoService.addCustomer("Gerry");

        demoService.addTeam("Team 1", "John", "Alissa");
        demoService.addTeam("Team 2", "Mary", "John");
        demoService.addTeam("Team 3", "Gerry", "Steven");

        demoService.addBooking("Booking 1", "John");
        demoService.addBooking("Booking 2", "Alissa");
        demoService.addBooking("Booking 3", "Mary");
        demoService.addBooking("Booking 4", "Gerry");
        demoService.addBooking("Booking 5", "Steven");
        demoService.addBooking("Booking 6", "John");
        demoService.addBooking("Booking 7", "Alissa");
        demoService.addBooking("Booking 8", "Mary");
        demoService.addBooking("Booking 9", "Steven");

        List<Booking> bookingForTeams = demoService.findBookingsForTeams("Team 1", "Team 2");

        Set<String> bookingDescriptions =
                bookingForTeams.stream()
                        .map(Booking::getDescription)
                        .collect(Collectors.toSet());

        assertThat(bookingDescriptions).containsExactlyInAnyOrder("Booking 1", "Booking 2", "Booking 3", "Booking 6", "Booking 7", "Booking 8");
    }
}
