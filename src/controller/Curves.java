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
	//http://www.cs.helsinki.fi/group/goa/mallinnus/curves/curves.html
	public void alignControlPoint() {
		
		if (curve != null) {
			System.out.println("OUÉ");
			Document doc = Application.getInstance().getActiveDocument();
			List selectedObjects = doc.getSelectedObjects(); 
			
			if (selectedObjects.size() > 0){
				Shape s = (Shape)selectedObjects.get(0);
				if (curve.getShapes().contains(s)){
					int controlPointIndex = curve.getShapes().indexOf(s);

					if(controlPointIndex == 0){
						System.out.println("Impossible d'aligner un point de départ");
						return;
					}
					if(controlPointIndex >=3 ){
						System.out.println("Impossible d'aligner un point de fin");
						return;
					}
									
					Point precedent = ((ControlPoint) curve.getShapes().get(controlPointIndex-1)).getCenter();
					Point suivant = ((ControlPoint) curve.getShapes().get(controlPointIndex+1)).getCenter();
					Point courant = ((ControlPoint) curve.getShapes().get(controlPointIndex)).getCenter();
					
					
					Point r1 = new Point((int)(courant.getX()-precedent.getX()),(int)(courant.getY()-precedent.getY()));
					Point r4 = new Point((int)(courant.getX()-suivant.getX()),(int)(courant.getY()-suivant.getY()));
					
					double distancePrecedent = Math.sqrt(Math.pow(r1.getX(), 2) + Math.pow(r1.getY(), 2));
					double distanceSuivant = Math.sqrt(Math.pow(r4.getX(), 2) + Math.pow(r4.getY(), 2));
					
					double distanceRatio = distanceSuivant/distancePrecedent;
					
					Point nouveauPoint = new Point((int)(courant.getX()+r1.getX()*distanceRatio), (int)(courant.getY()+r1.getY()*distanceRatio));
					suivant.setLocation(nouveauPoint);
					
					curve.update();
																
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
					
					if(controlPointIndex == 0){
						System.out.println("Impossible d'aligner un point de départ");
						return;
					}
					if(controlPointIndex >=3 ){
						System.out.println("Impossible d'aligner un point de fin");
						return;
					}
		
					Point precedent = ((ControlPoint) curve.getShapes().get(controlPointIndex-1)).getCenter();
					Point suivant = ((ControlPoint) curve.getShapes().get(controlPointIndex+1)).getCenter();
					Point courant = ((ControlPoint) curve.getShapes().get(controlPointIndex)).getCenter();
					
					
					Point distance = new Point((int)(courant.getX()-precedent.getX()),(int)(courant.getY()-precedent.getY()));
					
					Point nouveauPoint = new Point((int)(courant.getX()+distance.getX()), (int) (courant.getY()+distance.getY()));
					
					suivant.setLocation(nouveauPoint);
					curve.update();
					
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
