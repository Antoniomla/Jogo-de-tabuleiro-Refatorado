package jogoTabuleiro.Jogador;

import jogoTabuleiro.Abstraçao.TipoJogador;

import java.util.Random;

public class JogadorAzarado extends Jogador {

    public JogadorAzarado(String nome, String cor, int posicao) {
        super(nome, cor, posicao, TipoJogador.AZARADO);
    }

    // Sobrescreve apenas a regra especial de rolagem (máximo 6)
    @Override
    protected boolean aplicarRegraEspecialDados(int[] dados, int passos) {
        if (passos > 6) {
            System.out.format("-> %s: Rolagem de %d inválida (máx. 6).\n", getTipo().getDescricao(), passos);
            return false;
        }
        return true;
    }
}
