package jogoTabuleiro.Tabuleiro;

import java.util.Random;

public class Dado {
    private static final Random  gerardorNum = new Random();

    public static int[] rolarDados(){
        int dado1 = gerardorNum.nextInt(6) + 1;
        int dado2 = gerardorNum.nextInt(6) + 1;

        return new int[]{dado1, dado2};
    }

    public static int calcularPassos(int[] dados){
        return dados[0] + dados[1];
    }
}
