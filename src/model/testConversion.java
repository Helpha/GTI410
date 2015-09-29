package model;

import java.awt.Color;

public class testConversion {

	public static void main(String[] args) {
		Pixel test = new Pixel(245,156,214);
		
		float[] testConverti = Conversion.RGBtoHSV(test);
		System.out.println("hsv: h: " + testConverti[0] + " s: " + testConverti[1] + " v: " + testConverti[2]);
		Pixel convertiBack = Conversion.HSVtoRGB(testConverti[0], testConverti[1], testConverti[2]);
		
		System.out.println(convertiBack.getRed() + " " + convertiBack.getGreen() + " " + convertiBack.getBlue());

		float[] testConvertiCMYK = Conversion.RGBtoCMYK(test);
		Pixel convertibackCMYK = Conversion.cmykToRgb(testConvertiCMYK[0], testConvertiCMYK[1], testConvertiCMYK[2], testConvertiCMYK[3]);
		System.out.println(convertibackCMYK.getRed() + " " + convertibackCMYK.getGreen() + " " + convertibackCMYK.getBlue());
	}

}
