package jogoTabuleiro.Abstraçao;

public enum TipoCasa {
    NORMAL("Casa Normal"),
    SORTE("Casa da Sorte"),
    STOP("Casa da Stop"),
    SURPRESA("Casa da Surpresa"),
    TRAPACEIRO("Casa do Trapaceiro"),
    CORINGA("Casa da Coringa");

    private final String nome;

    TipoCasa(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}