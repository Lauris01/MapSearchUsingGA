package com.mygdx.game.thetask;

public class GeneticAlgorithm {
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;

    public GeneticAlgorithm(int populationSize, double mutationRate, double
            crossoverRate, int elitismCount) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
    }

    public Population initPopulation(int chromosomeLength) {
        Population population = new Population(this.populationSize,
                chromosomeLength);
        return population;
    }

    public double calcFitness(Individual individual, Map maze, Robot.TYPE robotType) {
        int[] chromosome = individual.getChromosome();
        Robot robot = new Robot(chromosome, maze, 100, robotType);
        robot.run();
        double s = maze.scoreRoute(robot);
        individual.setFitness(s);
        return s;
    }

    public void evalPopulation(Population population, Map maze, Robot.TYPE robotType) {
        double populationFitness = 0;
        for (Individual individual : population.getIndividuals()) {
            populationFitness += this.calcFitness(individual, maze, robotType);
        }
        population.setPopulationFitness(populationFitness);
    }


    public Population mutatePopulation(Population population) {
        Population newPopulation = new Population(this.populationSize);

        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {

            Individual individual = population.getFittest(populationIndex);
            for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {

                if (populationIndex >= this.elitismCount) {
                    if (this.mutationRate > Math.random()) {
                        individual.setGene(geneIndex, Individual.getRandomGene());
                    }
                }
            }
            newPopulation.setIndividual(populationIndex, individual);
        }
        return newPopulation;
    }

    public boolean isTerminationConditionMet(int generation, int maxGenerations) {
        return maxGenerations < generation;
    }

    public Individual selectParent(Population population) {
        // Get individuals
        Individual individuals[] = population.getIndividuals();
        double populationFitness = population.getPopulationFitness();
        double rouletteWheelPosition = Math.random() * populationFitness;
        double spinWheel = 0;
        for (Individual individual : individuals) {
            spinWheel += individual.getFitness();
            if (spinWheel >= rouletteWheelPosition) {
                return individual;
            }
        }
        return individuals[population.size() - 1];
    }

    public Population crossoverPopulation(Population population) {
        // Create new population
        Population newPopulation = new Population(population.size());
        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex <  population.size(); populationIndex++) {
            Individual parent1 = population.getFittest(populationIndex);
            if (this.crossoverRate > Math.random() && populationIndex >=this.elitismCount) {
                Individual offspring = new Individual(parent1.getChromosomeLength());
                Individual parent2 = this.selectParent(population);

                for (int geneIndex = 0; geneIndex < parent1.
                        getChromosomeLength(); geneIndex++) {

                    if (0.5 > Math.random()) {
                        offspring.setGene(geneIndex,
                                parent1.getGene(geneIndex));
                    } else {
                        offspring.setGene(geneIndex,
                                parent2.getGene(geneIndex));
                    }
                }
                newPopulation.setIndividual(populationIndex,
                        offspring);
            } else {
                newPopulation.setIndividual(populationIndex, parent1);
            }
        }
        return newPopulation;
    }

}
