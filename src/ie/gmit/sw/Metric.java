package ie.gmit.sw;

public class Metric {
	//declare variables
	private int inDegree;
	private int outDegree;
	private String Name;
	
	//getters and setters
	public int getInDegree() {
		return inDegree;
	}
	
	public void setInDegree(int inDegree) {
		this.inDegree = inDegree;
	}
	
	public int getOutDegree() {
		return outDegree;
	}
	
	public void setOutDegree(int outDegree) {
		this.outDegree = outDegree;
	}
	
	public String getName() {
		return Name;
	}
	
	public void setName(String name) {
		this.Name = name;
	}
	
	//get the stability
	public float getStability(){
		float stability =1f;
		
		//   ce
		// --------
		// ca + ce
		
		//stop divide by zero errors
		if(getOutDegree() > 0) {
			stability = (getOutDegree() / ((float)getInDegree() + (float)getOutDegree()));
		}else {
			stability=0f;
		}
			return stability;
		}
}
