package micro_array;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class main {
	         
	//////////////////
	// Main-Methode //
	//////////////////
	public static void main(String[] args) throws IOException, InterruptedException{		
		
		// Getwd()
		File currentDirectory = new File(new File(".").getAbsolutePath());
		System.out.println(currentDirectory.getCanonicalPath());
		System.out.println(currentDirectory.getAbsolutePath());
		
		
		//starten der R-Skriptes 
		//ExecuteR.runIt();
				
	
		// Laden der Dateien
		System.out.println("Laden der Daten");
	 	try{
	 		String[][] raw =input.readRaw("output/ND_Group2_133Plus_2/exprs/ND_Group2_133Plus_2_signals.txt");
	 		String[][] mas5 = input.readMAS5("output/ND_Group2_133Plus_2/MAS5/ND_Group2_133Plus_2_MAS5_500.txt");
	 		String[][] pm = input.readPm("output/ND_Group2_133Plus_2/pm/ND_Group2_133Plus_2_signals_PM.txt");
	 		String[][] mm = input.readMm("output/ND_Group2_133Plus_2/mm/ND_Group2_133Plus_2_signals_MM.txt");
	 		String[][] pma = input.readPMA("output/ND_Group2_133Plus_2/PMA/PMA_Calls.txt");
	 		
		
	 		// Namen der Chips
	 		System.out.println("Lese Chip-Namen");
	 		String[] Celnames = Arrays.copyOfRange(mas5[0], 0, mas5.length);
		
	 		// Namen der Probes 
	 		System.out.println("Lese Probeset-Namen");
	 		String[] mas5Names = output.getProbes(mas5);
		
	 		// Konvertiert in Matrix ohne erste Zeile und ohne erste Reihe
	 		System.out.println("Erstelle Matrix der Daten");
	 		double[][] mas5Double = micro_math.makeDouble(mas5);
	 		
	 		// �berpr�ft ob alle Present in Gruppe
	 		System.out.println("Erstelle Matrix der Daten");
	 		String[] present = micro_math.isPresent(pma);
	 		
	 		//Erstellt Liste ob hoch oder niedrig exprimiert
	 		System.out.println("Teste ob h�her exprimiert in Gruppe 1 als Gruppe 2");
	 		String[] express = micro_math.highOrLow(mas5Double);

	 		// T-Test
	 		System.out.println("t-Test wird durchgef�rt");
	 		double[] mas5test = micro_math.studT(mas5Double);	 		
	 		System.out.println("Schreibe p-values in p-values.txt");
			File tdic = new File("output/ND_Group2_133Plus_2/t-Test/");
	        tdic.mkdir();
	 		output.writeTXT(mas5Names,express,mas5test,"output/ND_Group2_133Plus_2/t-Test/p-values.txt");
	 		
	 		//Merge and Sort
	 		System.out.println("Bubble-Sort f�r p-values");
	 		micro_math.sortIt(mas5test,mas5Names,express);
			System.out.println("Schreibe sortierte p-values in p-values_sorted.txt");
			output.writeTXT(mas5Names,express,mas5test,"output/ND_Group2_133Plus_2/t-Test/p-values_sorted.txt");	
	 	}
		catch(IOException ex) {
			System.err.println("Kein Output-Ordner... Bitte lassen Sie erst readCel.R laufen!");
			ex.printStackTrace();
		}	
	}
}
