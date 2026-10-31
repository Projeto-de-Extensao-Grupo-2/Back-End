package school.sptech.exemplojwt.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ProdutoEmPedidoException extends RuntimeException {

    public ProdutoEmPedidoException(String message) {
        super(message);
    }
}
