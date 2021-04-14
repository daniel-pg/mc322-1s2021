package mc322.lab03;

public class AppLab03 {
    public static void main(String[] args) {
        Animacao novaAnimacao = new Animacao("080403MCMVM"); // Apenas um teste

        for (int i = 0; i < novaAnimacao.sequenciaAcoes.length(); i++) {
            System.out.println(novaAnimacao.apresenta());
            novaAnimacao.passo();
        }

        System.out.println(novaAnimacao.apresenta());
    }
}
