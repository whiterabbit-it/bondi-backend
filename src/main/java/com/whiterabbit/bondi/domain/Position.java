package com.whiterabbit.bondi.domain;

import java.io.Serializable;
import java.util.Date;

public class Position implements Serializable {

	private long longitude;

	private long latitude;

	private Date timestamp;

	public long getLongitude() {
		return longitude;
	}

	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}

	public long getLatitude() {
		return latitude;
	}

	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
