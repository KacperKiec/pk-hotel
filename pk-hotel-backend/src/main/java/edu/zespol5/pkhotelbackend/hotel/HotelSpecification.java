package edu.zespol5.pkhotelbackend.hotel;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class HotelSpecification {

    public static Specification<Hotel> hasName(String name){
        return (root, query, builder) -> {
            if(name == null){
                return builder.conjunction();
            }
            return builder.equal(root.get("name"), name);
        };
    }

    public static Specification<Hotel> hasCountry(List<String> countries){
        return (root, query, builder) -> {
            if(countries == null || countries.isEmpty()){
                return builder.conjunction();
            }
            return root.get("country").in(countries);
        };
    }

    public static Specification<Hotel> hasCity(List<String> cities){
        return (root, query, builder) -> {
            if(cities == null || cities.isEmpty()){
                return builder.conjunction();
            }
            return root.get("city").in(cities);
        };
    }
}
