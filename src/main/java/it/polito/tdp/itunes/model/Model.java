package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.itunes.db.Adiacenze;
import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	private ItunesDAO dao;
	private Graph<Track,DefaultWeightedEdge> grafo;
	private Map<Integer,Track> idMap;
	private List<Track> best;
	
	public Model(){
		dao=new ItunesDAO();
	}
	
	public String creaGrafo(Genre genere) {
		this.grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		idMap=new HashMap<>();
		for(Track t: dao.getTrackByGenre(genere))
			idMap.put(t.getTrackId(), t);
		Graphs.addAllVertices(this.grafo,dao.getTrackByGenre(genere));
		
		for(Adiacenze a:this.dao.getAdiacenze(genere, idMap))
			Graphs.addEdge(this.grafo, a.getT1(), a.getT2(), a.getDelta());
		
		return "Grafo creato:\n#Vertici: "+this.grafo.vertexSet().size()+"\n#Archi: "+this.grafo.edgeSet().size();
		
	}
	
	
	public List<Genre> getAllGenre(){
		return this.dao.getAllGenres();
	}
	
	public List<Adiacenze> getDeltaMassimo(Genre g) {
		List<Adiacenze> res=this.dao.getAdiacenze(g, idMap);
		Collections.sort(res);
		List<Adiacenze> r=new ArrayList<>();
		for(int i=0;i<=4;i++) {
			if(res.get(i).getDelta()==res.get(0).getDelta()) {
				r.add(res.get(i));
			}
		}
		return r;
	}
	
	public Collection<Track> getAllSong(){
		return this.idMap.values();
	}
	
	public List<Track> creaLista(Track c, int memoria){
		List<Track> parziale=new ArrayList<>();
		best=new ArrayList<>();
		ConnectivityInspector<Track, DefaultWeightedEdge> ci = new ConnectivityInspector<>(this.grafo);
		List<Track> canzoniValide=new ArrayList<>(ci.connectedSetOf(c));
		canzoniValide.remove(c);
		
		parziale.add(c);
		cerca(parziale,memoria,canzoniValide,1);
		return best;
	}

	private void cerca(List<Track> parziale, int memoria, List<Track> canzoniValide, int livello) {
		if(getDimensione(parziale)>memoria) {
			return;
		}
		if(parziale.size()>best.size()) {
			best=new ArrayList<>(parziale);
		}
		if(livello==canzoniValide.size()) {
			return;
		}
		
		parziale.add(canzoniValide.get(livello));
		cerca(parziale,memoria,canzoniValide,livello+1);
		parziale.remove(parziale.size()-1);
		cerca(parziale,memoria,canzoniValide,livello+1);
	}


	private int getDimensione(List<Track> parziale) {
		int dim=0;
		for(Track t:parziale) {
			dim+=t.getBytes();
		}
		return dim;
	}
}
