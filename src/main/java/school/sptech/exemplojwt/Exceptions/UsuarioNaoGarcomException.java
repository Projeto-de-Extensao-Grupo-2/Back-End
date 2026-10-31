package school.sptech.exemplojwt.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UsuarioNaoGarcomException extends RuntimeException {

    public UsuarioNaoGarcomException(String message) {
        super(message);
    }
}
