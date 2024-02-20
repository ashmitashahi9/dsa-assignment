// a) Implement ant colony algorithm solving travelling a salesman problem
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Ant {
    private static final double ALPHA = 1.0; // Pheromone importance
    private static final double BETA = 2.0;  // Heuristic importance
    private static final double EVAPORATION = 0.5; // Pheromone evaporation rate

    private int numCities;
    private int[][] distances;
    private double[][] pheromones;
    private List<Integer> tour;
    private boolean[] visited;

    public Ant(int numCities, int[][] distances) {
        this.numCities = numCities;
        this.distances = distances;
        this.pheromones = new double[numCities][numCities];
        this.tour = new ArrayList<>();
        this.visited = new boolean[numCities];
    }

    public void findTour() {
        tour.clear();
        for (int i = 0; i < numCities; i++) {
            visited[i] = false;
        }

        Random random = new Random();
        int startCity = random.nextInt(numCities);
        tour.add(startCity);
        visited[startCity] = true;

        while (tour.size() < numCities) {
            int currentCity = tour.get(tour.size() - 1);
            int nextCity = selectNextCity(currentCity);
            tour.add(nextCity);
            visited[nextCity] = true;
        }

        tour.add(tour.get(0)); // Return to starting city
    }

    private int selectNextCity(int currentCity) {
        double[] probabilities = new double[numCities];
        double totalProbability = 0.0;

        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                double pheromone = Math.pow(pheromones[currentCity][i], ALPHA);
                double distance = 1.0 / distances[currentCity][i]; // Inverse of distance
                double probability = pheromone * Math.pow(distance, BETA);
                probabilities[i] = probability;
                totalProbability += probability;
            }
        }

        double randomValue = totalProbability * Math.random();
        double cumulativeProbability = 0.0;

        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                cumulativeProbability += probabilities[i];
                if (cumulativeProbability >= randomValue) {
                    return i;
                }
            }
        }

        return -1; // Should never happen
    }

    public void updatePheromones(double[][] deltaPheromones) {
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                pheromones[i][j] = (1 - EVAPORATION) * pheromones[i][j] + deltaPheromones[i][j];
            }
        }
    }

    public List<Integer> getTour() {
        return tour;
    }
}

public class question5a {
    private static final int NUM_ANTS = 10;
    private static final int MAX_ITERATIONS = 1000;
    private static final double INITIAL_PHEROMONE = 0.1;
    private static final double Q = 1.0; // Pheromone deposit

    private int numCities;
    private int[][] distances;
    private double[][] pheromones;
    private List<Ant> ants;

    public question5a(int numCities, int[][] distances) {
        this.numCities = numCities;
        this.distances = distances;
        this.pheromones = new double[numCities][numCities];
        this.ants = new ArrayList<>();

        initializePheromones();
        initializeAnts();
    }

    private void initializePheromones() {
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                pheromones[i][j] = INITIAL_PHEROMONE;
            }
        }
    }

    private void initializeAnts() {
        for (int i = 0; i < NUM_ANTS; i++) {
            ants.add(new Ant(numCities, distances));
        }
    }

    public List<Integer> solve() {
        List<Integer> bestTour = null;
        int bestTourLength = Integer.MAX_VALUE;

        for (int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {
            for (Ant ant : ants) {
                ant.findTour();
                List<Integer> tour = ant.getTour();
                int tourLength = calculateTourLength(tour);

                if (tourLength < bestTourLength) {
                    bestTourLength = tourLength;
                    bestTour = new ArrayList<>(tour);
                }

                double[][] deltaPheromones = calculateDeltaPheromones(ant, tourLength);
                ant.updatePheromones(deltaPheromones);
            }
        }

        return bestTour;
    }

    private int calculateTourLength(List<Integer> tour) {
        int tourLength = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            int city1 = tour.get(i);
            int city2 = tour.get(i + 1);
            tourLength += distances[city1][city2];
        }
        return tourLength;
    }

    private double[][] calculateDeltaPheromones(Ant ant, int tourLength) {
        double[][] deltaPheromones = new double[numCities][numCities];
        List<Integer> tour = ant.getTour();

        for (int i = 0; i < tour.size() - 1; i++) {
            int city1 = tour.get(i);
            int city2 = tour.get(i + 1);
            deltaPheromones[city1][city2] += Q / tourLength;
            deltaPheromones[city2][city1] += Q / tourLength; // Symmetric matrix
        }

        return deltaPheromones;
    }

    public static void main(String[] args) {
        int numCities = 5;
        int[][] distances = {
            {0, 2, 9, 10, 7},
            {2, 0, 6, 8, 3},
            {9, 6, 0, 3, 4},
            {10, 8, 3, 0, 6},
            {7, 3, 4, 6, 0}
        };

        question5a antColony = new question5a(numCities, distances);
        List<Integer> bestTour = antColony.solve();

        System.out.println("Best tour found: " + bestTour);
        System.out.println("Tour length: " + antColony.calculateTourLength(bestTour));
    }
}