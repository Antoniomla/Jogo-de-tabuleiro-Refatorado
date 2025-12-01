package jogoTabuleiro.Controles;
import jogoTabuleiro.Jogador.Jogador;
import jogoTabuleiro.Tabuleiro.Tabuleiro;

import java.util.*;

public class Debug {
    private boolean debugAtivo;

    public Debug(boolean debugAtivo){
        this.debugAtivo = debugAtivo;
    }

    public boolean isDebugAtivo() { // Melhor nome
        return debugAtivo;
    }

    public void setDebugAtivo(boolean debugAtivo) {
        this.debugAtivo = debugAtivo;
    }

    /**
     * Move a responsabilidade de leitura para Jogo/Main e apenas retorna a posição forçada.
     */
    public int solicitarPosicao(Jogador j, Scanner r){
        System.out.format("\n\n(DEBUG) Para qual casa você deseja levar o jogador: %s(%s): ", j.getCor(), j.getNome());
        int posicao = r.nextInt();
        r.nextLine(); // Consome a linha

        if (posicao < 0) posicao = 0;
        if (posicao > Tabuleiro.TOTAL_CASAS) posicao = Tabuleiro.TOTAL_CASAS;

        return posicao;

    }

    public void notificarPosicaoForcada(Jogador j, int posicao) {
        System.out.format("\n(DEBUG) Jogador: %s(%s) movido para a casa %d com sucesso\n", j.getCor(), j.getNome(), posicao);
    }
}