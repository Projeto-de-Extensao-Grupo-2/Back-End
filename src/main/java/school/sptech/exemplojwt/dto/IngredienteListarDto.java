package school.sptech.exemplojwt.dto;

public class IngredienteListarDto {

    private Long id;
    private String nome;
    private String unidadeMedida;

    public IngredienteListarDto(Long id, String nome, String unidadeMedida) {
        this.id = id;
        this.nome = nome;
        this.unidadeMedida = unidadeMedida;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getUnidadeMedida() { return unidadeMedida; }
}