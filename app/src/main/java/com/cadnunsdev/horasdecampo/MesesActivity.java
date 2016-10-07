package com.cadnunsdev.horasdecampo;

import horasdecampo.builders.CsvFactoryBuilder;
import horasdecampo.models.Estudante;
import horasdecampo.models.Mes;
import horasdecampo.models.Registro;
import horasdecampo.models.RevisitaEstudante;
import horasdecampo.models.viewmodels.CsvRegistroHoras;
import horasdecampo.orm.RepositoriesFactory;
import horasdecampo.orm.RepositoryBase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cadnunsdev.horasdecampo.partials.MesesListAdapter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;


public class MesesActivity extends Activity {

    private ListView listAdapter;
    private RepositoryBase<Registro> registroRepo;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public static void buildIntentAndStart(Context ctx) {
        ctx.startActivity(new Intent(ctx, MesesActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Mes> mes = new ArrayList<Mes>();
        try {
            registroRepo = RepositoriesFactory.getRegistroRepository(this);
            mes = RepositoriesFactory.getGroupByMeses(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_meses);
        listAdapter = MesesListAdapter.BuildAdapterOnActivity(this, mes, R.id.lstMeses);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meses, menu);

        return true;
    }

    public void btnNovoRegistro_Click(View v) {
        AddEntryActivity.buildIntentAndStart(this);
    }

    public void btnEstudantes_Click(View v) {
        EstudantesActivity.buildIntentAndStart(this);
    }

    public void btnGerarCSVRelatorios(View v) {
        try {
            List<Registro> lista = registroRepo.queryForAll();
            RepositoryBase<Estudante> estudantes = RepositoriesFactory.getEstudanteRepository(this);
            CsvFactoryBuilder builder= new CsvFactoryBuilder();
            ArrayList<CsvRegistroHoras> models = new ArrayList<>();
            for (Registro registro : lista) {
                CsvRegistroHoras model = new CsvRegistroHoras();
                model.setHoras(registro.getHoras());
                model.setPublicacoes(registro.getPublicacoes() + registro.getFolhetos() + registro.getLivros()+registro.getRevistas());
                model.setRevisitas(registro.getRevisitas());
                model.setVideos(registro.getVideoExibidos());
                model.setData(registro.getDataCadastroString("dd/MM/yyyy"));
                ArrayList<String> estudantesLista = new ArrayList<>();
                for (RevisitaEstudante rev : registro.getEstudosDirigidos()){
                    try {
                        estudantesLista.add(estudantes.queryForId(rev.getEstudante().getId()).getNome());
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
                model.setEstudos(estudantesLista);
                models.add(model);
            }
            if (models.size() > 0)
                builder.loadData(models).Save();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_vertodos) {
            try {
                Mes mes = new Mes(0, 0, registroRepo.queryForAll());
                RegistrosActivity.buildIntentAndStart(this, mes);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Meses Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.cadnunsdev.horasdecampo/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Meses Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.cadnunsdev.horasdecampo/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
