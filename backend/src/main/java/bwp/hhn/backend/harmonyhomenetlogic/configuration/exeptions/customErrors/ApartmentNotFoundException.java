package bwp.hhn.backend.harmonyhomenetlogic.configuration.exeptions.customErrors;

public class ApartmentNotFoundException extends RuntimeException {
    public ApartmentNotFoundException(String message) {
        super("Apartment is not found" + message);
    }
}
