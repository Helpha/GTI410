package view;

import java.awt.Color;
import java.awt.image.BufferedImage;

import model.Conversion;
import model.ObserverIF;
import model.Pixel;

/***
 * Classe permettant de calculer les sliders pour HSV
 * 
 * @author SuperFacile
 * exemple http://colorizer.org/
 */
public class CMYKColorMediator extends Object implements SliderObserver,
		ObserverIF {

	public Color returnTest() {
		Color c = Color.getHSBColor((float) 0.25, (float) 0.9, (float) 0.9);
		return c;
	}

	public void setCyan(float cyan) {
		this.cyan = cyan;
	}

	public void setMagenta(float magenta) {
		this.magenta = magenta;
	}

	public void setYellow(float yellow) {
		this.yellow = yellow;
	}
	
	public void setKey(float key) {
		this.key = key;
	}

	ColorSlider cyanCS;
	ColorSlider magentaCS;
	ColorSlider yellowCS;
	ColorSlider keyCS;
	float cyan;
	float magenta;
	float yellow;
	float key;
	BufferedImage cyanImage;
	BufferedImage magentaImage;
	BufferedImage yellowImage;
	BufferedImage keyImage;
	int imagesWidth;
	int imagesHeight;
	ColorDialogResult result;

	public CMYKColorMediator(ColorDialogResult result, int imagesWidth,
			int imagesHeight) {
		this.imagesWidth = imagesWidth;
		this.imagesHeight = imagesHeight;
		
		float recievedCMYK[] = Conversion.RGBtoCMYK(result.getPixel());
		
		
		this.cyan = recievedCMYK[0];
		this.magenta = recievedCMYK[1];
		this.yellow = recievedCMYK[2];
		this.key = recievedCMYK[3];
		this.result = result;
		result.addObserver(this);


		cyanImage = new BufferedImage(imagesWidth, imagesHeight, BufferedImage.TYPE_INT_ARGB);
		magentaImage = new BufferedImage(imagesWidth, imagesHeight, BufferedImage.TYPE_INT_ARGB);
		yellowImage = new BufferedImage(imagesWidth, imagesHeight, BufferedImage.TYPE_INT_ARGB);
		keyImage = new BufferedImage(imagesWidth, imagesHeight, BufferedImage.TYPE_INT_ARGB);
		computeCyanImage(cyan, magenta, yellow, key);
		computeMagentaImage(cyan, magenta, yellow, key);
		computeYellowImage(cyan, magenta, yellow, key);
		computeKeyImage(cyan, magenta, yellow, key);
	}

	/**
	 * @param slider
	 */
	public void setCyanCS(ColorSlider slider) {
		cyanCS = slider;
		slider.addObserver(this);
	}

	/**
	 * @param slider
	 */
	public void setMagentaCS(ColorSlider slider) {
		magentaCS = slider;
		slider.addObserver(this);
	}

	/**
	 * @param slider
	 */
	public void setYellowCS(ColorSlider slider) {
		yellowCS = slider;
		slider.addObserver(this);
	}

	/**
	 * @param slider
	 */
	public void setKeyCS(ColorSlider slider) {
		keyCS = slider;
		slider.addObserver(this);
	}
	
	
	
	@Override
	public void update(ColorSlider cs, int v) {
		
		boolean updateCyan = false;
		boolean updateMagenta = false;
		boolean updateYellow = false;
		boolean updateKey = false;
		
		if (cs == cyanCS && v != cyan) {
			cyan = ((float)v /(float) 255);
			updateYellow = true;
			updateMagenta = true;
			updateKey = true;
		}
		if (cs == magentaCS && v != magenta) {
			magenta = ((float)v /(float) 255);
			updateYellow = true;
			updateCyan = true;
			updateKey = true;
		}
		if (cs == yellowCS && v != yellow) {
			yellow = ((float)v /(float) 255);
			updateMagenta = true;
			updateCyan = true;
			updateKey = true;
		}
		
		if (cs == keyCS && v != key) {
			key = ((float)v /(float) 255);
			updateMagenta = true;
			updateCyan = true;
			updateYellow = true;
		}
				
		if (updateCyan) {
			computeCyanImage(cyan, magenta, yellow, key);
		}
		if (updateMagenta) {
			 computeMagentaImage(cyan, magenta, yellow, key);
		}
		if (updateYellow) {
			 computeYellowImage(cyan, magenta, yellow, key);
		}
		if (updateKey) {
			 computeKeyImage(cyan, magenta, yellow, key);
		}
		
		// Pixel pixel = new Pixel(red, green, blue, 255);
		// result.setPixel(pixel);
	}

	//
	public void computeCyanImage(float cyan, float magenta, float yellow, float key) {
		for (int i = 0; i < imagesWidth; ++i) {
			Pixel p = Conversion.cmykToRgb(((float)i/(float)imagesWidth), magenta, yellow, key);
			int rgb = p.getARGB();
			for (int j = 0; j < imagesHeight; ++j) {
				cyanImage.setRGB(i, j, rgb);
			}
		}
		if (cyanCS != null) {
			cyanCS.update(cyanImage);
		}
	}

	//
	public void computeMagentaImage(float cyan, float magenta, float yellow, float key) {
		
		for (int i = 0; i<imagesWidth; ++i) {
			Pixel p = Conversion.cmykToRgb(cyan, ((float)i/(float)imagesWidth), yellow, key);
			int rgb = p.getARGB();
			for (int j = 0; j<imagesHeight; ++j) {
				magentaImage.setRGB(i, j, rgb);
			}
		}
		if (magentaCS != null) {
			magentaCS.update(magentaImage);
		}
	}

	public void computeYellowImage(float cyan, float magenta, float yellow, float key) {		
		
		for (int i = 0; i<imagesWidth; ++i) {
			Pixel p = Conversion.cmykToRgb(cyan, magenta, ((float)i/(float)imagesWidth), key);
			int rgb = p.getARGB();
			for (int j = 0; j<imagesHeight; ++j) {
				yellowImage.setRGB(i, j, rgb);
			}
		}
		if (yellowCS != null) {
			yellowCS.update(yellowImage);
		}
	}
	
	
	public void computeKeyImage(float cyan, float magenta, float yellow, float key) {		
		
		for (int i = 0; i<imagesWidth; ++i) {
			Pixel p = Conversion.cmykToRgb(cyan, magenta, yellow, ((float)i/(float)imagesWidth));
			int rgb = p.getARGB();
			for (int j = 0; j<imagesHeight; ++j) {
				keyImage.setRGB(i, j, rgb);
			}
		}
		if (keyCS != null) {
			keyCS.update(keyImage);
		}
	
	}

		/**
		 * @return
		 */
		public BufferedImage getCyanImage() {
			return cyanImage;
		}

		/**
		 * @return
		 */
		public BufferedImage getMagentaImage() {
			return magentaImage;
		}

		/**
		 * @return
		 */
		public BufferedImage getYellowImage() {
			return yellowImage;
		}
		
		
		/**
		 * @return
		 */
		public BufferedImage getKeyImage() {
			return keyImage;
		}


		/**
		 * @return
		 */
		public double getCyan() {
			return cyan;
		}

		/**
		 * @return
		 */
		public double getMagenta() {
			return magenta;
		}

		/**
		 * @return
		 */
		public double getYellow() {
			return yellow;
		}

		/**
		 * @return
		 */
		public double getKey() {
			return key;
		}
		/* (non-Javadoc)
		 * @see model.ObserverIF#update()
		 */
		public void update() {
			// When updated with the new "result" color, if the "currentColor"
			// is aready properly set, there is no need to recompute the images.
			Pixel currentColor = Conversion.cmykToRgb(cyan, magenta, yellow, key);
			if(currentColor.getARGB() == result.getPixel().getARGB()) return;
						
			float recievedCymk[] = Conversion.RGBtoCMYK(result.getPixel());
			
			int cyan = (int)recievedCymk[0];
			int magenta = (int)recievedCymk[1];
			int yellow = (int)recievedCymk[2];
			int key = (int)recievedCymk[3];
			
			
			cyanCS.setValue(cyan);
			magentaCS.setValue(magenta);
			yellowCS.setValue(yellow);
			keyCS.setValue(key);
			computeCyanImage(cyan, magenta, yellow, key);
			computeMagentaImage(cyan, magenta, yellow, key);
			computeYellowImage(cyan, magenta, yellow, key);
			computeKeyImage(cyan, magenta, yellow, key);
			
			// Efficiency issue: When the color is adjusted on a tab in the 
			// user interface, the sliders color of the other tabs are recomputed,
			// even though they are invisible. For an increased efficiency, the 
			// other tabs (mediators) should be notified when there is a tab 
			// change in the user interface. This solution was not implemented
			// here since it would increase the complexity of the code, making it
			// harder to understand.
		}

}
