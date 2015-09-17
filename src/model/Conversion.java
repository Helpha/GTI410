package model;

import java.awt.Color;

/***
 * classe proposant des méthodes statiques pour la conversion entre les
 * différents types de couleur
 * 
 * @author Marc
 *
 */
public class Conversion {
 // basé sur les notes du cours
	public static float[] RGBtoHSV(Color rgb) {

		// v est le max de la valeur R, G ou B, initié à 0
		int[] vmax = new int[2]; // vmax[0] = couleur (0 = r , 1 = g, 2 = b) ,
									// vmax[1] = valeur
		int[] vmin = new int[2]; // vmax[0] = couleur (0 = r , 1 = g, 2 = b) ,
									// vmax[1] = valeur
		float h, s, v;
		float red, green, blue;
		red = rgb.getRed();
		green = rgb.getGreen();
		blue = rgb.getBlue();

		// le [1] contient la valeur du max et du min
		vmax[1] = (int) Math.max(red, Math.max(green, blue));
		vmin[1] = (int) Math.min(red, Math.min(green, blue));
		
		System.out.println("vmax: " + vmax[1] + ", vmin: " + vmin[1]);

		v = vmax[1];
		s = (v - vmin[1]) / v;

		// on valide ensuite si les valeur min et max représentent le Rouge,
		// Vert, ou Bleu
		// couleur max est rouge
		if (vmax[1] == red) {
			vmax[0] = 0;
			System.out.println("vmax est rouge");
		}
		// couleur max est vert
		else if (vmax[1] == green) {
			vmax[0] = 1;
			System.out.println("vmax est vert");
		}
		// couleur max est bleu
		else {
			vmax[0] = 2;
			System.out.println("vmax est bleu");
		}
		// couleur min est rouge
		if (vmin[1] == red) {
			vmin[0] = 0;
			System.out.println("vmin est rouge");
		}
		// couleur max est vert
		else if (vmin[1] == green) {
			vmin[0] = 1;
			System.out.println("vmin est vert");
		}
		// couleur max est bleu
		else {
			vmin[0] = 2;
			System.out.println("vmin est bleu");
		}

		// on calcule ensuite le s
		h = 0;
		if (vmax[0] == 0 && vmin[0] == 1) {
			h = (5 + ((red - blue) / (red - green)));
		} else if (vmax[0] == 0 && vmin[0] == 2) {
			h = (1 - ((red - green) / (red - blue)));
		}else if (vmax[0] == 1 && vmin[0] == 2) {
			h = (1 + ((green - red) / (green - blue)));
		} else if (vmax[0] == 1 && vmin[0] == 0) {
			h = (3 - ((green - blue) / (green - red)));
		} else if (vmax[0] == 2 && vmin[0] == 0) {
			h = (3 + ((blue - green) / (blue - red)));
		} else if (vmax[0] == 2 && vmin[0] == 1) {
			h = (5 - ((blue - red) / (blue - green)));
		}
		h = h * 60;
		float[] hsv = new float[3];
		hsv[0] = h;
		hsv[1] = s;
		hsv[2] = v / 255;
		return hsv;
		/*
		 * v = max(R,G,B); // Value
s = (v-min(R,G,B)) / v // Saturation;
if(R=max && G=min) h=5+(R-B)/(R-G);
else if(R=max && B=min) h=1-(R-G)/(R-B);
else if(G=max && B=min) h=1+(G-R)/(G-B);
else if(G=max && R=min) h=3-(G-B)/(G-R);
else if(B=max && R=min) h=3+(B-G)/(B-R);
else if(B=max && G=min) h=5-(B-R)/(B-G);
h = h * 60;
		 */
	}
/**
 * algorithme utilisé sur http://www.easyrgb.com/index.php?X=MATH&H=21#text21
 * @param h parametre h du HSV
 * @param s parametre s du HSV
 * @param v parametre v du HSV
 * @return couleur en type RGB
 */
	public static Color HSVtoRGB(float h, float s, float v) {
		int r,g,b;	
		System.out.println("h: " + h + ", s: " +s + ", v: " + v);
		 r = g = b = 0;
		 h = h / 360;
		 if(s == 0){
			 r = (int) (v * 255);
			 g = (int) (v * 255);
			 b = (int) (v * 255);
		 }
		 else{
			 float tempH = h * 6;
			 // le H doit être plus petit que 1
			 if( tempH == 6){
				 tempH = 0;
			 }
			 int tempI =  (int) Math.floor(tempH);
			 float temp1 = (v * (1 - s));
			 float temp2 = (v * (1 - s * (tempH - tempI)));
			 float temp3 = (v * (1 - s * ( 1 - (tempH - tempI))));
			 float tempR, tempG,tempB = 0;
			 
			 if (tempI == 0){
				 tempR = v;
				 tempG = temp3;
				 tempB = temp1;
			 }
			 else if (tempI == 1){
				 tempR = temp2;
				 tempG =  v;
				 tempB = temp1;
			 }else if (tempI == 2){
				 tempR = temp1;
				 tempG =  v;
				 tempB = temp3;
			 }else if (tempI == 3){
				 tempR = temp1;
				 tempG = temp2;
				 tempB =  v;
			 }else if (tempI == 4){
				 tempR = temp3;
				 tempG = temp1;
				 tempB = v;
			 }else{
				 tempR = v;
				 tempG = temp1;
				 tempB = temp2;
			 }
			 r =  (int) (tempR * 255);
			 g = (int) (tempG * 255);
			 b = (int) (tempB * 255);
			 System.out.println("r: " + r + ", g: "+ g + ", b: " + b);
		 }
		
		Color rgb = new Color(r,g,b);

		return rgb;
	}
}
/*

 */

