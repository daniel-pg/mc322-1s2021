package mc322.lab05;

import java.util.Scanner;

public class AppDama {

    public static void main(String[] args) {
        Scanner filepath = new Scanner(System.in);
        executaJogo(filepath.next());
    }

    public static String[] executaJogo(String filepath) {
        // Extrai a sequência de comandos a partir do .csv em filepath, na forma e.g. 'a1:b2'
        CSVReader csv = new CSVReader();
        csv.setDataSource(filepath);
        String[] commands = csv.requestCommands();

        // Cria o histórico do Board neste jogo: vetor de Strings, cada uma serializando o Board.
        Board board = new Board();
        String[] boardHistory = new String[commands.length + 1];
        int boardHistoryIndex = 0;
        boardHistory[boardHistoryIndex++] = board.toString();

        // Exibe o estado inicial do Board.
        System.out.println("Tabuleiro inicial:");
        Board.printBoard(boardHistory[0]);

        // Executa os comandos até que não sobre nenhum.
        for (String command : commands) {
            String[] moveSrcToDest = command.split(":");
            System.out.println("Source: " + moveSrcToDest[0] + "\nTarget: " + moveSrcToDest[1]);

            // Converte o comando em índices de matriz e executa o comando.
            int[] srcDecoded = Board.decodeCoord(moveSrcToDest[0]);
            int[] dstDecoded = Board.decodeCoord(moveSrcToDest[1]);
            if (board.movePiece(srcDecoded, dstDecoded)) {
                // Exibe o estado resultante do Board na tela e o armazena no histórico.
                boardHistory[boardHistoryIndex++] = board.toString();
                Board.printBoard(boardHistory[boardHistoryIndex - 1]);
            }
        }

        // Finaliza retornando o histórico do Board neste Jogo.
        return boardHistory;
    }
}
