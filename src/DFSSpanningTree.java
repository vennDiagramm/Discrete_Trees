import java.util.*;

public class DFSSpanningTree {

    public static void main(String[] args) {
        // Graph represented as an adjacency list
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("A", Arrays.asList("B", "C"));
        graph.put("B", Arrays.asList("A", "D", "E"));
        graph.put("C", Arrays.asList("A", "F"));
        graph.put("D", Arrays.asList("B"));
        graph.put("E", Arrays.asList("B", "F"));
        graph.put("F", Arrays.asList("C", "E"));

        // Start DFS from vertex 'A'
        List<String[]> spanningTree = dfs(graph, "A");

        // Print the DFS spanning tree
        System.out.println("DFS Spanning Tree:");
        for (String[] edge : spanningTree) {
            System.out.println(Arrays.toString(edge));
        }
    }

    public static List<String[]> dfs(Map<String, List<String>> graph, String start) {
        List<String[]> spanningTree = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        dfsUtil(graph, start, visited, spanningTree);
        return spanningTree;
    }

    private static void dfsUtil(Map<String, List<String>> graph, String current, Set<String> visited, List<String[]> spanningTree) {
        visited.add(current);
        for (String neighbor : graph.getOrDefault(current, Collections.emptyList())) {
            if (!visited.contains(neighbor)) {
                spanningTree.add(new String[] {current, neighbor});
                dfsUtil(graph, neighbor, visited, spanningTree);
            }
        }
    }
}