package horasdecampo.builders;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private String contentFile = "";
    private static final String DataKey = "{Data}";

    public CsvFactoryBuilder(){
        _outputFolder = Environment.getExternalStorageDirectory()+"/HorasCampoFiles/";
        File file = new File(_outputFolder);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    public CsvFactoryBuilder loadData(ArrayList<CsvRegistroHoras> dados){
        String header = "Data;Horas;Publicacoes;Revisitas;Videos;EstudosDirigidos;";
        String detalhes =
                DataKey+ FieldDivider+
                HorasKey+ FieldDivider+
                PublicacoesKey+ FieldDivider+
                RevisitasKey+ FieldDivider+
                VideosKey+ FieldDivider+
                EstudosDirigidosKey+ FieldDivider;
        StringBuilder contentBuilder = new StringBuilder(header);
        for (CsvRegistroHoras registro :dados) {
            String linha = "\n"+(detalhes
                    .replace(DataKey, registro.getData())
                    .replace(HorasKey, registro.getHoras().toString())
                    .replace(PublicacoesKey, registro.getPublicacoes().toString())
                    .replace(RevisitasKey, registro.getRevisitas().toString())
                    .replace(VideosKey, registro.getVideos().toString())
                    .replace(EstudosDirigidosKey, getEstudosAsString(registro)));
            contentBuilder = contentBuilder.append(linha);
        }
        contentFile = contentBuilder.toString();
        return  this;
    }

    private String getEstudosAsString(CsvRegistroHoras registro) {
        ArrayList<String> linhas = (ArrayList<String>)registro.getEstudos().clone();
        String dados = "";
        for (String linha :linhas) {
            dados += linha.replace(EstudosDivider, "_")+EstudosDivider;
        }
        return  dados;
    }


    public void Save() {

        if (contentFile.length() > 0){
            final File myFile = new File(_outputFolder, "backup_horasCampo" + ".csv");

            try {
                if (!myFile.exists())
                {
                    try {
                        myFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                FileOutputStream fos = new FileOutputStream(myFile);

                fos.write(contentFile.getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
