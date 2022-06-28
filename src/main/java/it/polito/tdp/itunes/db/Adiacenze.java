package it.polito.tdp.itunes.db;

import it.polito.tdp.itunes.model.Track;

public class Adiacenze implements Comparable<Adiacenze> {
	private Track t1;
	private Track t2;
	private int delta;
	
	public Adiacenze(Track t1, Track t2, int delta) {
		super();
		this.t1 = t1;
		this.t2 = t2;
		this.delta = delta;
	}
	public Track getT1() {
		return t1;
	}
	public Track getT2() {
		return t2;
	}
	public int getDelta() {
		return delta;
	}
	@Override
	public int compareTo(Adiacenze o) {
		return o.getDelta()-this.getDelta();
	}
	
	
	
}
