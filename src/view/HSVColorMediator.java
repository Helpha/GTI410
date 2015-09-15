package view;

import java.awt.Color;
import java.awt.image.BufferedImage;

import model.ObserverIF;
import model.Pixel;

/***
 * Classe permettant de calculer les sliders pour HSV
 * @author SuperFacile
 *
 */


/*
 * 
 * v = max(R,G,B); // Value
s = (v-min(R,G,B)) / v // Saturation;
if(R=max && G=min) h=5+(R-B)/(R-G);
else if(R=max && B=min) h=1-(R-G)/(R-B);
else if(G=max && B=min) h=1+(G-R)/(G-B);
else if(G=max && R=min) h=3-(G-B)/(G-R);
else if(B=max && R=min) h=3+(B-G)/(B-R);
else if(B=max && G=min) h=5-(B-R)/(B-G);
h = h * 60;
if(h < 0 ) h += 360; // (assume h entre 0 et 360)
 * 
 */

/*
 * Color c = Color.getHSBColor(hue, saturation, value);
   String rgb = Integer.toHexString(c.getRGB());
 */

/*
 * exemple
 * http://colorizer.org/
 */
public class HSVColorMediator extends Object implements SliderObserver, ObserverIF{
		
	public Color returnTest(){
		Color c = Color.getHSBColor((float)0.25,(float) 0.9,(float) 0.9);
		return c;
	}
	
	public void setHue(float hue){
		this.hue = hue;
	}
	public void setSaturation(float saturation){
		this.saturation = saturation;
	}
	public void setValue(float value){
		this.value = value;
	}
	
	
	ColorSlider hueCS;
	ColorSlider saturationCS;
	ColorSlider valueCS;
	float hue;
	float saturation;
	float value;
	BufferedImage hueImage;
	BufferedImage saturationImage;
	BufferedImage valueImage;
	int imagesWidth;
	int imagesHeight;
	ColorDialogResult result;
	
	public HSVColorMediator(ColorDialogResult result, int imagesWidth, int imagesHeight) {
		this.imagesWidth = imagesWidth;
		this.imagesHeight = imagesHeight;
	
	}
		
		/**
		 * @param slider
		 */
		public void setHueCS(ColorSlider slider) {
			hueCS = slider;
			
			slider.addObserver(this);
		}
	
		/**
		 * @param slider
		 */
		public void setSaturationCS(ColorSlider slider) {
			saturationCS = slider;
			slider.addObserver(this);
		}
	
		/**
		 * @param slider
		 */
		public void setValueCS(ColorSlider slider) {
			valueCS = slider;
			slider.addObserver(this);
		}

		@Override
		public void update(ColorSlider cs, int v) {
			boolean updateHue = false;
			boolean updateSaturation = false;
			boolean updateValue = false;
			if (cs == hueCS && v != hue) {
				hue = v;
				updateSaturation = true;
				updateValue = true;
			}
			if (cs == saturationCS && v != saturation) {
				saturation = v;
				updateHue = true;
				updateValue = true;
			}
			if (cs == valueCS && v != value) {
				value = v;
				updateHue = true;
				updateSaturation = true;
			}
			if (updateHue) {
				//computeHueImage(hue, saturation, value);
			}
			if (updateSaturation) {
				//computeSaturationImage(hue, saturation, value);
			}
			if (updateValue) {
				//computeValueImage(hue, saturation, value);
			}
			
			//Pixel pixel = new Pixel(red, green, blue, 255);
			//result.setPixel(pixel);
		}

		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}


//		
//		public void computeHueImage(float hue, float saturation, float value) { 
//			Pixel p = new Pixel(red, green, blue, 255); 
//			for (int i = 0; i<imagesWidth; ++i) {
//				p.setRed((int)(((double)i / (double)imagesWidth)*255.0)); 
//				int rgb = p.getARGB();
//				for (int j = 0; j<imagesHeight; ++j) {
//					redImage.setRGB(i, j, rgb);
//				}
//			}
//			if (redCS != null) {
//				redCS.update(redImage);
//			}
//		}
//		
//		public void computeSaturationImage(float hue, float saturation, float value) {
//			Pixel p = new Pixel(red, green, blue, 255); 
//			for (int i = 0; i<imagesWidth; ++i) {
//				p.setGreen((int)(((double)i / (double)imagesWidth)*255.0)); 
//				int rgb = p.getARGB();
//				for (int j = 0; j<imagesHeight; ++j) {
//					greenImage.setRGB(i, j, rgb);
//				}
//			}
//			if (greenCS != null) {
//				greenCS.update(greenImage);
//			}
//		}
//		
//		public void computeValueImage(float hue, float saturation, float value) { 
//			Pixel p = new Pixel(red, green, blue, 255); 
//			for (int i = 0; i<imagesWidth; ++i) {
//				p.setBlue((int)(((double)i / (double)imagesWidth)*255.0)); 
//				int rgb = p.getARGB();
//				for (int j = 0; j<imagesHeight; ++j) {
//					blueImage.setRGB(i, j, rgb);
//				}
//			}
//			if (blueCS != null) {
//				blueCS.update(blueImage);
//			}
//		}
		
		
		//conversion to do
//		
//		this.red = result.getPixel().();
//		this.green = result.getPixel().getGreen();
//		this.blue = result.getPixel().getBlue();
//		this.result = result;
//		result.addObserver(this);
//		
//		redImage = new BufferedImage(imagesWidth, imagesHeight, BufferedImage.TYPE_INT_ARGB);
//		greenImage = new BufferedImage(imagesWidth, imagesHeight, BufferedImage.TYPE_INT_ARGB);
//		blueImage = new BufferedImage(imagesWidth, imagesHeight, BufferedImage.TYPE_INT_ARGB);
//		computeRedImage(red, green, blue);
//		computeGreenImage(red, green, blue);
//		computeBlueImage(red, green, blue); 	
//	}
//	/**
//	 * @return
//	 */
//	public BufferedImage getBlueImage() {
//		return blueImage;
//	}
//
//	/**
//	 * @return
//	 */
//	public BufferedImage getGreenImage() {
//		return greenImage;
//	}
//
//	/**
//	 * @return
//	 */
//	public BufferedImage getRedImage() {
//		return redImage;
//	}
//

//	/**
//	 * @return
//	 */
//	public double getBlue() {
//		return blue;
//	}
//
//	/**
//	 * @return
//	 */
//	public double getGreen() {
//		return green;
//	}
//
//	/**
//	 * @return
//	 */
//	public double getRed() {
//		return red;
//	}
//
//
//	/* (non-Javadoc)
//	 * @see model.ObserverIF#update()
//	 */
//	public void update() {
//		// When updated with the new "result" color, if the "currentColor"
//		// is aready properly set, there is no need to recompute the images.
//		Pixel currentColor = new Pixel(red, green, blue, 255);
//		if(currentColor.getARGB() == result.getPixel().getARGB()) return;
//		
//		red = result.getPixel().getRed();
//		green = result.getPixel().getGreen();
//		blue = result.getPixel().getBlue();
//		
//		redCS.setValue(red);
//		greenCS.setValue(green);
//		blueCS.setValue(blue);
//		computeRedImage(red, green, blue);
//		computeGreenImage(red, green, blue);
//		computeBlueImage(red, green, blue);
//		
//		// Efficiency issue: When the color is adjusted on a tab in the 
//		// user interface, the sliders color of the other tabs are recomputed,
//		// even though they are invisible. For an increased efficiency, the 
//		// other tabs (mediators) should be notified when there is a tab 
//		// change in the user interface. This solution was not implemented
//		// here since it would increase the complexity of the code, making it
//		// harder to understand.
//	}
//
//	
//	
//	
	
}
