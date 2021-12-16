package modulos.produtos;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.ComponentePojo;
import pojo.ProdutoPojo;
import pojo.UsuarioPojo;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes de API Rest do modulo de Produtos")
public class ProdutoTest{
    private String token;

    // Muitíssimo usado (BeforeEach) para testes
    // Exemplo para validação de login e senha e capturar token
    @BeforeEach
    public void beforeEach() {
        // Configuração dos dados de API Rest da Lojinha
        baseURI = "http://165.227.93.41";
        // port = 8080;
        basePath = "/lojinha";

        UsuarioPojo usuario =  new UsuarioPojo();
        usuario.setUsuarioLogin("admin");
        usuario.setUsuarioSenha("admin");


        // Obter token do usuario admin
        // Motivo para declarar o token
        this.token = given()
                .contentType(ContentType.JSON)
                .body(usuario)
                .when()
                      .post("/v2/login")
                .then()
                        .extract()
                        .path("data.token");
    }

    @Test
    @DisplayName("Validar os limites proibidos de valor de Produto")
    public void testValidarLimitesProibidosValorProduto(){


        ProdutoPojo produto = new ProdutoPojo();
        produto.setProdutoValor(8000.00);
        produto.setProdutoNome("Playstaxion");


        // Estudar ARRAY, LISTS
        // Muita dificuldade para entender como declarar variável de List<string> ArrayList
        // Necessidade de conhecer mais afundo o conteúdo

        List<String> cor = new ArrayList<>();
        cor.add("Preto");
        cor.add("Branco");

        produto.setProdutoCor(cor);
        produto.setProdutoUrlMock("");


        ComponentePojo componente = new ComponentePojo();
        componente.setComponenteNome("Controle");
        componente.setComponenteQuantidade(1);


        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body("{\n" +
                        "  \"produtoNome\": \"Playstation 5\",\n" +
                        "  \"produtoValor\": 7000.01,\n" +
                        "  \"produtoCores\": [\n" +
                        "    \"Preto\"\n" +
                        "  ],\n" +
                        "  \"produtoUrlMock\": \"\",\n" +
                        "  \"componentes\": [\n" +
                        "    {\n" +
                        "      \"componenteNome\": \"Controle\",\n" +
                        "      \"componenteQuantidade\": 1\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
        .when()
            .post("/v2/produtos")
        .then()
            .assertThat()
                .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                .statusCode(422);
    }

    @Test
    @DisplayName("Validar os limites proibidos de valor de Produto para Produto Com Valor Zerado")
    public void testValidarLimiteProibidoValorProdutoComValorZerado(){
             given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body("{\n" +
                        "  \"produtoNome\": \"Playstation 5\",\n" +
                        "  \"produtoValor\": 0.00,\n" +
                        "  \"produtoCores\": [\n" +
                        "    \"Preto\"\n" +
                        "  ],\n" +
                        "  \"produtoUrlMock\": \"\",\n" +
                        "  \"componentes\": [\n" +
                        "    {\n" +
                        "      \"componenteNome\": \"Controle\",\n" +
                        "      \"componenteQuantidade\": 1\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
            .when()
                 .post("/v2/produtos")
            .then()
                .assertThat()
                    .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                    .statusCode(422);
                        // Status code retornado foi 422
    }
}
