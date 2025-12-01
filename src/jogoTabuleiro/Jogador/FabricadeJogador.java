package jogoTabuleiro.Jogador;

import jogoTabuleiro.Abstraçao.TipoJogador;

import java.util.Random;

public class FabricadeJogador {
    public static Jogador criarJogador(String nome, String cor, int posicao, TipoJogador tipo) {
        if (tipo == TipoJogador.AZARADO) {
            return new JogadorAzarado(nome, cor, posicao);
        } else if (tipo == TipoJogador.SORTUDO) {
            return new JogadorSortudo(nome, cor, posicao);
        } else {
            return new Jogador(nome, cor, posicao, TipoJogador.NORMAL);
        }
    }

    public static Jogador sortearOTipoECriar(String nome, String cor, int posicao, Random rand) {
        int tipoIndex = rand.nextInt(TipoJogador.values().length);
        TipoJogador tipoSorteado = TipoJogador.values()[tipoIndex];
        return criarJogador(nome, cor, posicao, tipoSorteado);
    }

}
