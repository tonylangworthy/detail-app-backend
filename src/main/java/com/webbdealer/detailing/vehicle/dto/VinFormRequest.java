package com.webbdealer.detailing.vehicle.dto;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Component
public class VinFormRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Please provide a VIN.")
    @Size(min = 17, max = 17, message = "VIN must be exactly 17 characters.")
    private String vin;

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vin == null) ? 0 : vin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VinFormRequest other = (VinFormRequest) obj;
		if (vin == null) {
			if (other.vin != null)
				return false;
		} else if (!vin.equals(other.vin))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VinFormRequest [vin=" + vin + "]";
	}
	
	
}
