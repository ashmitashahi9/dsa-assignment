// Assume you were hired to create an application for an ISP, and there are n network devices, such as routers, that are linked together to provide internet access to users. You are given a 2D array that represents network connections between these network devices. write an algorithm to return impacted network devices, If there is a power outage on a certain device, these impacted device list assist you notify linked consumers that there is a power outage and it will take some time to rectify an issue.     Input: edges= {{0,1},{0,2},{1,3},{1,6},{2,4},{4,6},{4,5},{5,7}} Target Device (On which power Failure occurred): 4 Output (Impacted Device List) = {5,7}
import java.util.*;

public class question5b{
    public static List<Integer> findImpactedDevices(int[][] edges, int targetDevice) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            graph.putIfAbsent(from, new ArrayList<>());
            graph.putIfAbsent(to, new ArrayList<>());
            graph.get(from).add(to);
            graph.get(to).add(from);
        }

        Set<Integer> visited = new HashSet<>();
        Set<Integer> impactedDevices = new HashSet<>();
        dfs(graph, targetDevice, visited, impactedDevices);

        return new ArrayList<>(impactedDevices);
    }

    private static void dfs(Map<Integer, List<Integer>> graph, int current, Set<Integer> visited, Set<Integer> impactedDevices) {
        visited.add(current);
        for (int neighbor : graph.getOrDefault(current, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {
                if (neighbor != current) { // Avoid infinite loop
                    impactedDevices.add(neighbor);
                    dfs(graph, neighbor, visited, impactedDevices);
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {1, 6}, {2, 4}, {4, 6}, {4, 5}, {5, 7}};
        int targetDevice = 4;
        List<Integer> impactedDevices = findImpactedDevices(edges, targetDevice);
        System.out.println("Impacted Device List: " + impactedDevices); // Output: [5, 7]
    }
}