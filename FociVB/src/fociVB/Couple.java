package fociVB;

import java.util.Random;

public class Couple {
	private Husband husband;
	private Wife wife;
	private int chanceToWatch;	//Focirajongas szintje
	
	
	public Couple(Husband husbandPar, Wife wifePar) {
		// 5 es 20 kozti random szam valasztasa a rajongasi szint beallitasahoz
		Random r = new Random();
		chanceToWatch = r.nextInt(15) + 5;
		
		husband = husbandPar;
		wife = wifePar;
	}
	
	public void watch(Game currentGame) {
		//Meccs-kihagyas eldontese
		Random r = new Random();
		if(r.nextInt(20) > chanceToWatch)
			return;
		husband.watch(currentGame);
		wife.watch(currentGame);
	}
	
	//getterek
	public Husband getHusband() {
		return husband;
	}
	public Wife getWife() {
		return wife;
	}
	
	//Direkt a kiirashoz keszitett metodusok, az informaciot megfelelo formatumban adjak ki
	public String getCoupleString() {
		return String.format("%s - %s", husband.getName(), wife.getName());
	}
	
	public String getHusbandString() {
		return String.format("%s %d sört ivott meg", husband.getName(), husband.getBeers());
	}
	
	public String getWifeString() {
		return String.format("%s szabadideje: %d perc", wife.getName(), wife.getFreeTime());
	}
}
