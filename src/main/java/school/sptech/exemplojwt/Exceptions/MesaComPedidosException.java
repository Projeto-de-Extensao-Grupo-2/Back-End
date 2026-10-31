package school.sptech.exemplojwt.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class MesaComPedidosException extends RuntimeException {

    public MesaComPedidosException(String message) {
        super(message);
    }
}
