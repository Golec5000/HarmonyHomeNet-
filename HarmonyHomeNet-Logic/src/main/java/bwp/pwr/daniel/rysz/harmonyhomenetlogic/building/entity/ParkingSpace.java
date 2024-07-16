package bwp.pwr.daniel.rysz.harmonyhomenetlogic.building.entity;

import bwp.pwr.daniel.rysz.harmonyhomenetlogic.apartment.entity.Apartment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "parking_spaces")
public class ParkingSpace {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "parking_space_number")
    private int number;

    @Column(name = "apartments")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "building")
    private List<Apartment> apartments;

    @ManyToOne
    @JoinColumn(name = "building_id")
    @JsonBackReference
    private Building building;

}
