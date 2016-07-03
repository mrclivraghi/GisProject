package it.polimi.gis.model.bean;

public class AlgorithmBean {
	private AssociationItem[] associationList;
	
	private ParameterObject parameters;
	
	private String layer1;
	
	private String layer2;
	
	public AssociationItem[] getAssociationList() {
		return associationList;
	}
	public void setAssociationList(AssociationItem[] associationList) {
		this.associationList = associationList;
	}
	public ParameterObject getParameters() {
		return parameters;
	}
	public void setParameters(ParameterObject parameters) {
		this.parameters = parameters;
	}
	public String getLayer2() {
		return layer2;
	}
	public void setLayer2(String layer2) {
		this.layer2 = layer2;
	}
	public String getLayer1() {
		return layer1;
	}
	public void setLayer1(String layer1) {
		this.layer1 = layer1;
	}
}
