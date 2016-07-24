package horasdecampo.models;

import com.j256.ormlite.field.DatabaseField;

public class RevisitaEstudante {
	public RevisitaEstudante(Estudante estudante) {
		this();
		this.estudante = estudante;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public Registro getRegistro() {
		return registro;
	}
	public void setRegistro(Registro registro) {
		this.registro = registro;
	}
	public Estudante getEstudante() {
		return estudante;
	}
	public void setEstudante(Estudante estudante) {
		this.estudante = estudante;
	}
	public RevisitaEstudante() {
		// TODO Auto-generated constructor stub
	}
	@DatabaseField(generatedId=true)
	int Id;
	@DatabaseField(foreign = true)
	Registro registro;
	@DatabaseField(foreign = true)
	Estudante estudante;
	
	@Override
	public String toString() {
		return getEstudante().getNome();
	}
}
