/*
 * Daniel Paulo Garcia © 2021
 *
 * Jogo de "Peg Solitaire" para computador.
 */

package mc322.lab04;

import java.util.Scanner;

public class AppRestaUm {

    public static void main(String[] args) {
        /* A função main() serve apenas para testar as classes do programa, a correção automática provavelmente não vai
        chamá-la. */
        Scanner nomeCsv = new Scanner(System.in);
        executaJogo(nomeCsv.next());
    }

    static String[] executaJogo(String caminhoCsv) {
        Tabuleiro tabuleiro = new Tabuleiro();

        // Recebe os comandos do arquivo CSV
        CSVReader csv = new CSVReader();
        csv.setDataSource(caminhoCsv);
        String[] comandos = csv.requestCommands();

        String[] etapasJogo = new String[comandos.length + 1]; // Um espaço a mais pra configuração inicial.
        etapasJogo[0] = tabuleiro.desenhaTabuleiro();

        System.out.println("Tabuleiro inicial:");
        tabuleiro.imprimeTabuleiroFormatado();

        for (int i = 0; i < comandos.length; i++) {
            etapasJogo[i+1] = tabuleiro.desenhaTabuleiro();
            System.out.println("Source: " + comandos[i].substring(0, 2));
            System.out.println("Target: " + comandos[i].substring(3, 5));

            tabuleiro.moverPeca(tabuleiro.converteCoordenadas(comandos[i]));
            tabuleiro.imprimeTabuleiroFormatado();
        }

        return etapasJogo;
    }

}
