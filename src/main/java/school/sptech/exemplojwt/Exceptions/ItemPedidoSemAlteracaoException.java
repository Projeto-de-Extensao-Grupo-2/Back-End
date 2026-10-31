package school.sptech.exemplojwt.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ItemPedidoSemAlteracaoException extends RuntimeException {

    public ItemPedidoSemAlteracaoException(String message) {
        super(message);
    }
}
