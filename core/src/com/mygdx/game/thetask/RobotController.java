package com.mygdx.game.thetask;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class RobotController {

    public static int maxGenerations = 1000;

    public static final Map maze = new Map(new int[][]{
            /**
             * 0 = Empty
             * 1 = Wall
             * 8 = Red base
             * 9 = Blue base
            */
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 9, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 8, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}

    });

    public static void main(String[] args) {



        GeneticAlgorithm ga = new GeneticAlgorithm(200, 0.1, 0.9, 1);

        Population population = ga.initPopulation(50);
        Population population1 = ga.initPopulation(50);
        int generation = 1;

        while (ga.isTerminationConditionMet(generation, maxGenerations) == false) {
            // print fittiest(best) individual from population
            Individual fittest = population.getFittest(0);
            Individual fittest1 = population1.getFittest(0);

            System.out.println(generation + " Best Blue Robot fitness (" + fittest.getFitness() + "): " + fittest.toString());
            System.out.println(generation + " Best Red  Robot fitness (" + fittest1.getFitness() + "): " + fittest1.toString());
            //crossover
            population = ga.crossoverPopulation(population);
            population1 = ga.crossoverPopulation(population1);
            //mutation
            population = ga.mutatePopulation(population);
            population1 = ga.mutatePopulation(population1);

            ga.evalPopulation(population, maze, Robot.TYPE.BLUE);
            ga.evalPopulation(population1, maze, Robot.TYPE.RED);
            generation++;

        }

        Individual fittestBlue = population.getFittest(0);
        Individual fittestRed = population1.getFittest(0);
        System.out.println("Best Blue solution(" + fittestBlue.getFitness() + "): " + fittestBlue.toString());
        System.out.println("Best Red  solution(" + fittestRed.getFitness() + "): " + fittestBlue.toString());
//        ga.showSolutionPath(fittest, maze);
        //get best robots
        Robot bestBlue = new Robot(fittestBlue.getChromosome(), maze, 100, Robot.TYPE.BLUE);
        Robot bestRed = new Robot(fittestRed.getChromosome(), maze, 100, Robot.TYPE.RED);
        //go for challenge
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new MyGdxGame(bestBlue, bestRed, maze), config);

    }

}