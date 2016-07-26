package horasdecampo.models;

import com.j256.ormlite.dao.ForeignCollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cadnunsdev.PtBrUtils.HorasUtils;

public class Mes implements Serializable {

	private int mes;
	public List<Registro> getRegistros() {
		return registros;
	}

	public void setRegistros(ArrayList<Registro> registros) {
		this.registros = registros;
	}

	public int getMes() {
		return mes;
	}

	public int getAno() {
		return ano;
	}

	private int ano;
	private List<Registro> registros;
	private int quant;

	public Mes(int mes, int ano, List<Registro> registros) {
		this(mes, ano);
		this.registros = registros;
	}

	public Mes() {
		
	}

	public Mes(int mes, int ano) {
		this.mes = mes;
		// TODO Auto-generated constructor stub
		this.ano = ano;
	}

	@Override
	public String toString() {
		double hs = sumHoras();
		return String.format("%02d/%04d  = %s",mes, ano, HorasUtils.doubleHorasToString(hs)); 
	}

	private double sumHoras() {
		double hs = 0;		
		for (Registro registro : registros) {
			hs += registro.getHoras();
		}		
		return hs;
	}

	public void setQuantRegistros(int quant) {
		this.quant = quant;
	}
	public int getQuantRegistros() {
		return quant;
	}

	public String getTotalHoras() {
		return HorasUtils.doubleHorasToString(sumHoras());
	}

	public Integer getTotalVideos() {
		int videos = 0;
		for (Registro registro: registros) {
			videos += registro.getVideoExibidos();
		}
		return videos;
	}

	public Integer somarPublicacoes() {
		int count = 0;
		for (Registro registro: registros) {
			count += (registro.getPublicacoes() + registro.getRevistas() + registro.getFolhetos());
		}
		return count;
	}

	public Integer somarRevisitas() {
		int count = 0;
		for (Registro registro: registros) {
			count += registro.getRevisitas();
		}
		return count;
	}

	public Integer somarEstudosDirigidos() {
		ArrayList<Estudante> lista = new ArrayList<>();
		for (Registro registro: registros) {
			ForeignCollection<RevisitaEstudante> revisitaEstudantes  = registro.getEstudosDirigidos();
			for (RevisitaEstudante revisitaEstudante: revisitaEstudantes){
				Estudante estudante = revisitaEstudante.getEstudante();
				if(!lista.contains(estudante))
					lista.add(estudante);
			}
		}
		return lista.size();
	}
}
