package edu.zespol5.pkhotelbackend.extra;

import java.util.List;
import java.util.Optional;

public interface ExtraRepository {
    Extra save(Extra extra);
    Optional<Extra> findExtraById(int id);
    List<Extra> findAll();
    List<Extra> findExtrasByName(String name);
    List<Extra> findExtrasByPricePerDayIsLessThan(double price);
    List<Extra> findExtrasByPricePerDayIsGreaterThan(double price);
}
