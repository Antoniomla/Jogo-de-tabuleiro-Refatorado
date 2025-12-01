package jogoTabuleiro.Abstraçao;

public enum TipoJogador {
    NORMAL("Jogador Normal"),
    AZARADO("Jogador Azarado"),
    SORTUDO("Jogador Sortudo");

    private String descricao;

    TipoJogador(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
