package model;

import java.awt.Point;
import java.util.List;

public class HermiteCurveType extends CurveType {

	public HermiteCurveType(String name) {
		super(name);
	}
	
	/* (non-Javadoc)
	 * @see model.CurveType#getNumberOfSegments(int)
	 */
	public int getNumberOfSegments(int numberOfControlPoints) {
		if (numberOfControlPoints >= 4) {
			return (numberOfControlPoints - 1) / 3;
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
		int controlPointIndex = segmentNumber * 3 + controlPointNumber;
		return (ControlPoint)controlPoints.get(controlPointIndex);
	}


	@Override
	public Point evalCurveAt(List controlPoints, double t) {
		Point p1 = ((ControlPoint) controlPoints.get(0)).getCenter();
		Point p2 = ((ControlPoint) controlPoints.get(1)).getCenter();
		Point p3 = ((ControlPoint) controlPoints.get(2)).getCenter();
		Point p4 = ((ControlPoint) controlPoints.get(3)).getCenter();
		
		Point r1 = new Point((int)(p2.getX()-p1.getX()),(int)(p2.getY()-p1.getY()));
		Point r4 = new Point((int)(p4.getX()-p3.getX()),(int)(p4.getY()-p3.getY()));
		List tVector = Matrix.buildRowVector4(t*t*t, t*t, t, 1);
		List gVector = Matrix.buildColumnVector4(p1,p4,r1,r4);
		Point p = Matrix.eval(tVector, matrix, gVector);
		return p;
	}

	private List hermiteMatrix = 
		Matrix.buildMatrix4( 2, -2, -1, 1, 
							-3,  3, -2,-1, 
							 0,  0,  1, 0, 
							 1,  0,  0, 0);
							 
	private List matrix = hermiteMatrix;
	
	

}
