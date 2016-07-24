package cadnunsdev.PtBrUtils;

import java.security.InvalidParameterException;

public class PtBrUtils {
	public static String getMesStringPtBr(int mes){
		switch (mes) {
		case 1:
			return "Janeiro";
		case 2:
			return "Janeiro";
		case 3:
			return "Março";
		case 4:
			return "Abril";
		case 5:
			return "Maio";
		case 6:
			return "Junho";
		case 7:
			return "Julho";
		case 8:
			return "Agosto";
		case 9:
			return "Setembro";
		case 10:
			return "Outubro";
		case 11:
			return "Novembro";
		case 12:
			return "Dezembro";
		default:
			throw new InvalidParameterException("parametro mes deve estar entre 1 e 12");
		}
	}
}
