package pojo;

import java.util.List;

public class ProdutoPojo {
    private String produtoNome;
    private List<String> produtoCor;
    private double produtoValor;
    private String produtoUrlMock;

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public List<String> getProdutoCor() {
        return produtoCor;
    }

    public void setProdutoCor(List<String> produtoCor) {
        this.produtoCor = produtoCor;
    }

    public double getProdutoValor() {
        return produtoValor;
    }

    public void setProdutoValor(double produtoValor) {
        this.produtoValor = produtoValor;
    }

    public String getProdutoUrlMock() {
        return produtoUrlMock;
    }

    public void setProdutoUrlMock(String produtoUrlMock) {
        this.produtoUrlMock = produtoUrlMock;
    }
}
