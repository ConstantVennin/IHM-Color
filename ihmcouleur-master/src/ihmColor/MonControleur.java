package ihmColor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class MonControleur {
	@FXML
	Button minus0,minus1,minus2,minus3,minus4,minus5,minus6,minus7,minus8,minus9;
	@FXML
	Button plus0,plus1,plus2,plus3,plus4,plus5,plus6,plus7,plus8;
	@FXML
	Button NbGrisMinus0,NbGrisPlus0;
	@FXML
	Label labelNbGris0;
	@FXML
	TextField inputColor0,inputColor1,inputColor2,inputColor3,inputColor4,inputColor5,inputColor6,inputColor7,inputColor8,inputColor9;
	@FXML
	TextField inputColorDisplay0,inputColorDisplay1,inputColorDisplay2,inputColorDisplay3,inputColorDisplay4,inputColorDisplay5,inputColorDisplay6,inputColorDisplay7,inputColorDisplay8,inputColorDisplay9;
	@FXML
	TextField outputColor0,outputColor1,outputColor2,outputColor3,outputColor4,outputColor5,outputColor6,outputColor7,outputColor8,outputColor9;
	@FXML
	TextField outputColorDisplay0,outputColorDisplay1,outputColorDisplay2,outputColorDisplay3,outputColorDisplay4,outputColorDisplay5,outputColorDisplay6,outputColorDisplay7,outputColorDisplay8,outputColorDisplay9;
	@FXML
	ChoiceBox<Integer> choiceBox;
	@FXML
	CheckBox checkboxBW;
	@FXML
	Slider sliderR, sliderG, sliderB;
	
	ArrayList<Integer> listColor = new ArrayList<Integer>();

	static TextField[] textFieldOutput;
	static Button[] buttonsPlus;
	static Button[] buttonsMinus;
	static TextField[] textFieldInput;
	static TextField[] textFieldDisplayOutput;
	static TextField[] textFieldDisplayInput;




	public void initialize() {
		System.out.println("Initialisation...");
		buttonsPlus = new Button[] {plus0,plus1,plus2,plus3,plus4,plus5,plus6,plus7,plus8};
		buttonsMinus = new Button[] {minus0,minus1,minus2,minus3,minus4,minus5,minus6,minus7,minus8,minus9};
		textFieldInput = new TextField[] {inputColor0,inputColor1,inputColor2,inputColor3,inputColor4,inputColor5,inputColor6,inputColor7,inputColor8,inputColor9};
		textFieldOutput = new TextField[] {outputColor0,outputColor1,outputColor2,outputColor3,outputColor4,outputColor5,outputColor6,outputColor7,outputColor8,outputColor9};
		textFieldDisplayOutput = new TextField[] {outputColorDisplay0,outputColorDisplay1,outputColorDisplay2,outputColorDisplay3,outputColorDisplay4,outputColorDisplay5,outputColorDisplay6,outputColorDisplay7,outputColorDisplay8,outputColorDisplay9};
		textFieldDisplayInput = new TextField[] {inputColorDisplay0,inputColorDisplay1,inputColorDisplay2,inputColorDisplay3,inputColorDisplay4,inputColorDisplay5,inputColorDisplay6,inputColorDisplay7,inputColorDisplay8,inputColorDisplay9};
		listColor.add(1);
		choiceBox.getItems().addAll(listColor);
		choiceBox.setValue(1);
		
	}

	public void pressedButtonPlus(ActionEvent event) {
		listColor.add(listColor.size()+1);
		int id = Integer.parseInt(event.getSource().toString().substring(14, 15));
		if(id==0) {
			buttonsMinus[0].setVisible(true);
		}
		if (id!=8) {
			buttonsMinus[id+1].setVisible(true);
			textFieldInput[id+1].setVisible(true);
			buttonsPlus[id].setVisible(false);
			buttonsPlus[id+1].setVisible(true);
		}else {
			buttonsMinus[id+1].setVisible(true);
			textFieldInput[id+1].setVisible(true);
			buttonsPlus[id].setVisible(false);
		}
		choiceBox.getItems().clear();
		choiceBox.getItems().addAll(listColor);
		choiceBox.setValue(listColor.size());
		checkDisplayResult();
	}

	public void pressedButtonMinus(ActionEvent event) {
		listColor.remove(listColor.size()-1);
		int id = Integer.parseInt(event.getSource().toString().substring(15, 16));
		if (id==9) {
			textFieldInput[id].setVisible(false);
			textFieldInput[id].clear();
			textFieldOutput[id].setVisible(false);
			textFieldOutput[id].clear();
			textFieldDisplayOutput[id].setVisible(false);
			buttonsPlus[id-1].setVisible(true);
			buttonsMinus[id].setVisible(false);
			
		}else if(id==0 && getLastVisible()==1){
			System.out.println("toto");
			textFieldInput[0].setText(textFieldInput[1].getText());
			textFieldInput[1].setVisible(false);
			textFieldInput[1].clear();
			textFieldOutput[1].setVisible(false);
			textFieldOutput[1].clear();
			textFieldDisplayOutput[1].setVisible(false);
			buttonsPlus[0].setVisible(true);
			buttonsPlus[1].setVisible(false);
			buttonsMinus[0].setVisible(false);
			buttonsMinus[1].setVisible(false);
			
		}else {
			deleteOnCascade(id);
		}
		choiceBox.getItems().clear();
		choiceBox.getItems().addAll(listColor);
		
		if (getLastVisible()==0) {
			buttonsMinus[0].setVisible(false);
		}
		checkDisplayResult();
		update();		
	}

	public static void deleteOnCascade(int id) {
		textFieldInput[id].clear();
		textFieldOutput[id].clear();

		for (int i = id; i < 9; i++) {
			textFieldInput[i].setText(textFieldInput[i+1].getText());
		}

		int idx = getLastVisible();
		textFieldInput[idx].setVisible(false);
		textFieldInput[idx].clear();
		buttonsMinus[idx].setVisible(false);
		try {
			buttonsPlus[idx].setVisible(false);
		} catch (Exception e) {

		}

		buttonsPlus[idx-1].setVisible(true);
		applyColor();
	}

	public static int getLastVisible() {
		int idx = 9;
		while(!textFieldInput[idx].isVisible()) {
			idx--;
		}
		return idx;
	}

	public static void checkDisplayResult() {
		for (int i = 0; i < textFieldInput.length; i++) {
			if (textFieldInput[i].isVisible()) {
				textFieldOutput[i].setVisible(true);
				textFieldDisplayOutput[i].setVisible(true);
				textFieldDisplayInput[i].setVisible(true);
			}else {
				textFieldOutput[i].setVisible(false);
				textFieldOutput[i].clear();
				textFieldDisplayOutput[i].setVisible(false);
				textFieldDisplayInput[i].setVisible(false);
			}
		}
	}

	public static void applyColor() {
		int[] rgb;
		for (int i = 0; i < textFieldOutput.length; i++) {
			textFieldDisplayInput[i].setStyle(null);
			textFieldDisplayOutput[i].setStyle(null);
		}

		for (int i = 0; i < textFieldOutput.length; i++) {
			rgb = hexaOrDecTo3numbers(textFieldOutput[i].getText());
			//if(isValid(textFieldOutput[i].getText())) {
				if (rgb!=null) {
					textFieldDisplayOutput[i].setStyle("-fx-background-color: rgb(" + rgb[0] + "," + rgb[1] + ", " + rgb[2] + ");");
				}
				rgb = hexaOrDecTo3numbers(textFieldInput[i].getText());
				if (rgb!=null) {
					textFieldDisplayInput[i].setStyle("-fx-background-color: rgb(" + rgb[0] + "," + rgb[1] + ", " + rgb[2] + ");");
				}
			//}
		}
	}

	public static String toBandW(String s) {
		int[] tab = hexaOrDecTo3numbers(s);
		int nb1 = tab[0];
		int nb2 = tab[1];
		int nb3 = tab[2];
		double nbA = 0.3*nb1;
		double nbB = 0.59*nb2;
		double nbC = 0.11*nb3;
		Double doubleRes = nbA+nbB+nbC;
		int res = doubleRes.intValue();
		if(!s.contains(".")) {
			return "" + Integer.toHexString(res) + "" + Integer.toHexString(res) + "" + Integer.toHexString(res); 
		}else if(s != null && !s.equals("")){
			return res+"."+res+"."+res;
		}else {
			return null;
		}
	}

	public static String[] decToTab(String s) {
		String[] res = new String[3];
		res[0] = "";
		res[1] = "";
		res[2] = "";
		int tabCompteur = 0;
		for(int i = 0 ; i < s.length(); i++) {
			if(s.substring(i, i+1).equals(".")) {
				tabCompteur ++;
			}else {
				res[tabCompteur] += s.substring(i, i+1);
				System.out.println(res[tabCompteur]);//debug
			}
		}
		System.out.println(res[0] + " . " + res[1] + " . " + res[2]);//debug
		return res;
	}

	public void update() {
		for (int i = 0; i < textFieldOutput.length; i++) {
			textFieldOutput[i].clear();
		}
		
		for(int idx = 0; idx<10; idx++) {
			if(textFieldInput[idx] != null && isValid(textFieldInput[idx].getText())) {
				if(checkboxBW.isSelected()) {
					textFieldOutput[idx].setText(toBandW(textFieldInput[idx].getText()));
				}else {
					textFieldOutput[idx].setText(textFieldInput[idx].getText());
				}
				int[] tab = hexaOrDecTo3numbers(textFieldInput[idx].getText());
				sliderR.setValue(tab[0]);
				sliderG.setValue(tab[1]);
				sliderB.setValue(tab[2]);
			}
		}
		applyColor();
	}


	public void majGaucheVersDroite(ActionEvent event) {
		update();
	}


	public void checkBW(ActionEvent event) {
		if(checkboxBW.isSelected()) {
			System.out.println("is selected");
			for(int i = 0 ; i < 10 ; i++) {
				if(textFieldInput[i] != null && isValid(textFieldInput[i].getText())) {
					System.out.println("stage du for");
					String txt = textFieldInput[i].getText();
					System.out.println("txt");
					textFieldOutput[i].setText(toBandW(txt));
					System.out.println("txt traité");
				}
			}

		}else {
			for(int i = 0 ; i < 10 ; i++) {
				if(textFieldInput[i] != null && isValid(textFieldInput[i].getText())) {
					textFieldOutput[i].setText(textFieldInput[i].getText());
				}
			}
		}
		applyColor();
	}

	public void checkboxBW(ActionEvent event) {
		for(int i = 0 ; i < 10 ; i++) {
			if(textFieldInput[i] != null && isValid(textFieldInput[i].getText())) {
				String txt = textFieldInput[i].getText();
				textFieldOutput[i].setText(toBandW(txt));
			}
		}
		for(int i = 0 ; i < 10 ; i++) {
			if(textFieldInput[i] != null && isValid(textFieldInput[i].getText())) {
				textFieldOutput[i].setText(textFieldInput[i].getText());
			}
		}
	}

	public static boolean isValid(String s) {
		if(s.length()==6) {//pour l'hexa
			if(s.matches("([0-9]|[a-f]|[A-F]){6}")) {
				return true;
			}
		}else if(s.matches("[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}")) {
			int[] st = hexaOrDecTo3numbers(s);
			if(st[0]<256 && st[1]<256 && st[2]<256) {
				System.out.println("dec correct");	//debug
				return true;
			}
			System.out.println("dec incorrect");	//debug
			
		}
		return false;
	}



	public static int[] hexaOrDecTo3numbers(String s) {
		int nb1 = 0;
		int nb2 = 0;
		int nb3 = 0;
		if (s==null || s.equalsIgnoreCase("")|| s.length()<6 || s.contains("[g-z]")) {
			return null;
		}
		if(!s.contains(".")) {
			System.out.println("hexa");
			String s1 = s.substring(0, 2);
			System.out.println("s1 : "+s1);//debug
			String s2 = s.substring(2,4);
			System.out.println("s2 : "+s2);//debug
			String s3 = s.substring(4,6);
			System.out.println("s3 : "+s3);//debug
			nb1=Integer.parseInt(s1,16);
			System.out.println("nb1 : " + nb1);//debug
			nb2=Integer.parseInt(s2,16);
			System.out.println("nb2 : " + nb2);//debug
			nb3=Integer.parseInt(s3,16);
			System.out.println("nb3 : " + nb3);//debug
		}else if(s != null && !s.equals("")){
			System.out.println("pas hexa");//debug
			String[] s1 = decToTab(s);
			System.out.println(s);//debug
			System.out.println("taille du tableau après le split " + s1.length);//debug
			nb1 = Integer.parseInt(s1[0]);
			System.out.println("après un parseint " + nb1);//debug
			nb2 = Integer.parseInt(s1[1]);
			System.out.println("après un deuxieme parseint " + nb2);//debug
			nb3 = Integer.parseInt(s1[2]);
			System.out.println("fin parseint " + nb3);//debug
		}
		int[] res = new int[] {nb1,nb2,nb3};
		return res;
	}

	public void incrementMinus(ActionEvent event) {
		if(!labelNbGris0.getText().equalsIgnoreCase("1")) {
			labelNbGris0.setText((Integer.parseInt(labelNbGris0.getText())-1)+"");
		}
	}

	public void incrementPlus(ActionEvent event) {
		if(!labelNbGris0.getText().equalsIgnoreCase("8")) {
			labelNbGris0.setText((Integer.parseInt(labelNbGris0.getText())+1)+"");
		}
	}

	public void autoGrey(ActionEvent event) {
		int size = Integer.parseInt(labelNbGris0.getText());
		Color color;
		boolean end=true;
		clearAll();
		int[] shadesOfGrey = generateGrey(size);
		
		while (end) {
			try {
				for (int i = 0; i < size; i++) {
					textFieldInput[i].setVisible(true);
					buttonsMinus[i].setVisible(true);
					textFieldInput[i].setText(generateColorFromGrey(shadesOfGrey[i]-1));
				}
				checkDisplayResult();
				update();
				buttonsPlus[getLastVisible()].setVisible(true);
				end=false;
			} catch (Exception e) {
			}
		}
		
	}

	public int[] generateGrey(int size) {
		int shade = 255/size;
		int[] fiftyShadesOfGrey = new int[size];
		for (int i = 1; i <= fiftyShadesOfGrey.length; i++) {
			fiftyShadesOfGrey[i-1]=shade*i;
		}
		return fiftyShadesOfGrey;
	}
	
	public static String generateColorFromGrey(int n) {
		Random rand = new Random();
		int nbA=0;
		int nbB=0;
		int nbC=0;
		int idGrey=-99;
		System.out.println(n);
		while(idGrey!=n) {
			nbA = (int)(0.3*rand.nextInt(256));
			nbB = (int)(0.59*rand.nextInt(256));
			nbC = (int)(0.11*rand.nextInt(256));
			idGrey = nbA+nbB+nbC;
		}
		Color color = new Color(nbA,nbB,nbC);
		return Integer.toHexString(color.getRGB()).substring(2);
	}

	public void clearAll() {
		for (int i = 0; i < textFieldInput.length-1; i++) {
			buttonsPlus[i].setVisible(false);
			buttonsMinus[i].setVisible(false);

			textFieldOutput[i].setVisible(false);
			textFieldInput[i].setVisible(false);
			textFieldOutput[i].clear();
			textFieldInput[i].clear();

			textFieldDisplayOutput[i].setVisible(false);
			textFieldDisplayInput[i].setVisible(false);
			textFieldDisplayInput[i].setStyle(null);
			textFieldDisplayOutput[i].setStyle(null);
		}
		textFieldOutput[9].setVisible(false);
		textFieldInput[9].setVisible(false);
		textFieldOutput[9].clear();
		textFieldInput[9].clear();
		buttonsMinus[9].setVisible(false);
		textFieldDisplayOutput[9].setVisible(false);
		textFieldDisplayInput[9].setVisible(false);
		textFieldDisplayInput[9].setStyle(null);
		textFieldDisplayOutput[9].setStyle(null);
	}
	
	public void setRGBSlider() {
		System.out.println("slider drag");
		if(choiceBox.getValue()!=null) {
			textFieldInput[choiceBox.getValue()-1].setText((int)sliderR.getValue() + "." + (int)sliderG.getValue() + "." + (int)sliderB.getValue());
			update();
			applyColor();
		}
	}




}
