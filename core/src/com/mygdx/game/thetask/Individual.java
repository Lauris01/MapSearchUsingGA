package com.mygdx.game.thetask;

public class Individual {
    private int[] chromosome;
    private double fitness = -1;

    public Individual(int[] chromosome) {
        this.chromosome = chromosome;
    }

    public Individual(int chromosomeLength) {
        this.chromosome = new int[chromosomeLength];
        for (int gene = 0; gene < chromosomeLength; gene++) {
            double rand = Math.random();
            this.setGene(gene,getRandomGene());
        }
    }

    public static int getRandomGene(){
        double rand = Math.random();

        if (0.8 < rand) {
            return 0;
        } else if (0.6 < rand) {
            return 2;
        } else if (0.4 < rand) {
            return 4;
        } else if (0.2 < rand) {
            return 6;
        } else {
            return 8;
        }
    }

    public int[] getChromosome() {
        return this.chromosome;
    }

    public int getChromosomeLength() {
        return this.chromosome.length;
    }

    public void setGene(int offset, int gene) {
        this.chromosome[offset] = gene;
    }

    public int getGene(int offset) {
        return this.chromosome[offset];
    }

    public void setFitness(double f) {
        this.fitness = f;
    }

    public double getFitness() {
        return fitness;
    }

    public String toString() {
        String output = "";
        for (int gene = 0; gene < this.chromosome.length; gene++) {
            output += this.chromosome[gene];
        }
        return output;
    }
}
