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

	public static float[] RGBtoHSV(Color rgb) {

		// v est le max de la valeur R, G ou B, initié à 0
		int[] vmax = new int[2];   //vmax[0] = couleur (0 = r ,  1 = g,  2 = b) , vmax[1] = valeur
		int[] vmin = new int[2];   //vmax[0] = couleur (0 = r ,  1 = g,  2 = b) , vmax[1] = valeur
		float h, s, v;
		int red, green, blue;
		red = rgb.getRed();
		green = rgb.getGreen();
		blue = rgb.getBlue();

		// le [1] contient la valeur du max et du min
		vmax[1] = Math.max(red, Math.max(green, blue));
		vmin[1] = Math.min(red, Math.min(green, blue));
		
		v = vmax[1];
		s = v - vmin[1];
		
		//on valide ensuite si les valeur min et max représentent le Rouge, Vert, ou Bleu
		//couleur max est rouge
		if(vmax[1] == red){
			vmax[0] = 0;
		}
		//couleur max est blue
		else if(vmax[1] == blue){
			vmax[0] = 1;
		}
		//couleur max est vert
		else{
			vmax[0] = 2;
		}
		//couleur min est rouge
		if(vmin[1] == red){
			vmin[0] = 0;
		}
		//couleur max est blue
		else if(vmin[1] == blue){
			vmin[0] = 1;
		}
		//couleur max est vert
		else{
			vmin[0] = 2;
		}	
		
		// on calcule ensuite le s
		h = 0;
		if(vmax[0] == 0 && vmin[0] == 2){
			h = ( 5 + (red - blue) / (red - green));
		}else if(vmax[0] == 1 && vmin[0] == 2){
			h = ( 1 + (green - red) / (green - blue));
		}else if(vmax[0] == 1 && vmin[0] == 0){
			h = ( 3 - (green - blue) / (green - red));
		}else if(vmax[0] == 2 && vmin[0] == 0){
			h = ( 3 + (blue - green) / (blue - red));
		}else if(vmax[0] == 2 && vmin[0] == 1){
			h = ( 5 - (blue - red) / (blue - green));
		}
		h = h * 60;
		float[] hsv = new float[3];
		hsv[0] = h;
		hsv[1] = s;
		hsv[2] = v;
		return hsv;
	}

	public static Color HSVtoRGB(Color HSV) {
		return null;
	}
}

/*
 * v = max(R,G,B); Value s = (v-min(R,G,B)) Saturation; if(R=max && * G=min)
 * h=5+(R-B)/(R-G); 
 * else if(R=max && B=min) h=1-(R-G)/(R-B); 
 * else if(G=max && B=min) h=1+(G-R)/(G-B); 
 * else if(G=max && R=min) h=3-(G-B)/(G-R); 
 * else if(B=max && R=min) h=3+(B-G)/(B-R); 
 * else if(B=max && G=min) h=5-(B-R)/(B-G);
 * h = h * 60;
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * void RGBtoHSV( float r, float g, float b, float *h, float *s, float *v ) {
 * float min, max, delta; min = MIN( r, g, b ); max = MAX( r, g, b );v = max; //
 * v delta = max - min; if( max != 0 )s = delta / max; // s else { // r = g = b
 * = 0 // s = 0, v is undefineds = 0;h = -1; return; } if( r == max )h = ( g - b
 * ) / delta; // between yellow & magenta else if( g == max )h = 2 + ( b - r ) /
 * delta; // between cyan & yellow elseh = 4 + ( r - g ) / delta; // between
 * magenta & cyanh *= 60; // degrees if( *h < 0 )h += 360; }
 */
