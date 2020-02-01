package fociVB;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


//Eredetileg WorldCup volt a neve, de ugy gondolom ez logikusabb
public class Window extends JFrame {
	
	//Oszinten szolva ezt nem teljesen ertem, de a betetele eltuntetett egy warning-ot
	private static final long serialVersionUID = 1L;
	
	//Parokat tarolo lista
	private ArrayList<Couple> coupleList;
	
	//Aktualis meccs
	private Game currentGame = null;
	
	//A harom panel ami a bejegyzeseket tartalmazza
	private JPanel pCouples;			//bal panel
	private JPanel pHusbands;			//kozepso panel
	private JPanel pWiwes;				//jobb panel
	
	
	private JLabel lAverageBeers;		//sorok atlagos szamat kiiro cimke
	private JLabel lFreeTimeSum;		//szabadido osszeget kiiro cimke
	private JLabel lCurrentGame;		//aktualis meccset kiiro cimke
	
	private JButton bGame;				//"MECCS" gomb
	
	public Window(File f) {
		super("FociVB");
		coupleList = new ArrayList<Couple>(0);
		
		//coupleList inicializalasa parok.txt fajl beolvasasaval
		inicCoupleList(f);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
		getContentPane().setBackground(new Color(211, 236, 158));
		setLayout(null);
		
		
		// Harom panel elokeszitese(szinezes, pozicionalas(absolute positioning))
		pCouples = new JPanel();
		pCouples.setBackground(Color.WHITE);
		pCouples.setBounds(50, 150, 200, 300);
		pCouples.setLayout(new BoxLayout(pCouples, BoxLayout.Y_AXIS));
		add(pCouples);
		pHusbands = new JPanel();
		pHusbands.setBackground(Color.WHITE);
		pHusbands.setBounds(300, 150, 200, 300);
		pHusbands.setLayout(new BoxLayout(pHusbands, BoxLayout.Y_AXIS));
		add(pHusbands);
		pWiwes = new JPanel();
		pWiwes.setBackground(Color.WHITE);
		pWiwes.setBounds(550, 150, 200, 300);
		pWiwes.setLayout(new BoxLayout(pWiwes, BoxLayout.Y_AXIS));
		add(pWiwes);
		
		
		//lathatatlan panel az lCurrentGame-hez FlowLayout-al --> a szoveg mindig kozepen van
		JPanel pCurrentGame = new JPanel();
		pCurrentGame.setBounds(0, 0, 800, 75);
		pCurrentGame.setLayout(new FlowLayout());
		pCurrentGame.setOpaque(false);
		add(pCurrentGame);
		lCurrentGame = new JLabel("-");
		lCurrentGame.setFont(new Font("Arial", Font.BOLD, 20));
		pCurrentGame.add(lCurrentGame);
		
		//Sorok atlaga es szabadidok osszege feliratok beallitasa
		lAverageBeers = new JLabel("Sörök átlaga : 0");
		lAverageBeers.setBounds(300, 475, 250, 50);
		add(lAverageBeers);
		lFreeTimeSum = new JLabel("Szabadidõk összege: 0 perc");
		lFreeTimeSum.setBounds(550, 475, 250, 50);
		add(lFreeTimeSum);
		
		//Harom Fopanel elhelyezese
		JLabel t1 =new JLabel("Házaspárok");
		JLabel t2 =new JLabel("Férjek");
		JLabel t3 =new JLabel("Feleségek");
		t1.setBounds(50, 75, 200, 75);
		t2.setBounds(300, 75, 200, 75);
		t3.setBounds(550, 75, 200, 75);
		t1.setFont(new Font("Arial", Font.BOLD, 20));
		t2.setFont(new Font("Arial", Font.BOLD, 20));
		t3.setFont(new Font("Arial", Font.BOLD, 20));
		add(t1);
		add(t2);
		add(t3);
		
		//Gomb elhelyezese es EventHandler hozzaadasa
		bGame = new JButton("MECCS");
		bGame.setBounds(100, 475, 100, 50);
		add(bGame);
		EventHandler h = new EventHandler();
		bGame.addActionListener(h);
		
		addLabels();
	}
	
	
	private class EventHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			//meccsnezes()
			currentGame = new Game();
			for(Couple c : coupleList) {
				c.watch(currentGame);
			}
			//sorrend ujrarendezese
			sortByBeers();
			
			//sorok atlaga es szabadidok osszege 
			int beerSum = 0, freeTimeSum = 0, n = 0;
			for(Couple c : coupleList) {
				beerSum += c.getHusband().getBeers();
				freeTimeSum+= c.getWife().getFreeTime();
				n++;
			}
			
			//Sorok atlaga es szabadidok osszege feliratok frissitese
			lAverageBeers.setText(String.format("Sörök átlaga: %.2f", (double)beerSum/n));
			lFreeTimeSum.setText(String.format("Szabadidõk összege: %d", freeTimeSum));
			
			//Aktualis meccs felirat frissitese
			lCurrentGame.setText(currentGame.getGameString());
			
			//Fopanelek feliratainak frissitese
			for(Component c : pCouples.getComponents()) {
				int i = 0;
				while(c != pCouples.getComponents()[i])
					i++;
				if(c instanceof JLabel) {
					((JLabel) c).setText(coupleList.get(i).getCoupleString());
				}
			}
			for(Component c : pHusbands.getComponents()) {
				int i = 0;
				while(c != pHusbands.getComponents()[i])
					i++;
				if(c instanceof JLabel) {
					((JLabel) c).setText(coupleList.get(i).getHusbandString());
				}
			}
			for(Component c : pWiwes.getComponents()) {
				int i = 0;
				while(c != pWiwes.getComponents()[i])
					i++;
				if(c instanceof JLabel) {
					((JLabel) c).setText(coupleList.get(i).getWifeString());
				}
			}
			
			pCouples.validate();
			pHusbands.validate();
			pWiwes.validate();
		}
	}
	
	public void addLabels() {
		//Feliratok dinamikus hozzaadasa a fopanelekhez
		this.sortByBeers();
		for(Couple c : coupleList) {
			pCouples.add(new JLabel(c.getCoupleString()));
		}
		for(Couple c : coupleList) {
			pHusbands.add(new JLabel(c.getHusbandString()));
		}
		for(Couple c : coupleList) {
			pWiwes.add(new JLabel(c.getWifeString()));
		}
	}
	
	
	//Sorok szama szerinti sorbarendezes (Bubble sort)
	public void sortByBeers() {
		boolean isDone = false;
		Couple temp;
		int i;
		
		while(isDone == false) {
			isDone = true;
			for(i = 0; i < coupleList.size() - 1; i++) {
				if(coupleList.get(i).getHusband().getBeers() < coupleList.get(i+1).getHusband().getBeers()) {
					isDone = false;
					temp = coupleList.get(i);
					coupleList.set(i, coupleList.get(i+1));
					coupleList.set(i+1, temp);
				}
			}
		}
	}
	
	
	//Hazaspar lista inicializalasa
	public void inicCoupleList(File f) {
		Scanner s = null;
		try {
			s = new Scanner(f);
		}
		catch(FileNotFoundException e) {
			System.out.println("Fajl nem talalhato:" +f.getName());
			System.exit(0);
		}
		if(s == null) {
			System.out.println("Hiba tortent beolvasaskor!");
			System.exit(0);
		}
		while(s.hasNextLine()) {
			coupleList.add(new Couple(new Husband(s.next()), new Wife(s.next())));
		}
		s.close();
	}
	
	
	
	public static void main(String[] args) {
		
		String path = System.getProperty("user.dir");
		System.out.println(path);
		File Teams = new File(path.concat("\\src\\fociVB\\csapatok.txt"));
		File Couples = new File(path.concat("\\src\\fociVB\\parok.txt"));
		
		//Csapatlista inicializalo metodus meghivasa
		Game.inic(Teams);
		
		//Ablak letrehozasa
		Window Window = new Window(Couples);
		Window.setVisible(true);
		
	}
}

