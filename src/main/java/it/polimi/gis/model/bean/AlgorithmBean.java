package it.polimi.gis.model.bean;

public class AlgorithmBean {
	private AssociationItem[] associationList;
	
	private ParameterObject parameters;
	
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
}
