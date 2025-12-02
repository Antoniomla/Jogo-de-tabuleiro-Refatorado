package jogoTabuleiro.Tabuleiro;

import jogoTabuleiro.Abstracao.TipoCasa;
import jogoTabuleiro.Interface.Main;
import jogoTabuleiro.Jogador.Jogador;

import java.util.*;

public class Tabuleiro {
    public static final int TOTAL_CASAS = 40;
    private final List<Casa> casas = new ArrayList<>();
    private int turnoAtual = 1;

    public Tabuleiro() {
        inicializarCasas();
    }

    private void inicializarCasas() {
        for (int i = 0; i < TOTAL_CASAS; i++) {
            // Cria todas como NORMAL primeiro
            casas.add(new Casa(i + 1, TipoCasa.NORMAL));
        }

        // Marca as casas especiais
        marcarCasa(TipoCasa.SORTE, 5, 15, 30);
        marcarCasa(TipoCasa.STOP, 10, 25, 38);
        marcarCasa(TipoCasa.SURPRESA, 13);
        marcarCasa(TipoCasa.TRAPACEIRO, 17, 27);
        marcarCasa(TipoCasa.CORINGA, 20, 35);
    }

    /**
     * Marca o tipo de casa em posições específicas.
     */
    private void marcarCasa(TipoCasa tipo, int... numeros) {
        for (int numero : numeros) {
            if (numero > 0 && numero <= TOTAL_CASAS) {
                // Posicao - 1 para pegar o índice
                casas.set(numero - 1, new Casa(numero, tipo));
            }
        }
    }

    public int getTurnoAtual() { return turnoAtual; }
    public void mudarTurno() { this.turnoAtual += 1; }

    /**
     * Retorna a casa na posição indexada (1 a 40).
     */
    public Casa getCasa(int posicao) {
        if (posicao <= 0) return casas.get(0);
        if (posicao >= TOTAL_CASAS) return casas.get(TOTAL_CASAS - 1);
        return casas.get(posicao - 1);
    }

    // Sessão de controle de jogo:

    public Jogador verificarVitorioso(Jogador jogador) {
        if (jogador.getPosicao() >= TOTAL_CASAS) {
            System.out.println("\nTEMOS UM VENCEDOR!!!\n\n");
            System.out.format("Jogador(a): %s venceu!\n", jogador.getNome());
            return jogador;
        }
        return null;
    }

    // Relatório Geral movido para Jogo ou uma classe de UI
    public void relatorioGeral(List<Jogador> jogadores) {
        System.out.format("\n======= Status Geral (Turno %d) =======\n", getTurnoAtual());
        for (Jogador j : jogadores) {
            System.out.format("Jogador: %s %s%s\nTipo: %s\nPosição: %d\nTurnos Bloqueados: %d\n\n",
                    j.getCor(), j.getNome(), Main.ANSI_RESET,
                    j.getTipo().getDescricao(), j.getPosicao(), j.getTurnosBloqueados());
        }
        System.out.format("======================================\n\n");
    }

    /**
     * Aplica o efeito da casa atual do jogador.
     */
    public void verificarCasa(Jogador jogador, ArrayList<Jogador> jogadores, Tabuleiro tabuleiro, Scanner read, Random random) {
        if (jogador.getPosicao() > 0 && jogador.getPosicao() <= TOTAL_CASAS) {
            Casa casaAtual = getCasa(jogador.getPosicao());
            // Chama a estratégia da Casa para aplicar o efeito
            casaAtual.aplicarEfeito(jogador, jogadores, tabuleiro, read, random);
        }
    }
}
