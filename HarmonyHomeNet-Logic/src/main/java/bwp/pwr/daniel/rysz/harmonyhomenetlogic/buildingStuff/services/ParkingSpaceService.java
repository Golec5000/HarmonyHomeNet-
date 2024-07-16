package bwp.pwr.daniel.rysz.harmonyhomenetlogic.buildingStuff.services;

import bwp.pwr.daniel.rysz.harmonyhomenetlogic.buildingStuff.entitys.ParkingSpace;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParkingSpaceService {
    void deleteById(UUID id);
    Optional<ParkingSpace> findById(UUID id);
    Optional<ParkingSpace> findByNumber(int parkingSpaceNumber);
    List<ParkingSpace> findAll();
    void save(ParkingSpace parkingSpace);
}
