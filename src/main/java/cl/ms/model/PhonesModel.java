package cl.ms.model;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="PHONE")
public class PhonesModel {

	private BigDecimal phoCod;
	
	private UUID usuCod;
	
	private String number;
	
	private String citycode;
	
	private String contrycode;
	
	@Id
	@SequenceGenerator(name="pho_seq", sequenceName="pho_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="pho_seq")
	@Column(name = "PHO_COD", unique=true, nullable=false, precision=12, scale=0)
	public BigDecimal getPhoCod() {
		return phoCod;
	}

	public void setPhoCod(BigDecimal phoCod) {
		this.phoCod = phoCod;
	}

	@Column(name = "USU_COD", precision=12, scale=0)
	public UUID getUsuCod() {
		return usuCod;
	}

	public void setUsuCod(UUID usuCod) {
		this.usuCod = usuCod;
	}


	@Column(name = "NUMBER")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	@Column(name = "CITY_CODE")
	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	@Column(name = "COUNTRY_CODE")
	public String getContrycode() {
		return contrycode;
	}

	public void setContrycode(String contrycode) {
		this.contrycode = contrycode;
	}

}
