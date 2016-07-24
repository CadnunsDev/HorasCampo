package horasdecampo.models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import cadnunsdev.PtBrUtils.HorasUtils;

public class Registro implements Serializable {
	//campos novos e remanescentes
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private Date data;
	@DatabaseField
	private double horas;
	@DatabaseField
	private int publicacoes;
	@DatabaseField
	private int videoExibidos;
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getPublicacoes() {
		return publicacoes;
	}

	public void setPublicacoes(int publicacoes) {
		this.publicacoes = publicacoes;
	}

	public int getVideoExibidos() {
		return videoExibidos;
	}

	public void setVideoExibidos(int videoExibidos) {
		this.videoExibidos = videoExibidos;
	}

	public int getRevisitas() {
		return revisitas;
	}

	public void setRevisitas(int revisitas) {
		this.revisitas = revisitas;
	}

	public ForeignCollection<RevisitaEstudante> getEstudosDirigidos() {
		return estudosDirigidos;
	}

	public void setEstudosDirigidos(
			ForeignCollection<RevisitaEstudante> listaEstudos) {
		
		estudosDirigidos = listaEstudos;
		
	}

	public int getLivros() {
		return livros;
	}

	public void setLivros(int livros) {
		this.livros = livros;
	}

	public int getFolhetos() {
		return Folhetos;
	}

	public void setFolhetos(int folhetos) {
		Folhetos = folhetos;
	}

	public void setHoras(int horas, int min) {
		this.horas = HorasUtils.horasToDouble(horas, min);
	}

	public void setRevistas(int revistas) {
		this.revistas = revistas;
	}

	@DatabaseField
	private int revistas;
	@DatabaseField
	private int revisitas;
	@ForeignCollectionField
	private ForeignCollection<RevisitaEstudante> estudosDirigidos;
	
	//campos marcados para extincao
	private int livros;
	private int Folhetos;
	
	public Registro(){
		data = Calendar.getInstance().getTime();
	}
	
	public Registro(double horas,int revistas) {
		this(horas);
		this.revistas = revistas;		
	}
	
	public Registro(double horas) {
		this();
		this.horas = horas;
	}

	public void setId(int newId) {
		id = newId;
	}
	
	public double getId() {
		return id;
	}
	public double getHoras() {
		return horas;
	}	
	
	public String getDataCadastroString(String dateformat){
		DateFormat dateFormat = new SimpleDateFormat(dateformat);
		return dateFormat.format(data.getTime());
	}
	public String getDataCadastroString(){		
		return getDataCadastroString("dd/MM/yyyy HH:mm:ss");
	}
	
	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		if (horas > 0) {			
			ret.append(HorasUtils.doubleHorasToString(horas));
		}
		return ret.toString();
	}

	public int getSomenteHoras() {
		double minutoDouble = (horas % 1);
		int hs = (int)(horas - minutoDouble);
		return hs;
	}

	public double getSomenteMin() {
		double minutoDouble = (horas % 1);
		minutoDouble *=60;
		return minutoDouble;
	}

	public int getRevistas() {
		return revistas;
	}
}
