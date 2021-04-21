/*
 * Daniel Paulo Garcia © 2021
 */

package mc322.lab04;

public class Tabuleiro {
    private final int largura, altura;
    private final int tmhBorda; // Largura/Altura do quadrado recortado das bordas do tabuleiro
    private final Peca[][] tabuleiro;

    Tabuleiro() {
        // Dimensões padrão, caso não seja especificado
        this(7,7,2);
    }

    Tabuleiro(int largura, int altura, int tmhBorda) {
        this.largura = largura;
        this.altura = altura;
        this.tmhBorda = tmhBorda;

        tabuleiro = new Peca[largura][altura];
        char tipo;

        for (int i = 0; i < largura; i++) {
            for (int j = 0; j < altura; j++) {
                if (i == largura/2 && j == altura/2) {
                    // Peça central sempre tem um buraco.
                    tipo = '-';
                } else if (estaNaCruz(i, j)) {
                    // A peça está na cruz, então é uma estaca.
                    tipo = 'P';
                } else {
                    // A peça está nas bordas, logo ela é um espaço bloqueado.
                    tipo = ' ';
                }

                tabuleiro[i][j] = new Peca(tipo);
            }
        }
    }

    public String desenhaTabuleiro() {
        StringBuilder ret = new StringBuilder();

        for (int i = 0; i < largura; i++) {
            for (int j = 0; j < altura; j++) {
                ret.append(tabuleiro[i][j].tipo);
            }
            ret.append('\n');
        }

        return ret.toString();
    }

    public void imprimeTabuleiroFormatado() {
        String desenhoTabuleiro = desenhaTabuleiro();
        String[] linhas = desenhoTabuleiro.split("\\n");

        for (int i = 0; i < altura; i++) {
            System.out.print(altura - i);
            System.out.print(" ");
            System.out.println(linhas[i]);
        }

        System.out.print("  ");
        for (int c = 0; c < largura; c++) {
            System.out.print(Character.toString('a' + c));
        }

        System.out.println("\n");
    }

    public void moverPeca(int[][] coords) {
        int x1 = coords[0][0], y1 = coords[0][1];
        int x2 = coords[1][0], y2 = coords[1][1];
        int diff, media;

        // Checar se as coordenadas estão dentro do tabuleiro
        if (!estaNaCruz(x1, y1) || !estaNaCruz(x2, y2)) {
            return;
        }

        // Checar se as coordenadas estão na mesma coluna ou linha.
        if (x1 == x2 && y1 == y2) {
            return; // Origem coincide com destino.
        } else if (x1 == x2) {
            // Mesma coluna (x)
            diff = Math.abs(y1 - y2);
            media = (y1 + y2) / 2;

            if (diff == 2 && tabuleiro[media][x1].tipo == 'P') {
                // As peças estão a uma distância de duas unidades e tem outra peça no meio
                tabuleiro[media][x1].tipo = '-';
            } else {
                // As peças ou estão longes uma da outra, ou tem um espaço no meio.
                return;
            }
        } else if (y1 == y2) {
            // Mesma linha (y)
            diff = Math.abs(x1 - x2);
            media = (x1 + x2) / 2;

            if (diff == 2 && tabuleiro[y1][media].tipo == 'P') {
                // As peças estão a uma distância de duas unidades e tem outra peça no meio
                tabuleiro[y1][media].tipo = '-';
            } else {
                // As peças ou estão longes uma da outra, ou tem um espaço no meio.
                return;
            }
        }

        tabuleiro[y1][x1].tipo = '-';
        tabuleiro[y2][x2].tipo = 'P';
    }

    public boolean estaNaCruz(int i, int j) {
        return (tmhBorda <= i && i < (largura - tmhBorda) || tmhBorda <= j && j < (altura - tmhBorda));
    }

    public int[][] converteCoordenadas(String coordsCodificada) {
        int[][] coords = new int[2][2];

        coords[0][0] = coordsCodificada.charAt(0) - 'a';
        coords[0][1] = altura - (coordsCodificada.charAt(1) - '0');
        coords[1][0] = coordsCodificada.charAt(3) - 'a';
        coords[1][1] = altura - (coordsCodificada.charAt(4) - '0');

        return coords;
    }
}
