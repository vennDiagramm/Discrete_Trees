import java.util.*;

public class BFSSpanningTree {

    public static void main(String[] args) {
        // Graph represented as an adjacency list
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("A", Arrays.asList("B", "C"));
        graph.put("B", Arrays.asList("A", "D", "E"));
        graph.put("C", Arrays.asList("A", "F"));
        graph.put("D", Arrays.asList("B"));
        graph.put("E", Arrays.asList("B", "F"));
        graph.put("F", Arrays.asList("C", "E"));

        // Start BFS from vertex 'A'
        List<String[]> spanningTree = bfs(graph, "A");

        // Print the BFS spanning tree
        System.out.println("BFS Spanning Tree:");
        for (String[] edge : spanningTree) {
            System.out.println(Arrays.toString(edge));
        }
    }

    public static List<String[]> bfs(Map<String, List<String>> graph, String start) {
        List<String[]> spanningTree = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String neighbor : graph.getOrDefault(current, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    spanningTree.add(new String[] {current, neighbor});
                }
            }
        }

        return spanningTree;
    }
}