/*

    Aidan Chandra
    Mr. Paige
    Data Strucures
    4/23/18

*/
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

public class SymbolGraph implements Iterable<SymbolGraph.Vertex> {

    private HashMap<String, Vertex> map;
    private Array<Vertex> vertices;
    private boolean directed;
    private int edges;
    private static boolean debug = false;

    public SymbolGraph() {
        this(false);
    }

    public SymbolGraph(boolean directed) {
        this.map = new HashMap<String, Vertex>();
        this.vertices = new Array<Vertex>();
        this.directed = directed;
        this.edges = 0;
    }

    public int vertices() {
        return this.vertices.size();
    }

    public int edges() {
        return this.edges;
    }

    public Vertex get(int index) {
        return this.vertices.get(index);
    }

    public Vertex find(String name) {
        Vertex result = this.map.get(name);
        if (result == null) {
            result = new Vertex(name);
            this.vertices.append(result);
        }
        return result;
    }

    public Vertex addVertex(String name) {
        return find(name);
    }

    public void addEdge(int from, int to) {
        addEdge(this.vertices.get(from), this.vertices.get(to));
    }

    public void addEdge(String from, String to) {
        addEdge(find(from), find(to));
    }

    public void addEdge(Vertex from, Vertex to) {
        if (!directed) {
            to.neighbor(from);
        }
        from.neighbor(to);
    }

    public Iterator<Vertex> iterator() {
        return this.vertices.iterator();
    }

    public class Vertex {

        private List<Vertex> neighbors;
        private String name;
        private int index;
        private int eccentricity;

        public Vertex(String name) {
            this.neighbors = new SinglyLinkedList<Vertex>();
            this.index = SymbolGraph.this.map.size();
            SymbolGraph.this.map.put(name, this);
            this.name = name;
        }

        public int index() {
            return this.index;
        }

        public String name() {
            return this.name;
        }

        public int degree() {
            return this.neighbors.size();
        }

        public boolean isAdjacent(Vertex to) {
            return this.neighbors.contains(to);
        }

        public void neighbor(Vertex neighbor) {
            this.neighbors.prepend(neighbor);
            SymbolGraph.this.edges++;
        }

        public List<Vertex> neighbors() {
            return this.neighbors;
        }

        public String toString() {
            return this.name + ": " + this.neighbors;
        }

        public void setEccentricity(int in) {
            this.eccentricity = in;
        }

        public int getEccentricity() {
            return this.eccentricity;
        }
    }

    public static SymbolGraph read(boolean directed) {
        SymbolGraph graph = new SymbolGraph(directed);

        String line = Input.readString();
        while (line != null) {
            int comma = line.indexOf(',');
            if (comma >= 0) {
                String from = line.substring(0, comma);
                String to = line.substring(comma + 1);
                graph.addEdge(from, to);
            } else {
                graph.addVertex(line);
            }
            line = Input.readString();
        }
        return graph;
    }

    //__________________________________
    public static void print(SymbolGraph a) {
        for (SymbolGraph.Vertex vertex : a) {
            System.out.print(vertex.name() + ": ");
            for (SymbolGraph.Vertex neighbor : vertex.neighbors()) {
                System.out.print(neighbor.name() + " ");
            }
            System.out.println();
        }
    }

    private static void computeEccentricity() throws Queue.UnderflowException, Queue.EmptyException {
        int distances[] = new int[48];
        //Compute the eccentricity and store it (I store it in the vertex class too just for convienence)
        for (Vertex v : graph) {
            int result = breadthFirstSearch(v);
            if(debug) System.out.println(v.name + ": " + result);
            v.setEccentricity(result);
            distances[v.index] = result;
        }
        //Printout the diameter and radius, pretty self explanatory
        System.out.println("Diameter: " + computeMax(distances));
        int min = computeMin(distances);
        System.out.println("Radius: " + min);
        
        //Now we print out all the centers, of which there may be multiple
        System.out.print("Center(s): ");
        for(Vertex v : graph)
            if(v.getEccentricity() == min)
                System.out.print(v.name + ", ");
        System.out.println("");
        
        //And finally print out the weiner index
        int sum = 0;
        for(int a : distances)
            sum += a;
        System.out.println("Weiner Index: " + sum);
    }
    
    private static int computeMin(int arr[]) {
        int max = arr[0];
        for (int a : arr) {
            max = Math.min(a, max);
        }
        return max;
    }

    private static int computeMax(int arr[]) {
        int max = arr[0];
        for (int a : arr) {
            max = Math.max(a, max);
        }
        return max;
    }

    
    private static int breadthFirstSearch(Vertex start) throws Queue.UnderflowException, Queue.EmptyException {
        //Neccessary array to ensure we do not visit a node twice
        boolean visited[] = new boolean[48];
        //Keep a queue of vertecies and a queue of the depth of the verticies - they will be added to and poped from identically
        ListQueue<Vertex> queue = new ListQueue<Vertex>();
        ListQueue<Integer> numQueue = new ListQueue<Integer>();
        //Set the starting node as visited
        visited[start.index()] = true;
        queue.enqueue(start);
        numQueue.enqueue(new Integer(1));

        //Keep last
        int last = -1;
        //While the queue isnt empty
        while (!queue.isEmpty()) {
            //Pop off the top from both queues and mark as visited
            start = queue.dequeue();
            int current = numQueue.dequeue();
            visited[start.index] = true;
            //For all neigbors
            for (Vertex v : start.neighbors) {
                //If we havent visited it make sure to visit it
                if (visited[v.index()] == false) {
                    //As we keep adding new nodes to visit keep track of the depth
                    queue.enqueue(v);
                    numQueue.enqueue(current + 1);
                }
            }
            //Keep track of the most recent depth before we pop it off (queue will be empty when we exit)
            last = current;
        }
        //Return the max of the depth
        return last;
    }
     
    private static SymbolGraph graph;

    
    public static void main(String[] args) throws Queue.UnderflowException, Queue.EmptyException {
        graph = new SymbolGraph(true);

        //Initialize the graph
        for (State s : States.states) {
            if (s.toString().equals("Hawaii") || s.toString().equals("Alaska")) {
                continue;
            }
            graph.addVertex(s.name());
            if (debug) {
                System.out.println("S: " + s.toString());
            }
            for (State a : s.neighbors()) {
                if (debug) {
                    System.out.println("            A: " + a.toString());
                }
                graph.addEdge(s.toString(), a.toString());
            }
        }
        //Compute the eccentricity which will print out all relevant 
        computeEccentricity();
        
    }
}
