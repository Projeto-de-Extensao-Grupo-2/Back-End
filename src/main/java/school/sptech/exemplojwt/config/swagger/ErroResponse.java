package school.sptech.exemplojwt.config.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Modelo usado apenas para documentar, no Swagger, o corpo de erro padrão
 * devolvido pelo Spring Boot (spring.web.error.include-message=always).
 */
@Schema(name = "ErroResponse", description = "Corpo de erro padrão retornado pela API")
public class ErroResponse {

    @Schema(description = "Momento em que o erro ocorreu", example = "2026-11-01T14:32:10.123+00:00")
    private String timestamp;

    @Schema(description = "Código HTTP do erro", example = "404")
    private Integer status;

    @Schema(description = "Descrição do status HTTP", example = "Not Found")
    private String error;

    @Schema(description = "Mensagem explicando o erro", example = "Mesa não encontrada")
    private String message;

    @Schema(description = "Caminho da requisição que originou o erro", example = "/mesas/99")
    private String path;

    public String getTimestamp() { return timestamp; }
    public Integer getStatus() { return status; }
    public String getError() { return error; }
    public String getMessage() { return message; }
    public String getPath() { return path; }
}
