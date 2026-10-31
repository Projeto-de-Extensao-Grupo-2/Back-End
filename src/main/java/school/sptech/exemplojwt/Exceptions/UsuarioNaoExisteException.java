package school.sptech.exemplojwt.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UsuarioNaoExisteException extends RuntimeException {

    public UsuarioNaoExisteException(String message) {
        super(message);
    }
}
