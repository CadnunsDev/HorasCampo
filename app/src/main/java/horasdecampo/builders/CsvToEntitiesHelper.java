package horasdecampo.builders;

import com.j256.ormlite.stmt.query.In;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import cadnunsdev.ArraysUtils.WhereQuery;
import cadnunsdev.androidutils.TextFileUtils;
import horasdecampo.models.Estudante;
import horasdecampo.models.Registro;
import horasdecampo.models.RevisitaEstudante;
import horasdecampo.models.viewmodels.CsvRegistroHoras;
import horasdecampo.orm.RepositoryBase;

/**
 * Created by Tiago Silva on 08/10/2016.
 */
public class CsvToEntitiesHelper {
    private final RepositoryBase<Estudante> _estudanteRepository;
    private final RepositoryBase<RevisitaEstudante> _revisitaEstudanteRepository;
    private final RepositoryBase<Registro> _registroRepository;

    public CsvToEntitiesHelper(
            RepositoryBase<Estudante> estudanteRepository,
            RepositoryBase<RevisitaEstudante> revisitaEstudanteRepository,
            RepositoryBase<Registro> registroRepository){
        _estudanteRepository = estudanteRepository;
        _revisitaEstudanteRepository = revisitaEstudanteRepository;
        _registroRepository = registroRepository;
    }

    public void saveOnDataBase(String filePath) throws SQLException {
        ArrayList<CsvRegistroHoras> viewModelsArquivo = getViewModelFromFile(filePath);
        ArrayList<Estudante> estudantes = saveEstudantes(viewModelsArquivo);
        saveRegistros(viewModelsArquivo, estudantes);
    }

    private void saveRegistros(ArrayList<CsvRegistroHoras> viewModelsArquivo, ArrayList<Estudante> estudantes) {

    }

    private ArrayList<Estudante> saveEstudantes(ArrayList<CsvRegistroHoras> viewModelsArquivo) throws SQLException {
        WhereQuery<CsvRegistroHoras> query = new WhereQuery<>();
        ArrayList<Estudante> estudantes =  new ArrayList<>();
        ArrayList<CsvRegistroHoras> registrosComEstudantes = query.Where(viewModelsArquivo, new WhereQuery.WhereAction<CsvRegistroHoras>() {
            @Override
            public boolean Where(CsvRegistroHoras entity) {
                return entity.getEstudos().size() > 0;
            }
        });
        for (CsvRegistroHoras linha:registrosComEstudantes) {
            for (String estudo :linha.getEstudos()){
                if(_estudanteRepository.queryForEq("nome", estudo).size() == 0){
                    estudantes.add(new Estudante(estudo));
                }
            }
        }
        _estudanteRepository.create(estudantes);
        return estudantes;
    }

    public ArrayList<CsvRegistroHoras> getViewModelFromFile(String filePath){
        final File arquivoCsv = new File(filePath);
        ArrayList<CsvRegistroHoras> lista = new ArrayList<>();
        if(arquivoCsv.exists()){
            lista.addAll(ProcessFile(arquivoCsv));
        }
        return lista;
    }

    private ArrayList<CsvRegistroHoras> ProcessFile(File arquivoCsv) {
        String path = arquivoCsv.getPath();
        String[] linhas = TextFileUtils.GetLinesFromFile(path);
        ArrayList<CsvRegistroHoras> lista = new ArrayList<>();
        for (String linha : linhas) {
            if (!linha.contains("Horas")){
                String[] celulas = linha.split(";");
                CsvRegistroHoras model = new CsvRegistroHoras();
                model.setData(celulas[0]);
                model.setHoras(Double.valueOf(celulas[1]));
                model.setPublicacoes(Integer.getInteger(celulas[2]));
                model.setRevisitas(Integer.getInteger(celulas[3]));
                model.setVideos(Integer.getInteger(celulas[4]));
                model.setEstudos(new ArrayList<String>(Arrays.asList(celulas[5].split("|"))));
                lista.add(model);
            }
        }

        return lista;
    }
}
