package model;

import java.awt.Point;
import java.util.List;

public class BsplineCurveType extends CurveType {

	public BsplineCurveType(String name) {
		super(name);
	}
	
	/* (non-Javadoc)
	 * @see model.CurveType#getNumberOfSegments(int)
	 */
	public int getNumberOfSegments(int numberOfControlPoints) {
		if (numberOfControlPoints >= 4) {
			return (numberOfControlPoints -3);
		} else {
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see model.CurveType#getNumberOfControlPointsPerSegment()
	 */
	public int getNumberOfControlPointsPerSegment() {
		return 4;
	}

	/* (non-Javadoc)
	 * @see model.CurveType#getControlPoint(java.util.List, int, int)
	 */
	public ControlPoint getControlPoint(
		List controlPoints,
		int segmentNumber,
		int controlPointNumber) {
		int controlPointIndex = segmentNumber + controlPointNumber;
		return (ControlPoint)controlPoints.get(controlPointIndex);
	}


	@Override
	public Point evalCurveAt(List controlPoints, double t) {
		Point p1 = ((ControlPoint) controlPoints.get(0)).getCenter();
		Point p2 = ((ControlPoint) controlPoints.get(1)).getCenter();
		Point p3 = ((ControlPoint) controlPoints.get(2)).getCenter();
		Point p4 = ((ControlPoint) controlPoints.get(3)).getCenter();
		
		List tVector = Matrix.buildRowVector4(t*t*t, t*t, t, 1);
		List gVector = Matrix.buildColumnVector4(p1,p2,p3,p4);
		Point p = Matrix.eval(tVector, matrix, gVector);
		p.setLocation(p.getX()/6, p.getY()/6);
		return p;
	}

	private List hermiteMatrix = 
		Matrix.buildMatrix4(-1, 3,-3, 1, 
							 3,-6, 3, 0, 
							-3, 0, 3, 0, 
							 1, 4, 1, 0);
							 
	private List matrix = hermiteMatrix;
	
	

}
