import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GeneticAlgorithm
{
    private ArrayList<Chromosome> population; // population with chromosomes
    private ArrayList<Integer> occurrences; // list with chromosomes (indices) based on fitness score

    GeneticAlgorithm()
    {
        this.population = null;
        this.occurrences = null;
    }

    Chromosome run(int multitudeOfGenes, int populationSize, double mutationProbability, int maxSteps, int minFitness)
    {
        //We initialize the population
        this.initializePopulation(multitudeOfGenes, populationSize);

        Random r = new Random();

        for(int step = 0; step < maxSteps; step++)
        {
            //Initialize the new generated population
            ArrayList<Chromosome> newPopulation = new ArrayList<>();
            for(int i = 0; i < populationSize / 2; i++)
            {
                //We choose two chromosomes from the population
                //Due to how fitnessBounds ArrayList is generated, the probability of
                //selecting a specific chromosome depends on its fitness score
                int xIndex;
                int yIndex;
                Chromosome xParent;
                if (multitudeOfGenes >= 3){
                    xIndex = this.occurrences.get(r.nextInt(this.occurrences.size()));
                    xParent = this.population.get(xIndex);
                    yIndex = this.occurrences.get(r.nextInt(this.occurrences.size()));
                    while(xIndex == yIndex)
                    {
                        yIndex = this.occurrences.get(r.nextInt(this.occurrences.size()));
                    }
                }else{
                    xIndex = multitudeOfGenes/2;
                    xParent = this.population.get(xIndex);
                    yIndex = multitudeOfGenes/2;
                }
                Chromosome yParent = this.population.get(yIndex);
                //We generate the children of the two chromosomes
                Chromosome[] children = this.reproduce(multitudeOfGenes, xParent, yParent);

                //We might then mutate the children
                if(r.nextDouble() < mutationProbability)
                {
                    children[0].mutate();
                    children[1].mutate();
                }
                //...and finally add them to the new population
                newPopulation.add(children[0]);
                newPopulation.add(children[1]);
            }
            this.population = new ArrayList<>(newPopulation);
            //We sort the population so the one with the greater fitness is first
            this.population.sort(Collections.reverseOrder());
            //If the chromosome with the best fitness is acceptable we return it
            if(this.population.get(0).getFitness() >= minFitness) {
                return this.population.get(0);
            }
            //We update the occurrences list
            this.updateOccurrences();
        }
        return this.population.get(0);
    }

    //We initialize the population by creating random chromosomes
    void initializePopulation(int multitude, int populationSize)
    {
        this.population = new ArrayList<>();
        for(int i = 0; i < populationSize; i++)
        {
            this.population.add(new Chromosome(multitude));
        }
        this.updateOccurrences();
    }

    //Updates the list that contains indexes of the chromosomes in the population ArrayList
    void updateOccurrences()
    {
        this.occurrences = new ArrayList<>();
        for(int i = 0; i < this.population.size(); i++)
        {
            for(int j = 0; j < this.population.get(i).getFitness(); j++)
            {
                //Each chromosome index exists in the list as many times as its fitness score
                //By creating this list this way, and choosing a random index from it,
                //the greater the fitness score of a chromosome, the greater chance it will be chosen.
                this.occurrences.add(i);
            }
        }
    }

    //Reproduces two chromosomes and generates their children
    Chromosome[] reproduce(int multitude, Chromosome x, Chromosome y)
    {
        Random r = new Random();

        //Randomly choose the intersection point
        int intersectionPoint = r.nextInt(multitude);
        int[] firstChild = new int[multitude];
        int[] secondChild = new int[multitude];

        //The first child has the left side of the x chromosome up to the intersection point...
        //The second child has the left side of the y chromosome up to the intersection point...
        for(int i = 0; i < intersectionPoint; i++)
        {
            firstChild[i] = x.getGenes()[i];
            secondChild[i] = y.getGenes()[i];
        }
        //the right side of the y chromosome after the intersection point (for the first)
        //the right side of the x chromosome after the intersection point (for the second)
        for(int i = intersectionPoint; i < firstChild.length; i++)
        {
            firstChild[i] = y.getGenes()[i];
            secondChild[i] = x.getGenes()[i];
        }
        return new Chromosome[] {new Chromosome(multitude, firstChild), new Chromosome(multitude, secondChild)};
    }
}