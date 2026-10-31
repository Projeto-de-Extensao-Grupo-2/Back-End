package school.sptech.exemplojwt.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.exemplojwt.dto.ProdutoCriacaoDto;
import school.sptech.exemplojwt.dto.ProdutoMapper;
import school.sptech.exemplojwt.entity.Categoria;
import school.sptech.exemplojwt.entity.Produto;
import school.sptech.exemplojwt.repository.CategoriaRepository;
import school.sptech.exemplojwt.repository.FichaTecnicaRepository;
import school.sptech.exemplojwt.repository.ItemPedidoRepository;
import school.sptech.exemplojwt.repository.ProdutoRepository;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FichaTecnicaRepository fichaTecnicaRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public ProdutoService(ProdutoRepository produtoRepository,
                          CategoriaRepository categoriaRepository,
                          FichaTecnicaRepository fichaTecnicaRepository,
                          ItemPedidoRepository itemPedidoRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.fichaTecnicaRepository = fichaTecnicaRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public Produto criar(ProdutoCriacaoDto dto) {
        Categoria categoria = buscarCategoria(dto.getCategoriaId());
        return produtoRepository.save(ProdutoMapper.toEntity(dto, categoria));
    }

    public Produto atualizar(Long id, ProdutoCriacaoDto dto) {
        Produto produto = buscarPorId(id);
        Categoria categoria = buscarCategoria(dto.getCategoriaId());
        ProdutoMapper.atualizar(produto, dto, categoria);
        return produtoRepository.save(produto);
    }


    @Transactional
    public void deletar(Long id) {
        Produto produto = buscarPorId(id);

        if (itemPedidoRepository.existsByProdutoId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Não é possível excluir um produto que já está em pedidos");
        }

        fichaTecnicaRepository.deleteByProdutoId(id);
        produtoRepository.delete(produto);
    }

    private Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    private Categoria buscarCategoria(Long categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
    }
}