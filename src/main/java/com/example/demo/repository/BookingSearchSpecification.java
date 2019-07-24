package com.example.demo.repository;

import com.example.demo.domain.Booking;
import com.example.demo.domain.Customer;
import com.example.demo.domain.Team;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BookingSearchSpecification {

    private BookingSearchSpecification() {
    }

    public static Specification<Booking> bookingForTeams(Collection<String> teamNames) {
        return (Root<Booking> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Subquery<Customer> subquery = query.subquery(Customer.class);
            Root<Team> teamRoot = subquery.from(Team.class);
            subquery.select(teamRoot.get("customers"));
            subquery.where(teamRoot.get("name").in(teamNames));
            predicates.add(root.get("customer").in(subquery));

            return predicates.isEmpty() ?
                    cb.conjunction() :
                    cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
