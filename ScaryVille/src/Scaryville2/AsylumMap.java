package Scaryville2;


public class AsylumMap {
	private Coordinate [][] coordinateArray = new Coordinate [18][18];

	public AsylumMap(Coordinate[][] coordinateArray) {
		this.coordinateArray = coordinateArray;
	} 
	

	public AsylumMap() {
		this.coordinateArray = this.coordinateArray;
	}


	public Coordinate [][] getCoordinateArray() {
		return coordinateArray;
	}

	public void setCoordinateArray(Coordinate [][] coordinateArray) {
		this.coordinateArray = coordinateArray;
	} 
}
