package it.polimi.gis.model.bean;

public class ParameterObject {
	private Double angle;
	private Double sigma;
	private Double maxDistance;
	private Integer numIterations;
	public Double getAngle() {
		return angle;
	}
	public void setAngle(Double angle) {
		this.angle = angle;
	}
	public Double getSigma() {
		return sigma;
	}
	public void setSigma(Double sigma) {
		this.sigma = sigma;
	}
	public Double getMaxDistance() {
		return maxDistance;
	}
	public void setMaxDistance(Double maxDistance) {
		this.maxDistance = maxDistance;
	}
	public Integer getNumIterations() {
		return numIterations;
	}
	public void setNumIterations(Integer numIterations) {
		this.numIterations = numIterations;
	}
}
