import java.util.*;

class PrimsAlgo {
    private int vertices;
    private LinkedList<Edge>[] adjacencyList;

    class Edge {
        int dest;
        int weight;

        Edge(int dest, int weight) {
            this.dest = dest;
            this.weight = weight;
        }
    }

    PrimsAlgo(int vertices) {
        this.vertices = vertices;
        adjacencyList = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
    }

    void addEdge(int src, int dest, int weight) {
        adjacencyList[src].add(new Edge(dest, weight));
        adjacencyList[dest].add(new Edge(src, weight));
    }

    void primMST() {
        boolean[] inMST = new boolean[vertices];
        Edge[] edgeTo = new Edge[vertices];
        int[] key = new int[vertices];
        PriorityQueue<Pair> pq = new PriorityQueue<>(vertices, Comparator.comparingInt(o -> o.key));

        Arrays.fill(key, Integer.MAX_VALUE);
        key[0] = 0;
        pq.add(new Pair(0, key[0]));

        while (!pq.isEmpty()) {
            int u = pq.poll().vertex;
            inMST[u] = true;

            for (Edge edge : adjacencyList[u]) {
                int v = edge.dest;
                int weight = edge.weight;

                if (!inMST[v] && weight < key[v]) {
                    key[v] = weight;
                    edgeTo[v] = new Edge(u, weight);
                    pq.add(new Pair(v, key[v]));
                }
            }
        }

        printMST(edgeTo);
    }

    void printMST(Edge[] edgeTo) {
        int totalWeight = 0;
        System.out.println("Edge \tWeight");
        for (int i = 1; i < vertices; i++) {
            System.out.println(edgeTo[i].dest + " - " + i + "\t" + edgeTo[i].weight);
            totalWeight += edgeTo[i].weight;
        }
        System.out.println("Total weight of MST: " + totalWeight);
    }

    class Pair {
        int vertex;
        int key;

        Pair(int vertex, int key) {
            this.vertex = vertex;
            this.key = key;
        }
    }

    public static void main(String[] args) {
        int vertices = 5;
        PrimsAlgo graph = new PrimsAlgo(vertices);

        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 3, 6);
        graph.addEdge(1, 2, 3);
        graph.addEdge(1, 3, 8);
        graph.addEdge(1, 4, 5);
        graph.addEdge(2, 4, 7);
        graph.addEdge(3, 4, 9);

        graph.primMST();
    }
}