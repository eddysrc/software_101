package il.ac.tau.cs.sw1.ex7;
import java.util.*;


public class Graph implements Greedy<Graph.Edge>{
    List<Edge> lst; //Graph is represented in Edge-List. It is undirected. Assumed to be connected.
    int n; //nodes are in [0,...,n-1]

    Graph(int n1, List<Edge> lst1){
        lst = lst1;
        n = n1;
    }

    public static class Edge{
        int node1, node2;
        double weight;

        Edge(int n1, int n2, double w) {
            node1 = n1;
            node2 = n2;
            weight = w;
        }

        @Override
        public String toString() {
            return "{" + "(" + node1 + "," + node2 + "), weight=" + weight + '}';
        }
    }

    // Sub functions for Iterator:

    private Edge findNextEdge(List<Edge> edge_list){ // Finds and removes (from the iterator)
                                                     // the next best edge to add.
        Edge current_best = null;
        if (!edge_list.isEmpty()) {
            current_best = edge_list.get(0);
            for (Edge edge : edge_list) {
                if (edge.weight < current_best.weight){ // Clearly a new better edge to add.
                    current_best = edge;
                }
                else { // Both of the edges have the same weight.
                    if (edge.weight == current_best.weight) {
                        if (edge.node1 < current_best.node1) current_best = edge;
                        else {
                            if (edge.node1 == current_best.node1){
                                if (edge.node2 < current_best.node2) current_best = edge;
                            }
                        }
                    }
                }
            }
        }
        if (current_best != null){
            edge_list.remove(current_best);
        }
        return current_best;
    }

    @Override
    public Iterator<Edge> selection() {
        List<Edge> new_iterator_list = new ArrayList<>();
        new_iterator_list.addAll(this.lst);
        return new Iterator<Edge>() {
            @Override
            public boolean hasNext() {
                return !new_iterator_list.isEmpty();
            }

            @Override
            public Edge next() {
                return findNextEdge(new_iterator_list);
            }
        };
    }

    // Sub functions for checking circles:

    private LinkedList<Integer>[] buildGraph(List<Edge> edges, int size){
        LinkedList<Integer>[] graph = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            graph[i] = new LinkedList();
        }
        int node1, node2;

        for(Edge edge: edges){
            node1 = edge.node1;
            node2 = edge.node2;
            graph[node1].add(node2);
            graph[node2].add(node1);
        }

        return graph;
    }

    private boolean checkCycleHelper(int node, boolean[] visited, int parent, LinkedList<Integer>[] graph){
        visited[node] = true;
        Integer i;
        Iterator<Integer> iter = graph[node].iterator();
        while(iter.hasNext()){
            i = iter.next();

            if(!visited[i]){
                if (checkCycleHelper(i, visited, node, graph)) return true;
            }
            else if (i != parent) return true;
        }
        return false;
    }

    private boolean checkCycle(List<Edge> edges, int size){
        boolean[] visited_nodes = new boolean[size];
        for (int i = 0; i < size; i++) {
            visited_nodes[i] = false;
        }

        LinkedList<Integer>[] graph = buildGraph(edges, size);

        for (int i = 0; i < size; i++){
            if(!visited_nodes[i]){
                if (checkCycleHelper(i, visited_nodes, -1, graph)) return true;
            }
        }
        return false;
    }

    @Override
    public boolean feasibility(List<Edge> candidates_lst, Edge element) {
        List<Edge> check_list = new ArrayList<Edge>();
        check_list.addAll(candidates_lst);
        check_list.add(element);
        return !checkCycle(check_list, this.n + 1);
    }

    @Override
    public void assign(List<Edge> candidates_lst, Edge element) {
        candidates_lst.add(element);
    }

    @Override
    public boolean solution(List<Edge> candidates_lst) {
        return candidates_lst.size() == this.n;
    }
}
