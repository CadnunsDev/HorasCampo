package com.cadnunsdev.horasdecampo;

import horasdecampo.models.Mes;
import horasdecampo.models.Registro;
import horasdecampo.orm.RepositoriesFactory;
import horasdecampo.orm.RepositoryBase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cadnunsdev.horasdecampo.partials.MesesListAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;


public class MesesActivity extends Activity {

    private ListView listAdapter;
	private RepositoryBase<Registro> registroRepo;

    public static void buildIntentAndStart(Context ctx){		
		ctx.startActivity(new Intent(ctx,  MesesActivity.class));	
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meses, menu);
        
        return true;
    }

    public void btnNovoRegistro_Click(View v){
    	AddEntryActivity.buildIntentAndStart(this);
    }

    public void btnEstudantes_Click(View v){
    	EstudantesActivity.buildIntentAndStart(this);
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
}
