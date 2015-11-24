/*
   This file is part of j2dcg.
   j2dcg is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2 of the License, or
   (at your option) any later version.
   j2dcg is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   You should have received a copy of the GNU General Public License
   along with j2dcg; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.List;

import view.Application;
import view.CurvesPanel;
import model.BezierCurveType;
import model.BsplineCurveType;
import model.ControlPoint;
import model.Curve;
import model.CurvesModel;
import model.DocObserver;
import model.Document;
import model.HermiteCurveType;
import model.PolylineCurveType;
import model.Shape;

/**
 * <p>Title: Curves</p>
 * <p>Description: (AbstractTransformer)</p>
 * <p>Copyright: Copyright (c) 2004 Sébastien Bois, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.9 $
 */
public class Curves extends AbstractTransformer implements DocObserver {
		
	/**
	 * Default constructor
	 */
	public Curves() {
		Application.getInstance().getActiveDocument().addObserver(this);
	}	

	/* (non-Javadoc)
	 * @see controller.AbstractTransformer#getID()
	 */
	public int getID() { return ID_CURVES; }
	
	public void activate() {
		firstPoint = true;
		Document doc = Application.getInstance().getActiveDocument();
		List selectedObjects = doc.getSelectedObjects();
		boolean selectionIsACurve = false; 
		if (selectedObjects.size() > 0){
			Shape s = (Shape)selectedObjects.get(0);
			if (s instanceof Curve){
				curve = (Curve)s;
				firstPoint = false;
				cp.setCurveType(curve.getCurveType());
				cp.setNumberOfSections(curve.getNumberOfSections());
			}
			else if (s instanceof ControlPoint){
				curve = (Curve)s.getContainer();
				firstPoint = false;
			}
		}
		
		if (firstPoint) {
			// First point means that we will have the first point of a new curve.
			// That new curve has to be constructed.
			curve = new Curve(100,100);
			setCurveType(cp.getCurveType());
			setNumberOfSections(cp.getNumberOfSections());
		}
	}
    
	/**
	 * 
	 */
	protected boolean mouseReleased(MouseEvent e){
		int mouseX = e.getX();
		int mouseY = e.getY();

		if (firstPoint) {
			firstPoint = false;
			Document doc = Application.getInstance().getActiveDocument();
			doc.addObject(curve);
		}
		ControlPoint cp = new ControlPoint(mouseX, mouseY);
		curve.addPoint(cp);
				
		return true;
	}

	/**
	 * @param string
	 */
	public void setCurveType(String string) {
		if (string == CurvesModel.BEZIER) {
			curve.setCurveType(new BezierCurveType(CurvesModel.BEZIER));
			
		} else if (string == CurvesModel.LINEAR) {
			curve.setCurveType(new PolylineCurveType(CurvesModel.LINEAR));
		} 
		
		 else if (string == CurvesModel.BSPLINE) {
		curve.setCurveType(new BsplineCurveType(CurvesModel.BSPLINE));
		} 
		
		else if (string == CurvesModel.HERMITE) {
			curve.setCurveType(new HermiteCurveType(CurvesModel.HERMITE));
		
		}else {
			System.out.println("Curve type [" + string + "] is unknown.");
		}
	}
	
	public void alignControlPoint() {
		if (curve != null) {
			System.out.println("OUÉ");
			Document doc = Application.getInstance().getActiveDocument();
			List selectedObjects = doc.getSelectedObjects(); 
			if (selectedObjects.size() > 0){
				Shape s = (Shape)selectedObjects.get(0);
				if (curve.getShapes().contains(s)){
					int controlPointIndex = curve.getShapes().indexOf(s);
					//S'il n'est pas P2 il est définitivement P3
					boolean estP2 = true;
					if(controlPointIndex == 0){
						System.out.println("Impossible d'aligner un point de départ");
						return;
					}
					if(controlPointIndex >=3 ){
						System.out.println("Impossible d'aligner un point de fin");
						return;
					}
					
					if(controlPointIndex == 2 ){
						estP2 = false;				
					}
									
					Point precedent = ((ControlPoint) curve.getShapes().get(controlPointIndex-1)).getCenter();
					Point suivant = ((ControlPoint) curve.getShapes().get(controlPointIndex+1)).getCenter();
					Point courant = ((ControlPoint) curve.getShapes().get(controlPointIndex)).getCenter();
					
//					Point r1 =  new Point((int)(courant.getX()-precedent.getX()), (int)(courant.getY()-precedent.getY()));
//					Point r4 = new Point((int)(suivant.getX()-courant.getX()), (int)(suivant.getY()-courant.getY()));
					
					Point p1 = ((ControlPoint) curve.getShapes().get(0)).getCenter();
					Point p2 = ((ControlPoint) curve.getShapes().get(1)).getCenter();
					Point p3 = ((ControlPoint) curve.getShapes().get(2)).getCenter();
					Point p4 = ((ControlPoint) curve.getShapes().get(3)).getCenter();
					
					Point r1 = new Point((int)(p2.getX()-p1.getX()),(int)(p2.getY()-p1.getY()));
					Point r4 = new Point((int)(p4.getX()-p3.getX()),(int)(p4.getY()-p3.getY()));
					
					//http://stackoverflow.com/questions/9970281/java-calculating-the-angle-between-two-points-in-degrees
					   double angle = (double) Math.toDegrees(Math.atan2(r4.getY() - r1.getY(), r4.getX() - r1.getX()));
					   
					   if(angle < 0){
					       angle += 360;
					   }
					   System.out.println(angle);
					    
					   double angleComplementaire = 360 - angle;
					   System.out.println(angleComplementaire);
					   
					   //Si le point est P2, il faut lui appliquer une rotation de angleComplementaire par rapport a P1, réduisant l'écart a 0 degré
					   double defaultX = 0;
					   double defaultY = 0;
					   double complementaireEnRadian = Math.toRadians(angleComplementaire);
					   if(estP2)
					   {
						    defaultX = courant.getX() - precedent.getX();
						    defaultY = courant.getY() - precedent.getY();
							double nouveauX = defaultX * Math.cos(complementaireEnRadian) - defaultY * Math.sin(complementaireEnRadian);
							double nouveauY = defaultX * Math.sin(complementaireEnRadian) + defaultY * Math.cos(complementaireEnRadian);
							courant.setLocation(nouveauX + precedent.getX() , nouveauY + precedent.getY());
						   						   						  						   					   
					   } else					   
					   {
						    defaultX = courant.getX() - suivant.getX();
						    defaultY = courant.getY() - suivant.getY();
							double nouveauX = defaultX * Math.cos(complementaireEnRadian) - defaultY * Math.sin(complementaireEnRadian);
							double nouveauY = defaultX * Math.sin(complementaireEnRadian) + defaultY * Math.cos(complementaireEnRadian);
							courant.setLocation(nouveauX + suivant.getX() , nouveauY + suivant.getY());
					   }
					   


//						double nouveauX = defaultX * Math.cos(complementaireEnRadian) - defaultY * Math.sin(complementaireEnRadian);
//						double nouveauY = defaultX * Math.sin(complementaireEnRadian) + defaultY * Math.cos(complementaireEnRadian);
//						courant.setLocation(nouveauX + courant.getX() , nouveauY + courant.getY());
						curve.update();
						((ControlPoint) curve.getShapes().get(controlPointIndex + 1)).setCenter(suivant);
						
						
						 p1 = ((ControlPoint) curve.getShapes().get(0)).getCenter();
						 p2 = ((ControlPoint) curve.getShapes().get(1)).getCenter();
						 p3 = ((ControlPoint) curve.getShapes().get(2)).getCenter();
						 p4 = ((ControlPoint) curve.getShapes().get(3)).getCenter();
						
						 r1 = new Point((int)(p2.getX()-p1.getX()),(int)(p2.getY()-p1.getY()));
						 r4 = new Point((int)(p4.getX()-p3.getX()),(int)(p4.getY()-p3.getY()));
						
						//http://stackoverflow.com/questions/9970281/java-calculating-the-angle-between-two-points-in-degrees
						   angle = (double) Math.toDegrees(Math.atan2(r4.getY() - r1.getY(), r4.getX() - r1.getX()));
						   
						   System.out.println("Angle2" + angle);
						
						
					System.out.println("Try to apply G1 continuity on control point [" + controlPointIndex + "]");
				}
			}
			
		}
	}
	
	public void symetricControlPoint() {
		if (curve != null) {
			Document doc = Application.getInstance().getActiveDocument();
			List selectedObjects = doc.getSelectedObjects(); 
			if (selectedObjects.size() > 0){
				Shape s = (Shape)selectedObjects.get(0);
				if (curve.getShapes().contains(s)){
					int controlPointIndex = curve.getShapes().indexOf(s);
					System.out.println("Try to apply C1 continuity on control point [" + controlPointIndex + "]");
				}
			}
			
		}
	}

	public void setNumberOfSections(int n) {
		curve.setNumberOfSections(n);
	}
	
	public int getNumberOfSections() {
		if (curve != null)
			return curve.getNumberOfSections();
		else
			return Curve.DEFAULT_NUMBER_OF_SECTIONS;
	}
	
	public void setCurvesPanel(CurvesPanel cp) {
		this.cp = cp;
	}
	
	/* (non-Javadoc)
	 * @see model.DocObserver#docChanged()
	 */
	public void docChanged() {
	}

	/* (non-Javadoc)
	 * @see model.DocObserver#docSelectionChanged()
	 */
	public void docSelectionChanged() {
		activate();
	}

	private boolean firstPoint = false;
	private Curve curve;
	private CurvesPanel cp;
}
