package model;

import java.awt.Color;

/***
 * classe proposant des méthodes statiques pour la conversion entre les différents types de couleur
 * @author Marc
 *
 */
public class Conversion{
	
	public static Color RGBtoHSV(Color rgb){
		
		
		Color hsv = new Color(0, 0, 0);
		return hsv;
	}
	public static Color HSVtoRGB(Color HSV){
		return null;
	}
}




/* sample
 * 
 * void RGBtoHSV( float r, float g, float b, float *h, float *s, float *v )
{
	float min, max, delta;
	min = MIN( r, g, b );
	max = MAX( r, g, b );
	*v = max;				// v
	delta = max - min;
	if( max != 0 )
		*s = delta / max;		// s
	else {
		// r = g = b = 0		// s = 0, v is undefined
		*s = 0;
		*h = -1;
		return;
	}
	if( r == max )
		*h = ( g - b ) / delta;		// between yellow & magenta
	else if( g == max )
		*h = 2 + ( b - r ) / delta;	// between cyan & yellow
	else
		*h = 4 + ( r - g ) / delta;	// between magenta & cyan
	*h *= 60;				// degrees
	if( *h < 0 )
		*h += 360;
}
 */
