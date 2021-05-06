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

        // Tabuleiro do jogo, onde tudo acontece
        Board board = new Board();

        // Cria o histórico do jogo, um vetor de Strings, contendo o estado do tabuleiro para cada lance.
        String[] boardHistory = new String[commands.length + 1];
        int boardHistoryIndex = 0;
        boardHistory[boardHistoryIndex++] = board.toString();

        // Exibe o estado inicial do Board.
        System.out.println("Tabuleiro inicial:");
        board.printBoard();

        String[] srcDestPair;
        int[] srcDecoded, dstDecoded;
        boolean sucessoMovimento = false;

        // Executa os comandos até que não sobre nenhum.
        for (String command : commands) {
            srcDestPair = command.split(":");
            System.out.println("Source: " + srcDestPair[0] + "\nTarget: " + srcDestPair[1]);

            // Converte o comando em coordenadas da matriz e o executa.
            srcDecoded = Board.decodeCoord(srcDestPair[0]);
            dstDecoded = Board.decodeCoord(srcDestPair[1]);
            sucessoMovimento = board.requestMove(srcDecoded, dstDecoded);
            if (sucessoMovimento) {
                // Exibe o estado resultante do Board na tela e o armazena no histórico.
                boardHistory[boardHistoryIndex++] = board.toString();
                board.printBoard();
            } else {
                // Se o movimento for inválido, mostre uma mensagem de erro.
                System.out.println("Movimento invalido!\n");
            }
        }

        // Finaliza retornando o histórico do jogo e exportando o estado final do tabuleiro.
        if (sucessoMovimento) {
            board.exportToFile(csv);
        } else {
            // Último movimento inválido
            csv.exportState(new String[]{"erro"});
        }

        return boardHistory;
    }
}
