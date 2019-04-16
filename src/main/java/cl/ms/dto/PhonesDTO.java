package cl.ms.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PhonesDTO {
	
	private String number;
	private String citycode;
	private String contrycode;
	
}
