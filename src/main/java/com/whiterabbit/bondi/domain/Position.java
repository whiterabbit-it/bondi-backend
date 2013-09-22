package com.whiterabbit.bondi.domain;

import java.io.Serializable;
import java.util.Date;

public class Position implements Serializable {

	private double longitude;

	private double latitude;

	private Date timestamp;

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
