package tshpath;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Solution to TSHPATH problem in sphere online judge
 * 
 * @author dharanikumar
 *
 */
public class Main {

	public Main() throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in), 8192);
		
		int numTestCases = Integer.parseInt(br.readLine());

		for (int i = 0; i < numTestCases; i++) {
			int numCities = Integer.parseInt(br.readLine());

			Map<String, Integer> cityNameIndexMap = new HashMap<String, Integer>(numCities, 1);

			WeightedDirectedGraph graph = new WeightedDirectedGraph(numCities);

			for (int j = 0; j < numCities; j++) {
				String strCityName = br.readLine();
				int numNeighbours = Integer.parseInt(br.readLine());

				cityNameIndexMap.put(strCityName, j + 1);
				for (int k = 0; k < numNeighbours; k++) {
					String[] indexCost = br.readLine().split(" ");

					int index = Integer.parseInt(indexCost[0]);
					int cost = Integer.parseInt(indexCost[1]);
				}
			}
			
			int numRoutesToFind = Integer.parseInt(br.readLine());
			//Create buffer to hold the new line character
			StringBuilder sb = new StringBuilder(numRoutesToFind * 2);
			
			for(int r = 0; r < numRoutesToFind; r++) {
				String[] cities = br.readLine().split(" ");
				
				int fromIndex = cityNameIndexMap.get(cities[0]);
				int toIndex = cityNameIndexMap.get(cities[1]);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		new Main();
	}

	public class WeightedDirectedGraph {

		private int numVertices;
		private ArrayList<WeightedEdge> weightedEdges[];

		public WeightedDirectedGraph(int numVertices) {
			this.numVertices = numVertices;
			weightedEdges = (ArrayList<WeightedEdge>[]) new ArrayList[numVertices];

			for (int i = 0; i < numVertices; i++) {
				weightedEdges[i] = new ArrayList<WeightedEdge>();
			}
		}
		
		public void addEdge(WeightedEdge edge) {
			weightedEdges[edge.from].add(edge);
		}
	}

	public class WeightedEdge {
		
		private int from;
		private int to;
		private int weight;

		public WeightedEdge(int from, int to, int weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
	}
}
