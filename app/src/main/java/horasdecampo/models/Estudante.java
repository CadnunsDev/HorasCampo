package horasdecampo.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Estudante {
	
	@DatabaseField(generatedId=true)
	private int id;
		
	@DatabaseField
	private String nome;
	
	public Estudante() {
		// TODO Auto-generated constructor stub
	}
	public Estudante(String nome) {
		this.nome = nome;
		// TODO Auto-generated constructor stub
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nome;
	}
}
