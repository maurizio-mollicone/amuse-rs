package it.mollik.amuse.amusers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Entity Not Found")
public class EntityNotFoundException extends Exception {
    
    public EntityNotFoundException(String value) {
        super(String.format("EntityNotFoundException %s", value));
    }

    public EntityNotFoundException(Exception e) {
        super(e);
    }
}
