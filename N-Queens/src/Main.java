import java.util.Random;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nGive the number of Queens : ");
        int numberOfQueens = scanner.nextInt();
        int minFitness = 0;
        for (int i = 1; i <= numberOfQueens; i++){
            // (n-1) + (n-2) + ... + (n-n)
            minFitness += (numberOfQueens - i);
        }
        System.out.println("- Chromosome's Fitness for the solution must be great or equal than " + minFitness + ".");
        System.out.println("- The final chromosome depends on the number of repetitions of reproduction.");
        System.out.println("- If chromosome's fitness is less than " + minFitness + ", the chromosome returned is the best of the last reproduction.");
        GeneticAlgorithm algorithm = new GeneticAlgorithm();
        long start = System.currentTimeMillis();
        Chromosome solution = algorithm.run(numberOfQueens,1000, 0.05, 1000, minFitness);
        long end = System.currentTimeMillis();
        solution.print();
        System.out.println("\nSearch time: " + (double)(end - start) / 1000 + " seconds");
    }
}
