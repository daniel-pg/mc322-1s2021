package mc322.lab05;

public class GameUtils {
    public static int[] decodeCoord(String encodedCoord) {
        assert (encodedCoord.length() == 2);

        // Converte as coordenadas convencionais em índices da matriz, na qual 'a8' se torna (0,0).
        int[] coord = new int[2];
        coord[0] = Math.abs(encodedCoord.charAt(1) - '8');  // Linha
        coord[1] = encodedCoord.charAt(0) - 'a';            // Coluna
        return coord;
    }

    public static String encodeCoord(int[] coord) {
        return "" + Character.toString('a' + coord[1]) + ('8' - coord[0]);
    }

    public static void printBoard(String strBoard) {
        // Imprime o tabuleiro na tela com a formatação adequada.
        String[] linhas = strBoard.split("\\n");
        for (int row = Board.BOARD_SIZE; row > 0; row--) {
            System.out.print(row);
            String linha = linhas[Board.BOARD_SIZE - row];
            for (int i = 0; i < linha.length(); i++) {
                System.out.print(" " + linha.charAt(i));
            }
            System.out.print("\n");
        }
        System.out.print("  a b c d e f g h\n\n");
    }

}
