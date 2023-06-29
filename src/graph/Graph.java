package graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Menu;
import util.Tool;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author PHAMKHANG
 */
public final class Graph {

    public static Graph INSTANCE = new Graph("graphdata.txt");
    char[] vertices;
    int[] index;
    int[][] adjacency_matrix;
    int[][] incident_matrix;
    Set<Edge> edges = new HashSet<>();
    List<List<Relative>> adjacency_list;

    public Graph() {

    }

    ;
    
    public Graph(String filepath) {
        try {
            load_data(filepath);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        }
        index = new int[vertices.length];
        getEdgeList();
        getIncidentMatrix();
        getAdjacentList();
    }

    public static Graph getINSTANCE() {
        return INSTANCE;
    }

    //load and write data
    public boolean load_data(String filepath) throws FileNotFoundException {
        int count = 0;
        try {
            File f = new File(filepath);
            if (!f.exists()) {
                return false;
            }
            try (FileReader fr = new FileReader(f)) {
                try (BufferedReader br = new BufferedReader(fr)) {
                    vertices = toVertices(br.readLine().trim());
                    adjacency_matrix = new int[vertices.length][vertices.length];
                    String info;
                    while ((info = br.readLine()) != null) {
                        adjacency_matrix[count++] = toAdjancent(info.trim());
                    }
                    br.close();
                }
                fr.close();
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println(e);
        }
        return true;
    }

    public boolean write_data(String filepath) {
        if (adjacency_matrix == null) {
            System.out.println("Empty list");
            return false;
        }
        try {
            File f = new File(filepath);
            try (FileWriter fw = new FileWriter(f); PrintWriter pw = new PrintWriter(fw)) {
                for (int i = 0; i < vertices.length; i++) {
                    pw.print(vertices[i] + " ");
                }
                pw.println();

                for (int i = 0; i < adjacency_matrix.length; i++) {
                    for (int j = 0; j < adjacency_matrix.length; j++) {
                        pw.print(adjacency_matrix[i][j] + " ");

                    }
                    pw.println();
                }

            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return true;
    }

    //Console
    public void printVertices() {
        if (edges != null && vertices != null) {
            for (int i = 0; i < vertices.length; i++) {
                System.out.print(vertices[i] + " ");
            }
        } else {
            System.out.println("Data are failed in loading");
        }
    }

    public void printAdjacentMatrix() {
        if (edges != null && vertices.length != 0) {
            System.out.print("  ");
            printVertices();
            System.out.println();
            for (int i = 0; i < adjacency_matrix.length; i++) {
                System.out.print(toDestination(i) + " ");
                for (int j = 0; j < adjacency_matrix.length; j++) {
                    System.out.print(adjacency_matrix[i][j] + " ");
                }
                System.out.println();
            }
        } else {
            System.out.println("Data are failed in loading");
        }
        System.out.println();
    }

    public void printEdges() {
        if (edges != null && vertices != null) {
            for (Edge x : edges) {
                System.out.println(x.toString());
            }
        } else {
            System.out.println("Data are failed in loading");
        }
        System.out.println();
    }

    public void printIncidentMatrix() {
        if (edges != null && vertices != null) {
            System.out.print("  ");
            for (Edge x : edges) {
                System.out.print(x.getStart() + "" + x.getEnd() + " ");
            }
            System.out.println("");
            for (int i = 0; i < incident_matrix.length; i++) {
                System.out.print(vertices[i] + " ");
                for (int j = 0; j < incident_matrix[i].length; j++) {
                    System.out.print(" " + incident_matrix[i][j] + " ");
                }
                System.out.println("");
            }
        } else {
            System.out.println("Data are failed in loading");
        }
        System.out.println();
    }

    public void printAdjcencyList() {
        for (int i = 0; i < adjacency_list.size(); i++) {
            System.out.print(toDestination(i) + ":");
            for (int j = 0; j < adjacency_list.get(i).size(); j++) {
                System.out.print(adjacency_list.get(i).get(j).toString());
                if (j < adjacency_list.get(i).size() - 1) {
                    System.out.print(",");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printSolution(int dist[][]) {
        System.out.println(
                "The following matrix shows the shortest "
                + "distances between every pair of vertices");
        System.out.print("  ");
        printVertices();
        System.out.println();
        for (int i = 0; i < vertices.length; ++i) {
            System.out.print(vertices[i] + " ");
            for (int j = 0; j < vertices.length; ++j) {
                if (dist[i][j] == 99) {
                    System.out.print("INF ");
                } else {
                    System.out.print(dist[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void dfs() {
        char a = Tool.inputNonblankString("Enter a vertex").toUpperCase().toCharArray()[0];
        if (toindex(a) != -1) {
            dfs(a);
        } else {
            System.out.println("Input vertex doesn't exist!");
        }
        System.out.println();
    }

    public void bfs() {
        char a = Tool.inputNonblankString("Enter a vertex").toUpperCase().toCharArray()[0];
        if (toindex(a) != -1) {
            bfs(a);
        } else {
            System.out.println("Input vertex doesn't exist!");
        }
        System.out.println();

    }

    public void compute_degree() {
        char a = Tool.inputNonblankString("Enter a destination").toUpperCase().toCharArray()[0];
        System.out.println("Degree of vertex " + a + " is " + compute_degree(a));
    }

    public void dijkstra() {
        char u = Tool.inputNonblankString("Enter starting destination").toUpperCase().toCharArray()[0];
        char v = Tool.inputNonblankString("Enter ending destination").toUpperCase().toCharArray()[0];
        if (toindex(u) != -1 && toindex(v) != -1) {
            ijk(toindex(u), toindex(v));
        } else {
            System.out.println("Input destinations are invalid");
        }
        System.out.println();
    }

    public void prim() {
        primMST(adjacency_matrix);
        System.out.println();
    }

    public void kruskal() {
        System.out.println("Kruskal implementation:");
        kruskalMST(toINFMatrix(adjacency_matrix));
        System.out.println();
    }

    public void floyd() {
        floydWarshall(toINFMatrix(adjacency_matrix));
    }

    public void euler() {
        findpath(toAdjencyMatrix(adjacency_matrix), 5);
    }

    //Algorithms's implementation
    public void getEdgeList() {
        if (edges != null && vertices != null) {
            for (int i = 0; i < adjacency_matrix.length; i++) {
                for (int j = i + 1; j < adjacency_matrix.length; j++) {
                    if (adjacency_matrix[i][j] != 0) {
                        edges.add(new Edge(toDestination((i > j) ? j : i), toDestination((i > j) ? i : j), adjacency_matrix[i][j]));
                    }
                }
            }
        }
    }

    public void getIncidentMatrix() {
        if (edges != null && vertices != null) {
            incident_matrix = new int[vertices.length][edges.size()];
            int currentEdge;
            for (int i = 0; i < vertices.length; i++) {
                currentEdge = 0;
                for (Edge x : edges) {
                    if (x.getStart() == toDestination(i) || x.getEnd() == toDestination(i)) {
                        incident_matrix[i][currentEdge++] = x.getDistance();
                    } else {
                        incident_matrix[i][currentEdge++] = 0;
                    }
                }
            }
        }
    }

    public void getAdjacentList() {
        adjacency_list = new ArrayList<>();
        List<Relative> list;
        for (int i = 0; i < adjacency_matrix.length; i++) {
            list = new ArrayList<>();
            for (int j = 0; j < adjacency_matrix.length; j++) {
                if (adjacency_matrix[i][j] != 0) {
                    list.add(new Relative(toDestination(j), adjacency_matrix[i][j]));
                }
            }
            adjacency_list.add(list);
        }
    }

    public void dfs(char a) {
        Set<Character> set = new HashSet<>();
        set.add(a);
        System.out.println("DepthFirst from " + a + ": ");
        System.out.print(a + " ");
        depthFirst(set, a);
    }

    public void bfs(char a) {
        Set<Character> set = new HashSet<>();
        set.add(a);
        System.out.println("BreadthFirst from " + a + ": ");
        System.out.print(a + " ");
        breadthFirst(set, a);
    }

    public void depthFirst(Set<Character> set, char v) {
        if (adjacency_list.get(toindex(v)) != null) {
            for (Relative x : adjacency_list.get(toindex(v))) {
                boolean added = set.add(x.getId());
                if (added) {
                    System.out.print(x.getId() + " ");
                    depthFirst(set, x.getId());
                }
            }
        }
    }

    public void breadthFirst(Set<Character> set, char v) {
        if (adjacency_list.get(toindex(v)) != null) {
            Set<Character> subSet = new HashSet<>();
            for (Relative x : adjacency_list.get(toindex(v))) {
                boolean added = set.add(x.getId());
                if (added) {
                    System.out.print(x.getId() + " ");
                    subSet.add(x.getId());
                }
            }
            for (Relative x : adjacency_list.get(toindex(v))) {
                if (subSet.contains(x.getId())) {
                    breadthFirst(set, x.getId());
                }
            }
        }
    }

    public int compute_degree(char a) {
        return adjacency_list.get(toindex(a)).size();
    }

    public void ijk(int u, int v) {
        int[][] disMatrix = toINFMatrix(adjacency_matrix);
        boolean[] c = new boolean[vertices.length];
        int[] s = new int[vertices.length];
        int[] d = new int[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            s[i] = u;
            d[i] = disMatrix[u][i];
        }
        int curr = u;
        while (curr != v) {
            int k = -1, min = 99;
            //Look for the nearest neighbor vertex
            for (int i = 0; i < vertices.length; i++) {
                if (d[i] < min && !c[i]) {
                    min = d[i];
                    k = i;
                }
            }

            if (min == 99) {
                System.out.println("Shortest path from " + toDestination(u) + " to " + toDestination(v) + " not found");
                return;
            }

            curr = k;
            c[k] = true;
            for (int i = 0; i < vertices.length; i++) {
                if (d[i] > d[k] + disMatrix[k][i]) {
                    d[i] = d[k] + disMatrix[k][i];
                    s[i] = k;
                }
            }
        }
        System.out.println("Shortest path from " + toDestination(u) + " to " + toDestination(v) + " is " + d[v]);
        int[] h = new int[vertices.length];
        int hn = 0;
        h[hn] = v;
        int[] w = new int[vertices.length];
        int wn = 0;
        int x, y = v;
        while (u != v) {
            v = s[v];
            h[++hn] = v;
        }
        for (int i = hn; i >= 0; i--) {
            x = y;
            y = h[i];
            w[wn] = disMatrix[x][y];
            wn++;
        }
        int k = 1;
        System.out.println("Detail path with distance of one vertex to the following one:");
        System.out.print(vertices[h[hn]]);
        for (int i = hn - 1; i >= 0; i--) {
            System.out.print("->" + vertices[h[i]] + "(" + w[k++] + ")");
        }
        System.out.println("");
        System.out.println("Detail path distance of " + toDestination(u) + " to each vertex in its path:");
        System.out.print(vertices[h[hn]]);
        for (int i = hn - 1; i >= 0; i--) {
            System.out.print("->" + vertices[h[i]] + "(" + d[h[i]] + ")");
        }
    }

    public int find(int i) {
        while (index[i] != i) {
            i = index[i];
        }
        return i;
    }

    public void union1(int i, int j) {
        int a = find(i);
        int b = find(j);
        index[a] = b;
    }

    public void kruskalMST(int cost[][]) {
        int mincost = 0;
        for (int i = 0; i < vertices.length; i++) {
            index[i] = i;
        }
        int edge_count = 0;
        while (edge_count < vertices.length - 1) {
            int min = 99, a = -1, b = -1;
            for (int i = 0; i < vertices.length; i++) {
                for (int j = 0; j < vertices.length; j++) {
                    if (find(i) != find(j) && cost[i][j] < min) {
                        min = cost[i][j];
                        a = i;
                        b = j;
                    }
                }
            }

            union1(a, b);
            System.out.printf("Edge %d:(%s, %s) cost:%d \n",
                    edge_count++, toDestination(a), toDestination(b), min);
            mincost += min;
        }
        System.out.printf("Minimum cost= %d \n", mincost);
    }

    public void findpath(int[][] graph, int n) {
        System.out.println("Eucler implemetation: ");
        Vector<Integer> numofadj = new Vector<>();
        for (int i = 0; i < n; i++) {
            numofadj.add(accumulate(graph[i], 0));
        }
        int startPoint = 0, numofodd = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (numofadj.elementAt(i) % 2 == 1) {
                numofodd++;
                startPoint = i;
            }
        }

        if (numofodd > 2) {
            System.out.println("No Solution");
            return;
        }

        Stack<Integer> stack = new Stack<>();
        Vector<Integer> path = new Vector<>();
        int cur = startPoint;
        while (!stack.isEmpty() || accumulate(graph[cur], 0) != 0) {
            if (accumulate(graph[cur], 0) == 0) {
                path.add(cur);
                cur = stack.pop();
            } else {
                for (int i = 0; i < n; i++) {
                    if (graph[cur][i] != 0) {
                        stack.add(cur);
                        graph[cur][i] = 0;
                        graph[i][cur] = 0;
                        cur = i;
                        break;
                    }
                }
            }
        }

        for (int ele : path) {
            System.out.print(toDestination(ele) + " -> ");
        }
        System.out.println(toDestination(cur));
    }

    public int accumulate(int[] arr, int sum) {
        for (int i : arr) {
            sum += i;
        }
        return sum;
    }

    public int minKey(int key[], Boolean mstSet[]) {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < vertices.length; v++) {
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }
        }

        return min_index;
    }

    public void printMST(int parent[], int graph[][]) {
        System.out.println("Mininum spanning tree:");
        System.out.println("Edge  Weight");
        for (int i = 1; i < vertices.length; i++) {
            System.out.println(toDestination(parent[i]) + "" + toDestination(i) + "    " + graph[i][parent[i]]);
        }
    }

    public void primMST(int graph[][]) {
        int parent[] = new int[vertices.length];
        int key[] = new int[vertices.length];
        Boolean mstSet[] = new Boolean[vertices.length];

        for (int i = 0; i < vertices.length; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        key[0] = 0;
        parent[0] = -1;
        for (int count = 0; count < vertices.length - 1; count++) {
            int u = minKey(key, mstSet);
            mstSet[u] = true;
            for (int v = 0; v < vertices.length; v++) {
                if (graph[u][v] != 0 && mstSet[v] == false
                        && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }
        printMST(parent, graph);
    }

    public void floydWarshall(int graph[][]) {
        int dist[][] = new int[vertices.length][vertices.length];
        int i, j, k;
        for (i = 0; i < vertices.length; i++) {
            for (j = 0; j < vertices.length; j++) {
                dist[i][j] = graph[i][j];
            }
        }
        for (k = 0; k < vertices.length; k++) {
            for (i = 0; i < vertices.length; i++) {
                for (j = 0; j < vertices.length; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
        printSolution(dist);
    }

    //Converters
    public int[][] toINFMatrix(int a[][]) {
        int[][] disMatrix = new int[adjacency_matrix.length][adjacency_matrix.length];
        for (int i = 0; i < adjacency_matrix.length; i++) {
            for (int j = 0; j < adjacency_matrix[i].length; j++) {
                if (i != j && adjacency_matrix[i][j] == 0) {
                    disMatrix[i][j] = 99;
                } else {
                    disMatrix[i][j] = adjacency_matrix[i][j];
                }
            }
        }
        return disMatrix;
    }

    public int[][] toAdjencyMatrix(int[][] b) {
        int a[][] = new int[vertices.length][vertices.length];
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (b[i][j] != 0) {
                    a[i][j] = 1;
                } else {
                    a[i][j] = 0;
                }
            }
        }
        return a;
    }

    public char toDestination(int index) {
        for (int i = 0; i < vertices.length; i++) {
            if (index == i) {
                return vertices[i];
            }
        }
        return ' ';
    }

    public int toindex(char destination) {
        for (int i = 0; i < vertices.length; i++) {
            if (destination == vertices[i]) {
                return i;
            }
        }
        return -1;
    }

    public char[] toVertices(String verticeString) {
        char[] vertices = new char[verticeString.length() / 2 + 1];
        for (int i = 0; i < verticeString.length(); i++) {
            if (i % 2 == 0) {
                vertices[i / 2] = verticeString.charAt(i);
            }
        }
        return vertices;
    }

    public int[] toAdjancent(String distanceString) {
        int[] distances = new int[distanceString.length() / 2 + 1];
        String[] adj = distanceString.split(" ");
        for (int i = 0; i < adj.length; i++) {
            distances[i] = Integer.parseInt(adj[i]);
        }
        return distances;
    }

    public void function() {
        int choice, subChoice;
        do {
            choice = Menu.int_getChoice(Menu.graphMenu);
            switch (choice) {
                case 1:
                    subChoice = Menu.int_getChoice(Menu.graphPrintNmnu);
                    switch (subChoice) {
                        case 1:
                            dfs();
                            break;
                        default:
                            bfs();
                            break;
                    }
                    break;
                case 2:
                    compute_degree();
                    break;
                case 3:
                    dijkstra();
                    break;
                case 4:
                    floyd();
                    break;
                case 5:
                    prim();
                    break;
                case 6:
                    kruskal();
                    break;
                case 7:
                    euler();
                    break;
                case 8:
                    write_data("graphdata.txt");
                    break;
                default:
                    System.out.println("Return...");
            }
        } while (choice > 0 && choice < Menu.graphMenu.length);
    }
    
    public static void main(String[] args) {
        Graph.getINSTANCE().printAdjacentMatrix();
        Graph.getINSTANCE().ijk(0, 6);
    }
}
