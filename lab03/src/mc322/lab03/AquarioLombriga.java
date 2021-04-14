package mc322.lab03;

/**
 * Representa uma única lombriga dentro de um aquário unidimensional.
 */
public class AquarioLombriga {
    private final int tamanhoAquario;  // nunca muda
    private int tamanhoLombriga, posicaoLombriga;
    private boolean orientacaoLombriga; // false = esquerda; true = direita

    AquarioLombriga(int tmhAquario, int tmhLombriga, int posLombriga) {
        this.tamanhoAquario = tmhAquario;
        this.tamanhoLombriga = tmhLombriga;

        // Esse -1 serve pra compensar a notação esquisita que começa a contar da posição 1.
        this.posicaoLombriga = posLombriga - 1;

        this.orientacaoLombriga = true; // A lombriga começa sempre virada para a direita
    }

    public void crescer() {
        // Aumenta o tamanho da cauda da lombriga
        if (orientacaoLombriga && posicaoLombriga > 0) {
            posicaoLombriga--;
            tamanhoLombriga++;
        } else if (!orientacaoLombriga && !estaNoFinal()) {
            tamanhoLombriga++;
        }
    }

    public void mover() {
        // Move a lombriga pra frente ou pra trás dependendo da direção da cabeça
        if (!this.orientacaoLombriga && posicaoLombriga > 0) {
            this.posicaoLombriga--;
        } else if(this.orientacaoLombriga && !estaNoFinal()) {
            this.posicaoLombriga++;
        }
    }

    public void virar() {
        this.orientacaoLombriga = !this.orientacaoLombriga; // Sempre é possível inverter a lombriga de lado
    }

    public String apresenta() {
        StringBuilder result = new StringBuilder();

        result.append("#".repeat(Math.max(0, posicaoLombriga)));

        if (orientacaoLombriga) {
            result.append("@".repeat(Math.max(0, tamanhoLombriga - 1)));
            result.append("O");
        } else {
            result.append("O");
            result.append("@".repeat(Math.max(0, tamanhoLombriga - 1)));
        }

        result.append("#".repeat(Math.max(0, tamanhoAquario - tamanhoLombriga - posicaoLombriga)));
        return result.toString();
    }

    boolean estaNoFinal() {
        return posicaoLombriga >= tamanhoAquario - tamanhoLombriga;
    }
}
