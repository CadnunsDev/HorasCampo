package cadnunsdev.PtBrUtils;

public class HorasUtils {
	public static String doubleHorasToString(double horas){
		double minutoDouble = (horas % 1);
		int hs = (int)(horas - minutoDouble);
		minutoDouble *=60;
		return String.format("%02dhs%02.0f", hs, minutoDouble);
	}
	
	public static double horasToDouble(int hora, int minutos){
		float ret = hora;
		double min = (minutos / 60.0);
		return ret + min;
	}
}
