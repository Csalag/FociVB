package fociVB;

public class Husband extends Person {
	
	private int beers;
	
	public Husband(String husbandName) {
		super(husbandName);
		beers = 0;
	}
	
	
	//meccnezes()
	public void watch(Game currentGame){
		
		//megnezett lista kezelese
		for(Game m : alreadySeen) {
			if(m.equals(currentGame)) {
				System.out.println("Error: a person can't watch the same Game twice");
				return;
			}
		}
		alreadySeen.add(currentGame);
		
		//Sorok szamanak frissitese meccs josaga alapjan
		if(currentGame.isGood() == true)
			beers += BEERS_IF_GOOD;
		else
			beers += BEERS_IF_BORING;
	}
	
	
	//getter
	public int getBeers() {
		return beers;
	}
	
	
	//konstansok
	private static final int BEERS_IF_GOOD = 2;
	private static final int BEERS_IF_BORING = 4;
}
