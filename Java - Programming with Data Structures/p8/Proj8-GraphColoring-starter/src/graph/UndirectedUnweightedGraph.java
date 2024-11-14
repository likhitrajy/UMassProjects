package graph;
import java.util.ArrayList;
/**
 * This class implements general operations on a graph as specified by UndirectedGraphADT.
 * It implements a graph where data is contained in Vertex class instances.
 * Edges between verticies are unweighted and undirected.
 * A graph coloring algorithm determines the chromatic number. 
 * Colors are represented by integers. 
 * The maximum number of vertices and colors must be specified when the graph is instantiated.
 * You may implement the graph in the manner you choose. See instructions and course material for background.
 */
 
 public class UndirectedUnweightedGraph<T> implements UndirectedGraphADT<T> {
   // private class variables here.
   
   private int MAX_VERTICES;
   private int MAX_COLORS;
  
   private int edge;
   private ArrayList<Vertex<T>> vertices = new ArrayList<Vertex<T>>(); 
   private boolean edges[][];

   
   /**
    * Initialize all class variables and data structures. 
   */   
   public UndirectedUnweightedGraph (int maxVertices, int maxColors){
      MAX_VERTICES = maxVertices;
      MAX_COLORS = maxColors; 
    
      edge = 0;
      edges = new boolean[maxVertices][maxVertices];
   }

   /**
    * Add a vertex containing this data to the graph.
    * Throws Exception if trying to add more than the max number of vertices.
   */
   public void addVertex(T data) throws Exception {
  
    if(vertices.size() >= MAX_VERTICES) throw new Exception();
    
    Vertex<T> addition = new Vertex<T>(data);
    
    vertices.add(addition);

   }
   
   /**
    * Return true if the graph contains a vertex with this data, false otherwise.
   */   
   public boolean hasVertex(T data){
  
    boolean contains = false;
    for(int i = 0; i < vertices.size(); i++) {
      Vertex<T> current = vertices.get(i);
      if(current.getData().equals(data)){
        contains = true;
        break;
      }
    }

  return contains;
   } 

   /**
    * Add an edge between the vertices that contain these data.
    * Throws Exception if one or both vertices do not exist.
   */   
   public void addEdge(T data1, T data2) throws Exception{
  
    int data1Index = 0;
    int data2Index = 0;

    if(!hasVertex(data1) || !hasVertex(data2)) throw new Exception();

    for(int i = 0; i < vertices.size(); i++){
      Vertex<T> current = vertices.get(i);
      if(current.getData().equals(data1)){
        data1Index = i;
        break;
      }
    }


    for(int i = 0; i < vertices.size(); i++){
      Vertex<T> current = vertices.get(i);
      if(current.getData().equals(data2)){
        data2Index = i;
        break;
      }
    }

    edges[data1Index][data2Index] = true;
    edges[data2Index][data1Index] = true;
    edge++;
   }

   /**
    * Get an ArrayList of the data contained in all vertices adjacent to the vertex that
    * contains the data passed in. Returns an ArrayList of zero length if no adjacencies exist in the graph.
    * Throws Exception if a vertex containing the data passed in does not exist.
   */   
   public ArrayList<T> getAdjacentData(T data) throws Exception{
  
    if(!hasVertex(data)) throw new Exception();
    
    ArrayList<T> adjData = new ArrayList<T>();

    int dataIndex = 0;

    for(int i = 0; i < getNumVertices(); i++){
      Vertex<T> current = vertices.get(i);
      if(current.getData().equals(data)){
        
        dataIndex = i;
        break;
      }
    }

    for(int j = 0; j < getNumVertices(); j++){
      if(edges[dataIndex][j] == true){
       
        Vertex<T> current= vertices.get(j);
        adjData.add(current.getData());
      }
    }
    
    return adjData;
   }
   
   /**
    * Returns the total number of vertices in the graph.
   */   
   public int getNumVertices(){
    
      return vertices.size();
   }

   /**
    * Returns the total number of edges in the graph.
   */   
   public int getNumEdges(){
  
      return edge;
   }

   /**
    * Returns the minimum number of colors required for this graph as 
    * determined by a graph coloring algorithm.
    * Throws an Exception if more than the maximum number of colors are required
    * to color this graph.
   */   
   public int getChromaticNumber() throws Exception{
    
    int highestColour = -1;
    int colorToUse = -1;
    for(Vertex <T> current : vertices){
      if(current.getColor() == -1){
        colorToUse = getColorToUse(current);
        current.setColor(colorToUse);
      if(colorToUse >  highestColour) highestColour = colorToUse;
      }
    }

    if(highestColour > MAX_COLORS) throw new Exception();
    
      return highestColour;
     }
  
   
   private int getColorToUse(Vertex<T> current){
    int colorToUse = -1;
    Boolean[] adjColorsUsed = new Boolean[MAX_COLORS];
    ArrayList<Vertex<T>> adjVertList = getAdjVerts(current);
    int i;

     for(i = 0; i < adjVertList.size(); i++){
        if(adjVertList.get(i).getColor()!=-1){
          adjColorsUsed[i] = true;
          break;
        }
      }

     colorToUse = i;

     return colorToUse;
  }


   private ArrayList<Vertex<T>> getAdjVerts(Vertex<T> vertex){
    ArrayList<Vertex<T>> adjVert = new ArrayList<Vertex<T>>();

    int vertInd = -1;
    for(int i = 0; i < vertices.size(); i++){
      if(vertices.get(i).equals(vertex)) {
        vertInd = i;
      }
    }

    for(int i = 0; i < vertices.size(); i++){
      if(edges[vertInd][i] == true){
        adjVert.add(vertices.get(i));
      }
    }

    return adjVert;
   }
   }
   
   
