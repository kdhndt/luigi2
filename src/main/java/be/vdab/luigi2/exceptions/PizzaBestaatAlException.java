package be.vdab.luigi2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Code 409
@ResponseStatus(HttpStatus.CONFLICT)
public class PizzaBestaatAlException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PizzaBestaatAlException(String naam) {
        super("Een pizza bestaat al met volgende naam: " + naam);
    }
}
