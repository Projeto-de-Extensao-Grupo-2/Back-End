package school.sptech.exemplojwt.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ProdutoInativoException extends RuntimeException {

    public ProdutoInativoException(String message) {
        super(message);
    }
}
