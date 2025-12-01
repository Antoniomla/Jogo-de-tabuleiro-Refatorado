package jogoTabuleiro.Controles;

import jogoTabuleiro.Abstraçao.TipoJogador;
import jogoTabuleiro.Jogador.Jogador;
import jogoTabuleiro.Jogador.JogadorAzarado;
import jogoTabuleiro.Jogador.JogadorSortudo;
import jogoTabuleiro.Tabuleiro.Tabuleiro;
import jogoTabuleiro.Interface.Main;
import jogoTabuleiro.Jogador.FabricadeJogador;

import java.util.*;

public class Jogo {
    // Método auxiliar removido, pois a FabricaDeJogador garante os tipos
    // Opcionalmente, pode ser mantido para garantir a diversidade mínima de tipos
    public boolean tiposSuficientes(ArrayList<Jogador> jogadores) {
        Set<TipoJogador> tiposPresentes = new HashSet<>();
        for (Jogador j : jogadores) {
            tiposPresentes.add(j.getTipo());
        }
        // Verifica se há pelo menos 2 tipos diferentes
        return tiposPresentes.size() >= 2;
    }

    /**
     * Centraliza a lógica de cadastro, utilizando a Fabrica.
     * Método reduzido (limpo) e utiliza recursão para validação.
     */
    public ArrayList<Jogador> cadastrarJogadores(Scanner ler, Random rand) {
        ArrayList<Jogador> jogadores = new ArrayList<>();

        System.out.println("Quantos Jogadores irão jogar (Min 2, Max 6): ");
        int quantJogadores = ler.nextInt();
        ler.nextLine();

        if (quantJogadores > 6 || quantJogadores < 2) {
            System.out.println("Quantidade inválida (2-6 jogadores). Tente novamente.");
            return cadastrarJogadores(ler, rand); // Chama recursivamente
        }

        for (int i = 0; i < quantJogadores; i++) {
            System.out.format("\n--- Cadastro do %dº Jogador ---\n", i + 1);
            System.out.println("Digite seu nome: ");
            String nome = ler.nextLine();
            System.out.println("Digite sua cor (ex: 'Vermelho' ou código ANSI): ");
            String cor = ler.nextLine();

            Jogador novoJogador = FabricadeJogador.sortearOTipoECriar(nome, cor, 0, rand);
            jogadores.add(novoJogador);
            System.out.format("Jogador %s (%s) cadastrado como: %s.\n", novoJogador.getCor(), novoJogador.getNome(), novoJogador.getTipo().getDescricao());
        }

        if (!tiposSuficientes(jogadores)) {
            System.out.println("\nInfelizmente os tipos sorteados não possuem diversidade mínima (mínimo 2 tipos diferentes). Recadastrando...\n\n");
            return cadastrarJogadores(ler, rand);
        }

        System.out.println("\nUsuários cadastrados com sucesso!\n\n");
        return jogadores;
    }

    /**
     * Lógica principal da partida (Clean Code - Single Responsibility).
     */
    public void partida(ArrayList<Jogador> jogadores, Tabuleiro tabuleiro, Scanner read, Random rand, Debug debug) {
        Jogador vencedor = null;

        while (vencedor == null) {
            System.out.format("\n======== INÍCIO DO TURNO %d ========\n", tabuleiro.getTurnoAtual());

            for (int i = 0; i < jogadores.size(); i++) {
                Jogador jogadorTurnoAtual = jogadores.get(i);
                jogadorTurnoAtual.verificarAtivo();

                if (jogadorTurnoAtual.getAtivo()) {
                    System.out.format("\nVEZ DO JOGADOR: %s %s %s (Tipo: %s)\n",
                            jogadorTurnoAtual.getCor(), jogadorTurnoAtual.getNome(), Main.ANSI_RESET,
                            jogadorTurnoAtual.getTipo().getDescricao());

                    int passos = 0;

                    if (debug.isDebugAtivo()){
                        int posicaoForcada = debug.solicitarPosicao(jogadorTurnoAtual, read);
                        jogadorTurnoAtual.setPosicao(posicaoForcada); // Move diretamente
                        debug.notificarPosicaoForcada(jogadorTurnoAtual, posicaoForcada);
                    } else {
                        System.out.println("(Pressione ENTER para rolar os dados)");
                        read.nextLine(); // Espera o input para rolar
                        passos = jogadorTurnoAtual.mudarPosicao(); // MudarPosicao refatorado
                    }

                    // Aplica efeitos da casa na posição final
                    tabuleiro.verificarCasa(jogadorTurnoAtual, jogadores, tabuleiro, read, rand);

                    vencedor = tabuleiro.verificarVitorioso(jogadorTurnoAtual);
                    if (vencedor != null) break;

                } else {
                    jogadorTurnoAtual.decrementarBloqueio();
                }
            }

            if (vencedor != null) break;

            tabuleiro.relatorioGeral(jogadores);
            tabuleiro.mudarTurno();
        }
    }
}
