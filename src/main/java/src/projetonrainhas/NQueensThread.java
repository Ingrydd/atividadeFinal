package src.projetonrainhas;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class NQueensThread {

    public static void main(String[] args) {
        int n = 8;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o tamanho do tabuleiro (valor de N): ");
        while (true) {
            try {
                n = Integer.parseInt(scanner.nextLine());
                if (n < 1) {
                    System.out.println("Por favor, digite um número maior que 0.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            }
        }

        if (n > 3) {
            NQueensThread nq = new NQueensThread();
            nq.solve(n);
            System.out.printf("Fim da execução com %d rainhas.\n", n);
        } else {
            System.out.println("Nenhuma possibilidade encontrada.");
        }
    }

    public void solve(int n) {
        Set<Integer> cols = new HashSet<>();
        Set<Integer> posDiag = new HashSet<>();
        Set<Integer> negDiag = new HashSet<>();

        backtrack(new int[n], 0, n, cols, posDiag, negDiag);
    }

    private void backtrack(int[] board, int row, int n, Set<Integer> cols, Set<Integer> posDiag, Set<Integer> negDiag) {
        if (row == n) {
            printSolution(board);
            return;
        }

        for (int col = 0; col < n; col++) {
            int posD = row + col;
            int negD = row - col;

            if (cols.contains(col) || posDiag.contains(posD) || negDiag.contains(negD)) {
                continue;
            }

            board[row] = col;
            cols.add(col);
            posDiag.add(posD);
            negDiag.add(negD);

            // Cria uma nova thread para a próxima linha
            Thread thread = new Thread(() -> backtrack(board, row + 1, n, cols, posDiag, negDiag));
            thread.start();
            try {
                thread.join(); // Aguarda a finalização da thread
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            cols.remove(col);
            posDiag.remove(posD);
            negDiag.remove(negD);
        }
    }

      private void printSolution(int[] board) {
    int n = board.length;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (board[i] == j) {
                System.out.print("Q ");
            } else {
                System.out.print(". ");
            }
        }
        System.out.println();
    }
    System.out.println();
  }
}
