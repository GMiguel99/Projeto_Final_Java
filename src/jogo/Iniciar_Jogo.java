package jogo;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Iniciar_Jogo {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Um bravo herói sai à procura de uma grande conquista, ele soube de uma cabana amaldiçoada no meio da floresta.");
		System.out.println("Nesse momento ele está de frente para a porta dessa cabana e se prepara pra enfrentar o perigo.");
		System.out.println("Tudo e todos dentro desta cabana representa uma ameaça.");

		String getNomeHeroi;
		System.out.println("\nDigite o nome do herói: ");
		getNomeHeroi = scanner.nextLine();

		Heroi heroi = new Heroi(getNomeHeroi, 20, 20);

		System.out.println("\nNeste momento, " + heroi.nome + ", adentra a sala principal, nela ele não percebe nada de anormal para uma velha cabana abandonada.");
		System.out.println("Apenas uma pequena escada que o leva a uma pequena bifurcação.");
		System.out.println("O caminho da esquerda o leva a uma pequena sala levemente escura na qual ele ouve um pequeno grunhido.");
		System.out.println("O da direita uma sala até que bem iluminada na qual ele pode perceber um ser de tamanho mediano.");

		System.out.println("\nSelecione qual caminho " + heroi.nome + " irá seguir:\n(utilize 0 para o primeiro caminho ou 1 para o segundo)");
		int getSala;
		getSala = Integer.parseInt(scanner.nextLine());

		Sala sala = new Sala(getSala);
		sala.selecionarSala();

		System.out.println("\nDeseja atacar?\n(utilize n para não, s para sim)");
		char getAtk;
		getAtk = scanner.nextLine().charAt(0);

		boolean vaiAtk = false;
		if(getAtk == 'n') {
			System.out.println("Não vai atacar.");
			vaiAtk = false;
		} else if (getAtk == 's') {
			System.out.println("Vai atacar.");
			vaiAtk = true;
		} else {
			heroi.fugir();
		}

		heroi.vaiAtacar(sala, vaiAtk);

		scanner.close();

		System.out.println("\nCréditos:\nHistória: Gabriel Ferreira Miguel dos Santos\nCódigo: Gabriel Ferreira Miguel dos Santos.");
	}
}

abstract class Personagem {
	protected String nome; // nome da personagem
	protected float pv; // pontos de vida da personagem
	protected float atk; // ataque da personagem
	protected boolean status; // falso = inimigo, true = aliado;
}

class Heroi extends Personagem {

	Heroi(String nome, float pv, float atk) {
		this.nome = nome;
		this.pv = pv;
		this.atk = atk;
		status = true;
	}

	protected void atacar(Inimigo i) {
		i.pv = i.pv - atk;
		if (i.pv < 0) {
			i.pv = 0;
			System.out.println("O inimigo foi morto.");
		} else {
			System.out.println("O inimigo foi atacado e está com " + i.pv + " pontos de vida.");
		}
	}
	protected void atacar(Aliado a) { // atacar um aliado
		a.status = false;
		a.pv = a.pv - atk;
		if (a.pv < 0) {
			System.out.println("O aliado, " + a.nome + ", foi morto.");
		} else {
			System.out.println("O aliado, " + a.nome + ", foi atacado e está com " + a.pv + " pontos de vida.\nAgora ele vai atacar você.\n");
		}
	}
	protected void fugir() {
		System.out.println("\nVocê fugiu.\nVocê é um herói fracassado, não há honra ou glória para o seu nome.\nGAME OVER!!!");
	}
	void vaiAtacar(Sala x, boolean vaiAtacar) {

		if (vaiAtacar == true && x.sala == 0) {

			while (pv > 0 && x.inimigoLista != null) {
				Inimigo atual = x.inimigoLista.peek();
				if (atual == null) {
					break;
				} else {
					atual.atacar(this);
					atacar(atual);
					if (atual.pv <= 0) {
						x.inimigoLista.remove();
					}
				}
			}

			if(pv < 0 && x.inimigoLista != null) {
				System.out.println("Os inimigos mataram você! ");
			} else {
				System.out.println("\nTodos os inimigos foram mortos.\nVocê encontoru a Excalibur.\nAgora " + nome + " é o novo rei!!!");
				System.out.println("Deus sauda " + nome + " o novo Rei!!!\nGame Over!");
			}

		} else if (vaiAtacar == true && x.sala == 1) {

			while (pv > 0 && x.aliadoLista != null) {
				Aliado atual = x.aliadoLista.peek();
				if (atual == null) {
					break;
				} else {
					atual.atacar(this);
					atacar(atual);
					if (x.aliadoLista.peek().pv <= 0) {
						x.aliadoLista.remove();
					}
				}
			}

			if (pv < 0 && x.aliadoLista != null) {
				System.out.println("Seu aliado, " + x.aliadoLista.peek().nome + ", matou você.\nNão haverá honra para o seu nome.\nGame Over!");
			} else {
				System.out.println("Você matou seu aliado, agora você está amaldiçoado.\nNão haverá honra para o seu nome.\nGame Over!");
			}

		} else if (vaiAtacar == false && x.sala == 1) {
			System.out.println("O aliado " + x.aliadoLista.peek().nome + " é um antigo rei.");
			System.out.println("Ao salvá-lo ele te dá 500 moedas de ouro e nomeia " + nome + " cavaleiro e também um nobre herói.");
			System.out.println("Game Over!");

		} else {
			fugir();
		}
	}
}

class Inimigo extends Personagem {

	Inimigo(float pv, float atk) {
		nome = "Inimigo";
		this.pv = pv;
		this.atk = atk;
		status = false;
	}

	protected void atacar(Heroi h) {
		h.pv = h.pv - atk;
		System.out.println("O inimigo atacou.");
		if (h.pv < 0) {
			h.pv = 0;
			System.out.println("O herói, " + h.nome + ", foi morto.");
		} else {
			System.out.println("O herói, " + h.nome + ", foi atacado e está com " + h.pv + " pontos de vida.\n");
		}
	}
	protected void infoInimigo() {
		System.out.println("Dados do inimigo:\nVida: " + pv + "\nAtaque: " + atk + ".\n");
	}
}

class Aliado extends Personagem {

	Aliado(String nome, float pv, float atk) {
		this.nome = nome;
		this.pv = pv;
		this.atk = atk;
		status = true;
	}

	protected void atacar(Heroi h) {
		status = false;
		h.pv = h.pv - atk;
		System.out.println("O aliado atacou.");
		if (h.pv < 0) {
			h.pv = 0;
			System.out.println("O herói, " + h.nome + ", foi morto.");
		} else {
			System.out.println("O herói, " + h.nome + ", foi atacado e está com " + h.pv + " pontos de vida.\n");
		}
	}
	protected void infoAliado() {
		System.out.println("Nome: " + nome + "\nVida: " + pv + "\nAtaque: " + atk);
	}
}

class Sala {

	Queue<Inimigo> inimigoLista;
	Queue<Aliado> aliadoLista;

	protected int sala;

	Sala(int sala) {
		this.sala = sala;
		inimigoLista = new LinkedList<>();
		aliadoLista = new LinkedList<>();
		if (sala == 0) {
			inimigoLista.add(new Inimigo(5, 5));
			inimigoLista.add(new Inimigo(5, 10));
		} else if (sala == 1) {
			aliadoLista.add(new Aliado("George", 5, 5));
		}
	}

	void selecionarSala() {
		if (sala == 0) {
			System.out.println("\nNesta sala possui 2 inimigos.");
			for (Inimigo i : inimigoLista) {
				i.infoInimigo();
			}
		} else if (sala == 1) {
			System.out.println("\nNesta sala possui um aliado.\nDados do aliado:");
			aliadoLista.peek().infoAliado();
		} else {
			System.out.println("Você fugiu.\nVocê é um herói fracassado, não há honra ou glória para o seu nome.\nGAME OVER!");
		}
	}
}