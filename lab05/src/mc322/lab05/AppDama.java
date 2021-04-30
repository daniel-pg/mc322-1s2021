package mc322.lab05;

public class AppDama {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Número de argumentos é insuficiente.");
        } else {
            executaJogo(args[0], args[1]);
        }
    }

    public static String[] executaJogo(String inputFilePath, String outputFilePath) {
        // Extrai a sequência de comandos a partir do .csv de entrada e configura a saída.
        CSVHandling csv = new CSVHandling();
        csv.setDataSource(inputFilePath);
        csv.setDataExport(outputFilePath);
        String[] commands = csv.requestCommands();

        // Cria o histórico do Board neste jogo: vetor de Strings, cada uma serializando o Board.
        Board board = new Board();
        String[] boardHistory = new String[commands.length + 1];
        int boardHistoryIndex = 0;
        boardHistory[boardHistoryIndex++] = board.toString();

        // Exibe o estado inicial do Board.
        System.out.println("Tabuleiro inicial:");
        board.printBoard();

        // Executa os comandos até que não sobre nenhum.
        for (String command : commands) {
            String[] moveSrcToDest = command.split(":");
            System.out.println("Source: " + moveSrcToDest[0] + "\nTarget: " + moveSrcToDest[1]);

            // Converte o comando em índices de matriz e executa o comando.
            int[] srcDecoded = Board.decodeCoord(moveSrcToDest[0]);
            int[] dstDecoded = Board.decodeCoord(moveSrcToDest[1]);
            if (board.requestMove(srcDecoded, dstDecoded)) {
                // Exibe o estado resultante do Board na tela e o armazena no histórico.
                boardHistory[boardHistoryIndex++] = board.toString();
                board.printBoard();
            }
        }

        // Finaliza retornando o histórico do Board neste Jogo.
        return boardHistory;
    }
}
