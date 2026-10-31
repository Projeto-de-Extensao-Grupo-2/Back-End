package school.sptech.exemplojwt.dto;

import school.sptech.exemplojwt.entity.Categoria;
import school.sptech.exemplojwt.entity.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoMapper {

    private ProdutoMapper() {
    }

    public static Produto toEntity(ProdutoCriacaoDto dto, Categoria categoria) {
        Produto produto = new Produto();
        produto.setCategoria(categoria);
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setAtivo(dto.getAtivo() == null ? true : dto.getAtivo());
        return produto;
    }

    public static void atualizar(Produto produto, ProdutoCriacaoDto dto, Categoria categoria) {
        produto.setCategoria(categoria);
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        if (dto.getAtivo() != null) {
            produto.setAtivo(dto.getAtivo());
        }
    }

    public static ProdutoListarDto toListarDto(Produto produto) {
        return new ProdutoListarDto(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getCategoria().getId(),
                produto.getCategoria().getNome(),
                produto.isAtivo()
        );
    }

    public static List<ProdutoListarDto> toListarDto(List<Produto> produtos) {
        List<ProdutoListarDto> dtos = new ArrayList<>();

        for (Produto p : produtos) {
            dtos.add(toListarDto(p));
        }

        return dtos;
    }
}