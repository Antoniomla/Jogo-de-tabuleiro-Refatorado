package jogoTabuleiro.Interface;

import jogoTabuleiro.Controles.Debug;
import jogoTabuleiro.Controles.Jogo;
import jogoTabuleiro.Jogador.Jogador;
import jogoTabuleiro.Tabuleiro.Tabuleiro;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        Random rand = new Random();
        Tabuleiro tabuleiro = new Tabuleiro();
        Jogo jogo = new Jogo();

        Debug debug = configurarDebug(read);

        ArrayList<Jogador> jogadores = jogo.cadastrarJogadores(read, rand);

        jogo.partida(jogadores, tabuleiro, read, rand, debug);

        read.close();
    }

    private static Debug configurarDebug(Scanner read) {
        System.out.println("Deseja ativar o modo Debug? (S/N)");
        String resposta = read.nextLine().trim().toUpperCase();
        boolean debugAtivo = resposta.equals("S");
        if (debugAtivo) {
            System.out.println("Modo Debug ATIVADO. Você poderá forçar posições.");
        } else {
            System.out.println("Modo Normal ATIVADO.");
        }
        return new Debug(debugAtivo);
    }
}