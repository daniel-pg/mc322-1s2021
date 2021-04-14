package mc322.lab03;

public class Animacao {
    private AquarioLombriga lombrigaAnimada; // É um ótimo nome de variável...
    String sequenciaAcoes;
    private int instAtual; // Posição atual no vetor de instruções/estado da animação

    Animacao (String instrucoes) {
        int ta, tl, pl;
        ta = Integer.parseInt(instrucoes.substring(0,2));
        tl = Integer.parseInt(instrucoes.substring(2,4));
        pl = Integer.parseInt(instrucoes.substring(4,6));

        lombrigaAnimada = new AquarioLombriga(ta, tl, pl);
        sequenciaAcoes = instrucoes.substring(6, instrucoes.length());
        instAtual = 0; // Primeira posição do vetor que contém uma instrução
    }

    public void passo() {
        // Fim da animação
        if (instAtual >= sequenciaAcoes.length()) {
            return;
        }

        switch (sequenciaAcoes.charAt(instAtual)) {
            case 'C' -> lombrigaAnimada.crescer();
            case 'M' -> lombrigaAnimada.mover();
            case 'V' -> lombrigaAnimada.virar();
        }

        instAtual++;
    }

    public String apresenta() {
        return lombrigaAnimada.apresenta();
    }
}
