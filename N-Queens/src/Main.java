import java.util.Random;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nGive the number of Queens : ");
        int multitudeOfGenes = scanner.nextInt();
        int minFitness = 0;
        for (int i = 1; i <= multitudeOfGenes; i++){
            // (n-1) + (n-2) + ... + (n-n)
            minFitness += (multitudeOfGenes - i);
        }
        System.out.println("minFitness: " + minFitness);
        GeneticAlgorithm algorithm = new GeneticAlgorithm();
        long start = System.currentTimeMillis();
        Chromosome solution = algorithm.run(multitudeOfGenes,1000, 0.05, 1000, minFitness);
        long end = System.currentTimeMillis();
        solution.print();
        System.out.println("\nSearch time: " + (double)(end - start) / 1000 + " seconds");
        System.out.println(solution.getFitness());
    }
}
