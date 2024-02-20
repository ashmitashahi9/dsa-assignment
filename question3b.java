// Implement Kruskal algorithm and priority queue using minimum heap
import java.util.*;

class Edge implements Comparable<Edge> {
    int source, destination, weight;

    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

class Graph {
    private final List<Edge> edges;
    private final int vertices;

    public Graph(int vertices) {
        this.vertices = vertices;
        edges = new ArrayList<>();
    }

    public void addEdge(int source, int destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        edges.add(edge);
    }

    public List<Edge> kruskalMST() {
        List<Edge> result = new ArrayList<>();
        Collections.sort(edges);

        DisjointSet ds = new DisjointSet(vertices);

        for (Edge edge : edges) {
            int srcParent = ds.find(edge.source);
            int destParent = ds.find(edge.destination);

            if (srcParent != destParent) {
                result.add(edge);
                ds.union(srcParent, destParent);
            }
        }
        return result;
    }
}

class DisjointSet {
    private final int[] parent;

    public DisjointSet(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public void union(int x, int y) {
        int xSet = find(x);
        int ySet = find(y);
        parent[xSet] = ySet;
    }
}

class PriorityQueueMinHeap<T extends Comparable<T>> {
    private final List<T> heap;

    public PriorityQueueMinHeap() {
        heap = new ArrayList<>();
    }

    public void insert(T element) {
        heap.add(element);
        int current = heap.size() - 1;
        while (current > 0 && heap.get(current).compareTo(heap.get((current - 1) / 2)) < 0) {
            swap(current, (current - 1) / 2);
            current = (current - 1) / 2;
        }
    }

    public T extractMin() {
        if (heap.isEmpty()) return null;
        if (heap.size() == 1) return heap.remove(0);

        T min = heap.get(0);
        heap.set(0, heap.remove(heap.size() - 1));

        int current = 0;
        while (true) {
            int leftChild = 2 * current + 1;
            int rightChild = 2 * current + 2;
            int smallest = current;

            if (leftChild < heap.size() && heap.get(leftChild).compareTo(heap.get(smallest)) < 0)
                smallest = leftChild;
            if (rightChild < heap.size() && heap.get(rightChild).compareTo(heap.get(smallest)) < 0)
                smallest = rightChild;

            if (smallest == current) break;

            swap(current, smallest);
            current = smallest;
        }
        return min;
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}

public class question3b {
    public static void main(String[] args) {
        Graph graph = new Graph(6);
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 4);
        graph.addEdge(1, 2, 2);
        graph.addEdge(1, 0, 4);
        graph.addEdge(2, 0, 4);
        graph.addEdge(2, 1, 2);
        graph.addEdge(2, 3, 3);
        graph.addEdge(2, 5, 2);
        graph.addEdge(2, 4, 4);
        graph.addEdge(3, 2, 3);
        graph.addEdge(3, 4, 3);
        graph.addEdge(4, 2, 4);
        graph.addEdge(4, 3, 3);
        graph.addEdge(5, 2, 2);
        graph.addEdge(5, 4, 3);

        List<Edge> result = graph.kruskalMST();
        System.out.println("Edges in MST using Kruskal's algorithm:");
        for (Edge edge : result) {
            System.out.println(edge.source + " - " + edge.destination + ": " + edge.weight);
        }
    }
}
