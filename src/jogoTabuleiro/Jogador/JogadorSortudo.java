package jogoTabuleiro.Jogador;

import jogoTabuleiro.Abstraçao.TipoJogador;

import java.util.Random;

public class JogadorSortudo extends Jogador {

    public JogadorSortudo(String nome, String cor, int posicao) {
        super(nome, cor, posicao, TipoJogador.SORTUDO);
    }

    @Override
    protected boolean aplicarRegraEspecialDados(int[] dados, int passos) {
        if (passos < 7) {
            System.out.format("-> %s: Rolagem de %d inválida (mín. 7).\n", getTipo().getDescricao(), passos);
            return false;
        }
        return true;
    }
}
