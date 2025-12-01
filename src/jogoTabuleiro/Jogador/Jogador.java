package jogoTabuleiro.Jogador;
import jogoTabuleiro.Abstraçao.TipoJogador;
import jogoTabuleiro.Interface.Main;
import jogoTabuleiro.Tabuleiro.Dado;

import java.util.*;

public class Jogador {
	protected String nome;
	protected String cor;
	protected int posicao;
	protected TipoJogador tipo;
	protected boolean ativo = true;
	private int turnosBloqueados = 0;
	
	public Jogador(String nome, String cor, int posicao, TipoJogador tipo) {
		this.nome = nome;
		this.cor = cor;
		this.posicao = posicao;
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public String getCor() {
		return cor;
	}

	public int getPosicao() {
		return posicao;
	}
	
	public boolean getAtivo() {
		return ativo;
	}
	
	public int getTurnosBloqueados() {
		return turnosBloqueados;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}
	
	public void setTurnosBloqueados(int turnosBloqueados) {
		this.turnosBloqueados = turnosBloqueados;
	}
	
	public int mudarPosicao() {
		if (!this.ativo){
            System.out.format("Jogador %s(%s) está com turno bloqueiado.\n", this.getCor(), this.getNome());
            return 0;
        }
        int[] dados = {0, 0};
        int passos = 0;
        boolean continuarJogando = true;

        while(continuarJogando){
            dados = Dado.rolarDados();
            passos = Dado.calcularPassos(dados);
            if(!aplicarRegraEspecialDados(dados, passos)) {
                System.out.format("Rolagem Inválida pela regra especial. Tentando nova rolagem de dados...");
                continue;
            }
            this.posicao += passos;
            System.out.format("\nJogador: %s %s%s  tirou %d, %d, e andou %d (Nova Posição %d).", this.getCor(), this.getNome(),Main.ANSI_RESET, this.getPosicao(), dados[0], dados[1], passos,this.getPosicao());
            if (dados[0] == dados[1]) {
                System.out.format("-> Jogador: %s pegou dados iguais e ganhou o direito de jogar mais uma vez!\n", this.nome);
                continuarJogando = true; // Repete o loop
            } else {
                continuarJogando = false; // Encerra a jogada
            }
        }
        return passos;
	}

    protected boolean aplicarRegraEspecialDados(int[] dados, int passos) {
        return true; // Padrão: Jogador Normal aceita qualquer rolagem
    }

    public boolean verificarNome(String nome) {
        return this.nome.equalsIgnoreCase(nome);
    }

    public void decrementarBloqueio() {
        if (this.turnosBloqueados > 0) {
            this.turnosBloqueados--;
            if (this.turnosBloqueados == 0) {
                this.ativo = true;
            }
        }
    }
    public TipoJogador getTipo() {
        return tipo;
    }

    public void verificarAtivo() {
        if (this.turnosBloqueados > 0) {
            this.ativo = false;
            System.out.format("\nJogador: %s %s%s (%s) está BLOQUEADO por %d rodada(s).\n",
                    this.cor, this.nome, Main.ANSI_RESET, this.tipo.getDescricao(), this.turnosBloqueados);
        } else {
            this.ativo = true;
        }
    }
}
