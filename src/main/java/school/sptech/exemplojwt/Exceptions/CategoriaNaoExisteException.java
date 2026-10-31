package school.sptech.exemplojwt.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CategoriaNaoExisteException extends RuntimeException {

    public CategoriaNaoExisteException(String message) {
        super(message);
    }
}
