/*
Implement Graph data structure in Java. Use hashmap (HashMap<String,HashMap<<String, Integer>>>) 
to model the relationship between graph and its vertices/edges.
Note: For the basic problem, we assume that every path has a fixed length and is a
two-way path. Therefore, we will use an undirected and weighted graph.
*/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class Graph {
    HashMap<String, HashMap<String, Integer>> graph;

    public Graph() {
        graph = new HashMap<String, HashMap<String, Integer>>();
    }

    public void addVertex(String vertex) {
        if (!graph.containsKey(vertex)) {
            graph.put(vertex, new HashMap<String, Integer>());
        }
    }

    public void addEdge(String vertex1, String vertex2, Integer edge) {
        if (!graph.containsKey(vertex1)) {
            addVertex(vertex1);
        }
        if (!graph.containsKey(vertex2)) {
            addVertex(vertex2);
        }
        graph.get(vertex1).put(vertex2, edge);
        graph.get(vertex2).put(vertex1, edge);
    }

    public void removeVertex(String vertex) {
        if (graph.containsKey(vertex)) {
            for (String key : graph.get(vertex).keySet()) {
                graph.get(key).remove(vertex);
            }
            graph.remove(vertex);
        }
    }

    public void removeEdge(String vertex1, String vertex2) {
        if (graph.containsKey(vertex1) && graph.containsKey(vertex2)) {
            graph.get(vertex1).remove(vertex2);
            graph.get(vertex2).remove(vertex1);
        }
    }

    public boolean hasVertex(String vertex) {
        return graph.containsKey(vertex);
    }

    public boolean hasEdge(String vertex1, String vertex2) {
        return graph.containsKey(vertex1) && graph.get(vertex1).containsKey(vertex2);
    }

    public Integer getEdge(String vertex1, String vertex2) {
        if (graph.containsKey(vertex1) && graph.get(vertex1).containsKey(vertex2)) {
            return graph.get(vertex1).get(vertex2);
        }
        return null;
    }

    public int getVertexCount() {
        return graph.size();
    }

    public int getEdgeCount() {
        int count = 0;
        for (String key : graph.keySet()) {
            count += graph.get(key).size();
        }
        return count / 2;
    }

    public void printGraph() {
        for (String key : graph.keySet()) {
            System.out.print(key + ": ");
            for (String key2 : graph.get(key).keySet()) {
                System.out.print(key2 + " ");
            }
            System.out.println();
        }
    }

    // Using BFS/DFS to find the number of paths between two vertices
    public int findPath(String vertex1, String vertex2) {
        if (!graph.containsKey(vertex1) || !graph.containsKey(vertex2)) {
            return 0;
        }
        int count = 0;
        HashMap<String, Boolean> visited = new HashMap<>();
        for (String key : graph.keySet()) {
            visited.put(key, false);
        }
        count = findPathHelper(vertex1, vertex2, visited, count);
        return count;
    }

    private int findPathHelper(String vertex1, String vertex2, HashMap<String, Boolean> visited, int count) {
        visited.put(vertex1, true);
        if (vertex1.equals(vertex2)) {
            count++;
        } else {
            for (String key : graph.get(vertex1).keySet()) {
                if (!visited.get(key)) {
                    count = findPathHelper(key, vertex2, visited, count);
                }
            }
        }
        visited.put(vertex1, false);
        return count;
    }

    // Print out paths with largest and smallest number of vertices
    public void findPaths(String start, String end) {
        Queue<List<String>> queue = new LinkedList<>();
        int min = 26;
        int max = 0;

        List<String> path = new ArrayList<>();
        path.add(start);
        queue.offer(path);

        while (!queue.isEmpty()) {
            path = queue.poll();
            String last = path.get(path.size() - 1);
            if (last == end) {
                // check if the path is the shortest
                if (path.size() < min) {
                    min = path.size();
                }
                // check if the path is the longest
                if (path.size() > max) {
                    max = path.size();
                }
            }

            for (String key : graph.get(last).keySet()) {
                if (isNotVisited(key, path)) {
                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(key);
                    queue.offer(newPath);
                }
            }
        }

        path.clear();
        path.add(start);
        queue.clear();
        queue.offer(path);

        while (!queue.isEmpty()) {
            path = queue.poll();
            String last = path.get(path.size() - 1);
            if (last == end) {
                // check if the path is the shortest
                if (path.size() == min) {
                    printPath(path);
                    // calculate the cost
                    int cost = 0;
                    for (int i = 0; i < path.size() - 1; i++) {
                        cost += (int) graph.get(path.get(i)).get(path.get(i + 1));
                    }
                    System.out.println("Cost: " + cost);
                } else if (path.size() == max) {
                    printPath(path);
                    int cost = 0;
                    for (int i = 0; i < path.size() - 1; i++) {
                        cost += (int) graph.get(path.get(i)).get(path.get(i + 1));
                    }
                    System.out.println("Cost: " + cost);
                }
            }

            for (String key : graph.get(last).keySet()) {
                if (isNotVisited(key, path)) {
                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(key);
                    queue.offer(newPath);
                }
            }
        }
    }

    public void printPath(List<String> path) {
        for (String key : path) {
            System.out.print(key + " ");
        }
        System.out.println();
    }

    public boolean isNotVisited(String x, List<String> path) {
        for (String key : path) {
            if (key == x) {
                return false;
            }
        }
        return true;
    }

    // Apply Dijkstra's algorithm to find the shortest path
    public void dijkstra(String start, String end) {
        HashMap<String, Integer> distance = new HashMap<>();
        HashMap<String, String> parent = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return distance.get(o1) - distance.get(o2);
            }
        });

        for (String key : graph.keySet()) {
            distance.put(key, Integer.MAX_VALUE);
            parent.put(key, null);
        }

        distance.put(start, 0);
        pq.offer(start);

        while (!pq.isEmpty()) {
            String curr = pq.poll();
            for (String key : graph.get(curr).keySet()) {
                int newDistance = distance.get(curr) + (int) graph.get(curr).get(key);
                if (newDistance < distance.get(key)) {
                    distance.put(key, newDistance);
                    parent.put(key, curr);
                    pq.offer(key);
                }
            }
        }

        Stack<String> stack = new Stack<>();
        String curr = end;
        while (curr != null) {
            stack.push(curr);
            curr = parent.get(curr);
        }

        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
        System.out.println();

        System.out.println("Cost: " + distance.get(end));
    }

    // If a node has more than 2 connected nodes, the traffic will be heavier at that node.
    // So, the cost to go from other nodes should be increased by (x-2)*2 where x is the number of connected nodes.
    // Find the fastest path from two given nodes with the new cost.
    public void findFastestPath(String start, String end) {
        HashMap<String, Integer> distance = new HashMap<>();
        HashMap<String, String> parent = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return distance.get(o1) - distance.get(o2);
            }
        });

        for (String key : graph.keySet()) {
            distance.put(key, Integer.MAX_VALUE);
            parent.put(key, null);
        }

        distance.put(start, 0);
        pq.offer(start);

        while (!pq.isEmpty()) {
            String curr = pq.poll();
            for (String key : graph.get(curr).keySet()) {
                int newDistance = distance.get(curr) + (int) graph.get(curr).get(key);
                if (graph.get(curr).size() > 2) {
                    newDistance += (graph.get(curr).size() - 2) * 2;
                }
                if (newDistance < distance.get(key)) {
                    distance.put(key, newDistance);
                    parent.put(key, curr);
                    pq.offer(key);
                }
            }
        }

        Stack<String> stack = new Stack<>();
        String curr = end;
        while (curr != null) {
            stack.push(curr);
            curr = parent.get(curr);
        }

        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
        System.out.println();

        System.out.println("Cost: " + distance.get(end));
    }

    // Implement a function that helps randomly generate a graph from an input number of nodes.
    // You may set your constraint on the minimum and maximum number of connections for each node.
    // Define nodes as alphabets.
    public void generateGraph(int n, int min, int max) {
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            char c = (char) ('A' + i);
            graph.put(String.valueOf(c), new HashMap<>());
        }

        for (int i = 0; i < n; i++) {
            char c = (char) ('A' + i);
            int num = rand.nextInt(max - min + 1) + min;
            for (int j = 0; j < num; j++) {
                int index = rand.nextInt(n);
                char c2 = (char) ('A' + index);
                if (c != c2) {
                    graph.get(String.valueOf(c)).put(String.valueOf(c2), rand.nextInt(10) + 1);
                }
            }
        }
    }
}
