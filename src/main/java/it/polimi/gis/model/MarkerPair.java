package it.polimi.gis.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarkerPair {
	private Integer markerPairId;
	
	private Marker marker1;
	private Marker marker2;
	
	public Integer getMarkerPairId() {
		return markerPairId;
	}
	public void setMarkerPairId(Integer markerPairId) {
		this.markerPairId = markerPairId;
	}
	public Marker getMarker1() {
		return marker1;
	}
	public void setMarker1(Marker marker1) {
		this.marker1 = marker1;
	}
	public Marker getMarker2() {
		return marker2;
	}
	public void setMarker2(Marker marker2) {
		this.marker2 = marker2;
	}

}
