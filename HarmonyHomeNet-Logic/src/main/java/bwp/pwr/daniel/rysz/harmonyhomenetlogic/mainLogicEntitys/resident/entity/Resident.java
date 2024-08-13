package bwp.pwr.daniel.rysz.harmonyhomenetlogic.mainLogicEntitys.resident.entity;

import bwp.pwr.daniel.rysz.harmonyhomenetlogic.mainLogicEntitys.apartment.entitys.Apartment;
import bwp.pwr.daniel.rysz.harmonyhomenetlogic.mainLogicEntitys.basment.entity.Basement;
import bwp.pwr.daniel.rysz.harmonyhomenetlogic.mainLogicEntitys.forum.entity.Post;
import bwp.pwr.daniel.rysz.harmonyhomenetlogic.mainLogicEntitys.parkingSpace.entity.ParkingSpace;
import bwp.pwr.daniel.rysz.harmonyhomenetlogic.mainLogicEntitys.user.entity.User;
import bwp.pwr.daniel.rysz.harmonyhomenetlogic.utils.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "residents")
public class Resident extends User {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resident")
    @JsonManagedReference
    private List<Basement> basementList;

    @JoinColumn(name = "apartment_id")
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private Apartment apartment;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resident")
    @JsonManagedReference
    private List<ParkingSpace> parkingSpaces;

    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Post> posts;


}
