package nl.home.freddy.afvalkalender;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelAfvalkalender {

	@JsonProperty("afvalstroom_id")
	Long afvalstroomId;
	@JsonFormat(pattern="yyyy-MM-dd")
	Date ophaaldatum;


	public Long getAfvalstroomId() {
		return afvalstroomId;
	}
	public void setAfvalstroomId(Long afvalstroomId) {
		this.afvalstroomId = afvalstroomId;
	}
	public Date getOphaaldatum() {
		return ophaaldatum;
	}
	public void setOphaaldatum(Date ophaaldatum) {
		this.ophaaldatum = ophaaldatum;
	}

	@Override
	public String toString() {
		return "ModelAfvalkalender [afvalstroomId=" + afvalstroomId + ", ophaaldatum=" + ophaaldatum + "]";
	}

}
