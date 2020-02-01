package fociVB;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
	
	private Team team1;
	private Team team2;
	private int iD;					//meccs egyedi azonositoja, kulombseget tesz ket ugyanojan csapatosszetel kozott
	private static int cnt = 0;		//szamlalo az egyedi azonositohoz
	private boolean isGood;			//true ha a meccs jo
	private int overtime;			//hosszabbitas ideje (0-10 perc)
	
	
	
	//konstruktor
	public Game() {
		Random r = new Random();
		isGood = r.nextBoolean();
		overtime = r.nextInt(10);
		
		//Csapatok v;letlenszeru kivalasztasa a TEAMS tombbol
		team1 = new Team(TEAMS.get(r.nextInt(TEAMS.size()-1)));
		do {
		team2 = new Team(TEAMS.get(r.nextInt(TEAMS.size()-1)));
		}
		while(team1.getName() == team2.getName());
		
		//Egyedi azonosito beallitasa
		iD = cnt;
		cnt++;
	}
	
	
	
	//TEAMS lista feltoltese fajlbol
	public static void inic(File f) {
		Scanner s = null;
		TEAMS = new ArrayList<String>(0);
		try {
			s = new Scanner(f);
		}
		catch(FileNotFoundException e) {
			System.out.println("fajl nem talalhato: " +f.getName());
			System.exit(0);
		}
		if(s == null) {
			System.out.println("Hiba torten beolvasaskor!");
			System.exit(0);
		}
		//Enelkul a Scanner szeparalta a kotojeles neveket
		s.useDelimiter("\\n");
		
		while(s.hasNext())
			TEAMS.add(s.next());
		s.close();
	}
	
	
	
	//meccshoszz
	public int lenght() {
		return REGULAR_GAMETIME + overtime;
	}
	
	
	
	//getterek
	public Team getTeam1() {
		return team1;
	}
	public Team getTeam2() {
		return team2;
	}
	public int getID() {
		return iD;
	}
	public boolean isGood() {
		return isGood;
	}
	
	//A meccs kiirasahoz megfeleloen formazott string
	public String getGameString() {
		return String.format("%s - %s", team1.getName(), team2.getName());
	}
	
	//Teszteleshez hasznaltam, bennemaradt az elkuldott verzioban
	//igy bennhagytam itt is, de nincs felhasznalva 
	public static void printTeams() {
		for(String t : TEAMS) {
			System.out.print(t);
		}
	}
	
	private static final int REGULAR_GAMETIME = 90;
	
	//Csapatok tombje
	private static ArrayList<String> TEAMS;
}
