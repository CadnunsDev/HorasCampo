package horasdecampo.builders;

import android.content.Context;
import android.os.Environment;

import java.util.ArrayList;

import horasdecampo.models.viewmodels.CsvRegistroHoras;

/**
 * Created by Tiago Silva on 06/10/2016.
 */
public class CsvFactoryBuilder {
    private final String _outputFolder;
    private static final String HorasKey = "{Horas}";
    private static final String PublicacoesKey = "{Publicacoes}";
    private static final String RevisitasKey = "{Revisitas}";
    private static final String VideosKey = "{Videos}";
    private static final String EstudosDirigidosKey = "{EstudosDirigidos}";
    private static final String FieldDivider = ";";
    private static final String EstudosDivider = "|";

    public CsvFactoryBuilder(){
        _outputFolder = Environment.getExternalStorageDirectory()+"HorasCampoFiles/";
    }
    public void loadData(ArrayList<CsvRegistroHoras> dados){
        String header = "Horas;Publicacoes;Revisitas;Videos;EstudosDirigidos;";
        String detalhes =
                HorasKey+ FieldDivider+
                PublicacoesKey+ FieldDivider+
                RevisitasKey+ FieldDivider+
                VideosKey+ FieldDivider+
                EstudosDirigidosKey+ FieldDivider;
        StringBuilder contentBuilder = new StringBuilder(header);
        for (CsvRegistroHoras registro :dados) {
            String linha = "\n"+(detalhes
                    .replace(HorasKey, registro.getHoras().toString())
                    .replace(PublicacoesKey, registro.getPublicacoes().toString())
                    .replace(RevisitasKey, registro.getRevisitas().toString())
                    .replace(VideosKey, registro.getVideos().toString())
                    .replace(EstudosDirigidosKey, getEstudosAsString(registro)));
            contentBuilder = contentBuilder.append(linha);
        }
    }

    private String getEstudosAsString(CsvRegistroHoras registro) {
        ArrayList<String> linhas = (ArrayList<String>)registro.getEstudos().clone();
        String dados = "";
        for (String linha :linhas) {
            dados += linha.replace(EstudosDivider, "_")+EstudosDivider;
        }
        return  dados;
    }


}
