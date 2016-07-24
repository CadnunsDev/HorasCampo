package com.cadnunsdev.horasdecampo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;

import cadnunsdev.androidutils.TextViewsHelper;
import horasdecampo.models.Estudante;
import horasdecampo.models.Mes;
import horasdecampo.models.Registro;
import horasdecampo.models.RevisitaEstudante;
import horasdecampo.orm.RepositoriesFactory;
import horasdecampo.orm.RepositoryBase;
import android.R.anim;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;

public class AddEntryActivity extends Activity {

	RepositoryBase<Registro> registroRepository;
	private RepositoryBase<Estudante> estudantesRepository;
	private ArrayList<RevisitaEstudante> estudosDirigidos;
	private RepositoryBase<RevisitaEstudante> revisitaEstudoRepo;
	
	public static void buildIntentAndStart(Context ctx){		
		ctx.startActivity(new Intent(ctx,  AddEntryActivity.class));	
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_entry);
		try {
			registroRepository = RepositoriesFactory.getRegistroRepository(this);
			estudantesRepository = RepositoriesFactory.getEstudanteRepository(this);
			revisitaEstudoRepo = RepositoriesFactory.getRevisitasEstudanteRepository(this);
			configViews();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void configViews() throws SQLException {
		final Spinner spEstudantes = (Spinner)findViewById(R.id.spEstudantes);
		List<Estudante> lista = estudantesRepository.queryForAll();
		ArrayAdapter<Estudante> adapter = new ArrayAdapter<Estudante>(this, android.R.layout.simple_spinner_dropdown_item, lista);
		spEstudantes.setAdapter(adapter);
		
		final ListView listaEstudos = (ListView)findViewById(R.id.lvEstudosDirigidos);
		estudosDirigidos = new ArrayList<RevisitaEstudante>();
		//estudosDirigidos.add(new RevisitaEstudante(new Estudante("teste")));
		final ArrayAdapter<RevisitaEstudante> lvAdapter = new ArrayAdapter<RevisitaEstudante>(this, android.R.layout.simple_list_item_activated_1, estudosDirigidos);
		
		listaEstudos.setAdapter(lvAdapter);
		
		Button btnAdd = (Button)findViewById(R.id.btnAddEstudantes);
		btnAdd.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Estudante estudante = (Estudante)spEstudantes.getSelectedItem();
				RevisitaEstudante rev = new RevisitaEstudante();
				estudosDirigidos.add(new RevisitaEstudante(estudante));
				lvAdapter.notifyDataSetChanged();
			}
		});
		listaEstudos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				estudosDirigidos.remove(position);	
				lvAdapter.notifyDataSetChanged();
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_entry, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void btnSalvar_Click(View view){
		boolean success = true;
		try{
			
			Registro registro = new Registro();
			DatePicker datePicket = (DatePicker)findViewById(R.id.dpData);
			Calendar data = Calendar.getInstance();
			int mount = datePicket.getMonth();
			data.clear();
			data.set(datePicket.getYear(),mount, datePicket.getDayOfMonth());
			registro.setData(data.getTime());
			registro.setHoras(TextViewsHelper.getInt(this, R.id.edtHs), TextViewsHelper.getInt(this, R.id.edtMin));
			registro.setLivros(TextViewsHelper.getInt(this, R.id.edtLvs));
			registro.setFolhetos(TextViewsHelper.getInt(this, R.id.edtTrat));
			registro.setVideoExibidos(TextViewsHelper.getInt(this, R.id.edtVideos));
			registro.setRevistas(TextViewsHelper.getInt(this, R.id.edtRevistas));
			int revisitasAvulsas = 0;
			registro.setRevisitas(estudosDirigidos.size() + revisitasAvulsas);
			ForeignCollection<RevisitaEstudante> revisitas = registroRepository.getEmptyForeignCollection("estudosDirigidos");
			for (RevisitaEstudante revisitaEstudante : estudosDirigidos) {
				revisitas.add(revisitaEstudante);
			}
			registro.setEstudosDirigidos(revisitas);
			
			registroRepository.create(registro);
			for (RevisitaEstudante revisitaEstudante : estudosDirigidos) {
				revisitaEstudante.setRegistro(registro);
				revisitaEstudoRepo.update(revisitaEstudante);
			}
		}catch(Exception e){
			success = false;
		}finally{
			if(success)
				MesesActivity.buildIntentAndStart(this);
		}
		
		
	}
}
