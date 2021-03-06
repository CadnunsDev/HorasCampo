package horasdecampo.models.viewmodels;

import java.util.ArrayList;

/**
 * Created by Tiago Silva on 06/10/2016.
 */
public class CsvRegistroHoras {
    private String data;
    private double horas;
    private int publicacoes;
    private int revisitas;
    private int videos;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private ArrayList<String> estudos;

    public Double getHoras() {
        return horas;
    }

    public void setHoras(double horas) {
        this.horas = horas;
    }

    public Integer getPublicacoes() {
        return publicacoes;
    }

    public void setPublicacoes(int publicacoes) {
        this.publicacoes = publicacoes;
    }

    public Integer getRevisitas() {
        return revisitas;
    }

    public void setRevisitas(int revisitas) {
        this.revisitas = revisitas;
    }

    public Integer getVideos() {
        return videos;
    }

    public void setVideos(int videos) {
        this.videos = videos;
    }

    public ArrayList<String> getEstudos() {
        return estudos;
    }

    public void setEstudos(ArrayList<String> estudos) {
        this.estudos = estudos;
    }
}
