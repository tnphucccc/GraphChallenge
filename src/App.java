public class App {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addEdge("A", "B", 11);
        graph.addEdge("A", "G", 3);
        graph.addEdge("B", "C", 11);
        graph.addEdge("B", "F", 5);
        graph.addEdge("C", "D", 3);
        graph.addEdge("D", "E", 6);
        graph.addEdge("D", "N", 6);
        graph.addEdge("E", "F", 9);
        graph.addEdge("E", "J", 3);
        graph.addEdge("F", "G", 6);
        graph.addEdge("G", "H", 3);
        graph.addEdge("G", "T", 3);
        graph.addEdge("H", "I", 5);
        graph.addEdge("H", "R", 8);
        graph.addEdge("H", "S", 7);
        graph.addEdge("I", "L", 3);
        graph.addEdge("J", "K", 5);
        graph.addEdge("J", "M", 3);
        graph.addEdge("K", "N", 4);
        graph.addEdge("L", "Q", 5);
        graph.addEdge("L", "R", 3);
        graph.addEdge("M", "N", 7);
        graph.addEdge("M", "Q", 3);
        graph.addEdge("N", "O", 3);
        graph.addEdge("O", "P", 3);
        graph.addEdge("O", "W", 1);
        graph.addEdge("P", "Q", 3);
        graph.addEdge("P", "Z", 3);
        graph.addEdge("Q", "Y", 3);
        graph.addEdge("R", "S", 3);
        graph.addEdge("R", "X", 3);
        graph.addEdge("S", "T", 3);
        graph.addEdge("S", "V", 1);
        graph.addEdge("T", "U", 1);
        graph.addEdge("U", "V", 3);
        graph.addEdge("V", "X", 1);
        graph.addEdge("W", "Z", 3);
        graph.addEdge("X", "Y", 3);
        graph.addEdge("Y", "Z", 3);
        
        // Task 3
        graph.printGraph();
        System.out.println();

        // Task 4
        System.out.println("The number of paths between A to Z is " + graph.findPath("A", "Z"));
        System.out.println();
        System.out.println("Paths with the smallest and largest number of nodes and its cost:");
        graph.findPaths("A", "Z");
        System.out.println();

        // Task 5
        System.out.println("The shortest path between A to W is:");
        graph.dijkstra("A", "W");
        System.out.println("The shortest path between C to U is:");
        graph.dijkstra("C", "U");
        System.out.println();

        // Task 6
        System.out.println("The fastest path between A to W is:");
        graph.findFastestPath("A", "W");
        System.out.println("The fastest path between C to U is:");
        graph.findFastestPath("C", "U");
        System.out.println();

        // // Task 7
        // graph.generateGraph(8, 2, 5);
        // graph.printGraph();
        // System.out.println();
        // System.out.println("The number of paths between A to G is " + graph.findPath("A", "G"));
        // System.out.println();
        // System.out.println("Paths with the smallest and largest number of nodes and its cost:");
        // graph.findPaths("A", "G");
        // System.out.println();
        // System.out.println("The shortest path between A to G is:");
        // graph.dijkstra("A", "G");
        // System.out.println("The shortest path between C to H is:");
        // graph.dijkstra("C", "H");
        // System.out.println();
        // System.out.println("The fastest path between A to G is:");
        // graph.findFastestPath("A", "G");
        // System.out.println("The fastest path between C to H is:");
        // graph.findFastestPath("C", "H");
        // System.out.println();
    }
}
