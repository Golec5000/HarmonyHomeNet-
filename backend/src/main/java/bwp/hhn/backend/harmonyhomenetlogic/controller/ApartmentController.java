package bwp.hhn.backend.harmonyhomenetlogic.controller;

import bwp.hhn.backend.harmonyhomenetlogic.configuration.exeptions.customErrors.ApartmentNotFoundException;
import bwp.hhn.backend.harmonyhomenetlogic.configuration.exeptions.customErrors.UserNotFoundException;
import bwp.hhn.backend.harmonyhomenetlogic.service.interfaces.ApartmentsService;
import bwp.hhn.backend.harmonyhomenetlogic.utils.request.ApartmentRequest;
import bwp.hhn.backend.harmonyhomenetlogic.utils.response.ApartmentResponse;
import bwp.hhn.backend.harmonyhomenetlogic.utils.response.PossessionHistoryResponse;
import bwp.hhn.backend.harmonyhomenetlogic.utils.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bwp/hhn/api/v1/apartment")
@RequiredArgsConstructor
public class ApartmentController {

    private final ApartmentsService apartmentsService;

    // GET
    @GetMapping("/get-apartment-by-id")
    public ResponseEntity<ApartmentResponse> getApartmentById(@RequestParam UUID apartmentId) throws ApartmentNotFoundException {
        return ResponseEntity.ok(apartmentsService.getApartmentById(apartmentId));
    }

    @GetMapping("/get-apartment-by-user")
    public ResponseEntity<List<ApartmentResponse>> getApartmentsByUserId(@RequestParam UUID userId) throws ApartmentNotFoundException, UserNotFoundException {
        return ResponseEntity.ok(apartmentsService.getCurrentApartmentsByUserId(userId));
    }

    @GetMapping("/get-all-apartments")
    public ResponseEntity<List<ApartmentResponse>> getAllApartments() {
        return ResponseEntity.ok(apartmentsService.getAllApartments());
    }

    @GetMapping("/possession-history-for-apartment")
    public ResponseEntity<PossessionHistoryResponse> getPossessionHistory(@RequestParam UUID apartmentId, @RequestParam UUID userId) throws ApartmentNotFoundException, UserNotFoundException {
        return ResponseEntity.ok(apartmentsService.getPossessionHistory(apartmentId, userId));
    }

    @GetMapping("/current-apartment-residents")
    public ResponseEntity<List<UserResponse>> getCurrentResidents(@RequestParam UUID apartmentId) throws ApartmentNotFoundException {
        return ResponseEntity.ok(apartmentsService.getCurrentResidents(apartmentId));
    }

    @GetMapping("/whole-possession-history-for-apartment")
    public ResponseEntity<List<PossessionHistoryResponse>> getApartmentPossessionHistory(@RequestParam UUID apartmentId) throws ApartmentNotFoundException {
        return ResponseEntity.ok(apartmentsService.getApartmentPossessionHistory(apartmentId));
    }

    @GetMapping("/user-apartments")
    public ResponseEntity<List<ApartmentResponse>> getAllUserApartments(@RequestParam UUID userId) throws UserNotFoundException {
        return ResponseEntity.ok(apartmentsService.getAllUserApartments(userId));
    }

    @GetMapping("/apartment-residents")
    public ResponseEntity<List<UserResponse>> getAllResidentsByApartmentId(@RequestParam UUID apartmentId) throws ApartmentNotFoundException {
        return ResponseEntity.ok(apartmentsService.getAllResidentsByApartmentId(apartmentId));
    }

    //POST
    @PostMapping("/create-possession-history")
    public ResponseEntity<PossessionHistoryResponse> createPossessionHistory(@RequestParam UUID apartmentId, @RequestParam UUID userId) throws ApartmentNotFoundException, UserNotFoundException {
        return ResponseEntity.ok(apartmentsService.createPossessionHistory(apartmentId, userId));
    }

    @PostMapping("/create-apartment")
    public ResponseEntity<ApartmentResponse> createApartment(@RequestBody ApartmentRequest request) {
        return ResponseEntity.ok(apartmentsService.createApartments(request));
    }

    //DELETE
    @DeleteMapping("/delete-apartment")
    public ResponseEntity<String> deleteApartment(@RequestParam UUID apartmentId) throws ApartmentNotFoundException {
        return ResponseEntity.ok(apartmentsService.deleteApartment(apartmentId));
    }

    @DeleteMapping("/delete-possession-history/{possessionHistoryId}")
    public ResponseEntity<String> deletePossessionHistory(@PathVariable Long possessionHistoryId) throws ApartmentNotFoundException {
        return ResponseEntity.ok(apartmentsService.deletePossessionHistory(possessionHistoryId));
    }

    //PUT
    @PutMapping("/update-apartment")
    public ResponseEntity<ApartmentResponse> updateApartment(@RequestParam UUID apartmentId, @RequestBody ApartmentRequest request) throws ApartmentNotFoundException {
        return ResponseEntity.ok(apartmentsService.updateApartment(request, apartmentId));
    }

    @PutMapping("/end-possession-history")
    public ResponseEntity<PossessionHistoryResponse> endPossessionHistory(@RequestParam UUID apartmentId, @RequestParam UUID userId) throws ApartmentNotFoundException, UserNotFoundException {
        return ResponseEntity.ok(apartmentsService.endPossessionHistory(apartmentId, userId));
    }

}