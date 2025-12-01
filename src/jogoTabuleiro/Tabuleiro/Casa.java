package jogoTabuleiro.Tabuleiro;

import jogoTabuleiro.Abstraçao.TipoCasa;
import jogoTabuleiro.Abstraçao.TipoJogador;
import jogoTabuleiro.Interface.Main;
import jogoTabuleiro.Jogador.Jogador;
import jogoTabuleiro.Jogador.FabricadeJogador;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Casa {
    private final int numero;
    private final TipoCasa tipo;

    public Casa(int numero, TipoCasa tipo) {
        this.numero = numero;
        this.tipo = tipo;
    }

    public int getNumero() {
        return numero;
    }

    public TipoCasa getTipo() {
        return tipo;
    }

    /**
     * Aplica o efeito da casa no jogador.
     * Esta é a lógica central, mas é mínima (Strategy Pattern: a casa sabe como se comportar).
     */
    public void aplicarEfeito(Jogador jogador, ArrayList<Jogador> todosJogadores, Tabuleiro tabuleiro, Scanner read, Random rand) {
        if (this.tipo == TipoCasa.NORMAL) return;

        System.out.format("\n%s caiu na %s!\n", jogador.getCor() + jogador.getNome() + Main.ANSI_RESET, this.tipo.getNome());

        switch (this.tipo) {
            case SORTE -> casaDaSorte(jogador);
            case STOP -> casaStop(jogador);
            case TRAPACEIRO -> casaTrapaceiro(jogador, todosJogadores, read);
            case CORINGA -> casaCoringa(jogador, todosJogadores);
            case SURPRESA -> casaSurpresa(jogador, todosJogadores, rand);
            default -> {}
        }
    }

    private void casaDaSorte(Jogador j) {
        j.setPosicao(j.getPosicao() + 3);
        System.out.println("Bônus de Sorte! Avança 3 casas.");
    }

    private void casaStop(Jogador j) {
        j.setTurnosBloqueados(1);
        j.setPosicao(j.getPosicao() + 1); // Avança 1 casa por cair na stop, se for regra
        System.out.println("PERDE A VEZ! Bloqueado por 1 turno.");
    }

    private void casaTrapaceiro(Jogador j, ArrayList<Jogador> jogadores, Scanner read) {
        // Lógica de interação com usuário ainda está aqui, mas idealmente seria em um InputHandler
        System.out.println("Instrução: Escolha um jogador para voltar ao início (Posição 0).");

        Jogador alvo = null;
        while (alvo == null) {
            System.out.format("Digite o NOME do jogador que deseja mover: ");
            String nomeRemover = read.nextLine();

            for (Jogador jog : jogadores) {
                if (jog.verificarNome(nomeRemover)) {
                    alvo = jog;
                    break;
                }
            }
            if (alvo == null) {
                System.out.println("Jogador não encontrado. Tente novamente.");
            }
        }
        alvo.setPosicao(0);
        alvo.setTurnosBloqueados(0); // Garante que não está bloqueado
        System.out.format("Jogador(a) %s movido(a) com sucesso para a casa 0.\n", alvo.getNome());
    }

    private void casaCoringa(Jogador j, ArrayList<Jogador> jogadores) {
        Jogador maisAtrasado = null;
        for (Jogador jog : jogadores) {
            if (maisAtrasado == null || jog.getPosicao() < maisAtrasado.getPosicao()) {
                maisAtrasado = jog;
            }
        }

        if (j.getPosicao() > maisAtrasado.getPosicao()) {
            int tempPosicao = j.getPosicao();
            j.setPosicao(maisAtrasado.getPosicao());
            maisAtrasado.setPosicao(tempPosicao);
            System.out.format("TROCA DE POSIÇÃO! Você trocou de lugar com %s(%s).\n", maisAtrasado.getCor() + maisAtrasado.getNome() + Main.ANSI_RESET, maisAtrasado.getCor());
        } else {
            System.out.println("Você já é o jogador mais atrasado. Nenhuma troca realizada.");
        }
    }

    private void casaSurpresa(Jogador j, ArrayList<Jogador> jogadores, Random r) {
        TipoJogador tipoAtual = j.getTipo();
        TipoJogador novoTipo;

        // Sorteia um novo tipo que seja diferente do atual
        do {
            novoTipo = TipoJogador.values()[r.nextInt(TipoJogador.values().length)];
        } while (novoTipo == tipoAtual);

        // 1. Cria a nova instância de jogador (mantendo o estado)
        Jogador novoJogador = FabricadeJogador.criarJogador(j.getNome(), j.getCor(), j.getPosicao(), novoTipo);
        novoJogador.setTurnosBloqueados(j.getTurnosBloqueados());
        novoJogador.setAtivo(j.getAtivo());

        // 2. Substitui na lista
        int indice = jogadores.indexOf(j);
        jogadores.set(indice, novoJogador);

        System.out.format("MUDANÇA DE CLASSE! Você agora é um %s.\n", novoTipo.getDescricao());
    }
}
