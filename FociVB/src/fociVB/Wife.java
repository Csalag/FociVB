package fociVB;

public class Wife extends Person{
	
	private int freeTime;
	
	
	
	public Wife(String wifeName) {
		super(wifeName);
		freeTime = 0;
	}
	
	
	
	//meccsnezes()
	public void watch(Game currentGame) {
		//megnezett lista kezelese
		for(Game m : alreadySeen) {
			if(m.equals(currentGame)) {
				System.out.println("Hiba: Egy ember nem nézhet meg egy meccset kétszer");
				return;
			}
		}
		
		//megnezett lista bovitese, es szabadido frissitese
		alreadySeen.add(currentGame);
		freeTime += currentGame.lenght();
	}
	
	
	
	//getter
	public int getFreeTime() {
		return freeTime;
	}
}
