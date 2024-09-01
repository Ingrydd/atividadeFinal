package src.projetonrainhas;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class NQueens {
  int total = 0;

  public static void main(String[] args) {
    int n = 0;
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
      // Cria uma instaancia de NQueens e resolve o problema
      NQueens nq = new NQueens();
      nq.solve(n);
      System.out.printf("Fim da execucao com %d rainhas.\n", n);
    } else {
      System.out.printf("Nenhuma posibilidade encontrada\n", n);
    }

    scanner.close();
  }

  public void solve(int n) {
    // Conjuntos para rastrear colunas e diagonais usadas
    Set<Integer> cols = new HashSet<>();
    Set<Integer> posDiag = new HashSet<>();
    Set<Integer> negDiag = new HashSet<>();

    // Inicia a resolucao a partir da primeira linha
    backtrack(new int[n], 0, n, cols, posDiag, negDiag);
  }

  private void backtrack(int[] board, int row, int n, Set<Integer> cols, Set<Integer> posDiag, Set<Integer> negDiag) {
    // Se todas as rainhas foram colocadas, imprime a solucao
    if (row == n) {
      printSolution(board);
      return;
    }

    for (int col = 0; col < n; col++) {
      int posD = row + col;
      int negD = row - col;

      // Verifica se a coluna ou as diagonais ja estao ocupadas
      if (cols.contains(col) || posDiag.contains(posD) || negDiag.contains(negD)) {
        continue;
        // nao executa as lihas ou comando abaixo do if
      }

      // Coloca a rainha na posicao (row, col)
      board[row] = col;
      cols.add(col);
      posDiag.add(posD);
      negDiag.add(negD);

      // Avanca para a proxima linha
      backtrack(board, row + 1, n, cols, posDiag, negDiag);

      // Remove a rainha da posicao (row, col) e remove as restricoes
      cols.remove(col);
      posDiag.remove(posD);
      negDiag.remove(negD);
    }
  }

  private void printSolution(int[] board) {
        total++;
        int n = board.length;
        System.out.printf("Solucao %d:\n", total);
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
