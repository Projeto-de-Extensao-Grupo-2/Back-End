package school.sptech.exemplojwt.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API do Restaurante",
                description = """
                        API REST para gestão de um restaurante: **usuários** (garçons e gestores), \
                        **mesas**, **pedidos**, **itens de pedido**, **produtos**, **ingredientes** \
                        e **fichas técnicas**.

                        ### Autenticação
                        1. Faça login em `POST /usuarios/login` com e-mail e senha.
                        2. A resposta traz o JWT no campo `token` e também grava o cookie HttpOnly `authToken`.
                        3. O token pode ser enviado de duas formas: pelo cookie `authToken` (enviado \
                        automaticamente pelo navegador) ou pelo header `Authorization: Bearer <token>`.
                        4. No Swagger UI, clique em **Authorize** e cole o token (sem o prefixo `Bearer`).

                        ### Erros
                        Os erros seguem o formato padrão do Spring Boot (`timestamp`, `status`, `error`, \
                        `message`, `path`). Erros de validação de campos retornam `400` com os detalhes \
                        em `errors`.
                        """,
                contact = @Contact(
                        name = "Diego",
                        url = "https://github.com/BandTec/spring-security-jwt-exemplo",
                        email = "diego.lima@sptech.school"
                ),
                license = @License(name = "UNLICENSED"),
                version = "1.0.0"
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Ambiente local")
        },
        tags = {
                @Tag(name = "Usuários", description = "Cadastro, login/logout e ativação de usuários"),
                @Tag(name = "Mesas", description = "Gerenciamento das mesas do restaurante"),
                @Tag(name = "Pedidos", description = "Abertura, acompanhamento e exclusão de pedidos"),
                @Tag(name = "Itens de Pedido", description = "Itens (produtos) que compõem um pedido"),
                @Tag(name = "Produtos", description = "Cardápio: produtos e preços"),
                @Tag(name = "Ingredientes", description = "Ingredientes usados na produção dos produtos"),
                @Tag(name = "Fichas Técnicas", description = "Relação de ingredientes e quantidades por produto")
        }
)
@SecurityScheme(
        name = "Bearer",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Token JWT obtido em `POST /usuarios/login`. Informe apenas o token, sem o prefixo `Bearer`."
)
public class OpenApiConfig {

}
