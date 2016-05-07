package it.polimi.gis.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Marker {

	private Integer markerId;
	private Double lat;
	private Double lng;
	private String message;
	
	public Marker()
	{
		
	}
	
	public Integer getMarkerId() {
		return markerId;
	}
	public void setMarkerId(Integer markerId) {
		this.markerId = markerId;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
