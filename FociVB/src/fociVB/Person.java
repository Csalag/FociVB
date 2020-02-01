package fociVB;

import java.util.ArrayList;

public abstract class Person {
	protected String name;
	protected ArrayList<Game> alreadySeen; //mar megnezett meccsek listaja

	//konstruktor
	public Person(String personName) {
		name = personName;
		alreadySeen = new ArrayList<Game>(0);
	}
	//meccsnezes fv.
	public void watch(Game m) {}
	
	//getter
	public String getName() {
		return name;
	}
}
