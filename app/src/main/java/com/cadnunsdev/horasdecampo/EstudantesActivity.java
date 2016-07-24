package com.cadnunsdev.horasdecampo;

import horasdecampo.models.Estudante;
import horasdecampo.orm.RepositoriesFactory;
import horasdecampo.orm.RepositoryBase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cadnunsdev.horasdecampo.partials.EstudantesListAdapter;
import com.cadnunsdev.horasdecampo.partials.EstudantesListAdapter.EstudantesListListener;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class EstudantesActivity extends TabActivity implements EstudantesListListener{

	private ArrayList<Estudante> listaEstudantes;
	private TabHost tabHost;
	private Estudante estudanteOnForm;
	private RepositoryBase<Estudante> _estudantesRepository;
	private EditText edtNomeEstudante;
	private int estudantePosition;
	private TabSpec tabFormEstudante;
	private TabSpec tabListEstudante;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estudantes);		
		tabHost = (TabHost)findViewById(android.R.id.tabhost);
		tabListEstudante = tabHost.newTabSpec("First Tab");
		tabFormEstudante = tabHost.newTabSpec("Second Tab");
		tabListEstudante.setIndicator("Lista");
		tabListEstudante.setContent(R.id.tabListEstudante);
		tabFormEstudante.setIndicator("Novo");
		tabFormEstudante.setContent(R.id.tabFormEstudante);
		tabHost.addTab(tabListEstudante);
		tabHost.addTab(tabFormEstudante);	
		edtNomeEstudante = (EditText)findViewById(R.id.edtNomeEstudante);
		try {
			_estudantesRepository = RepositoriesFactory.getEstudanteRepository(this);
//			listaEstudantes = new ArrayList<Estudante>(Arrays.asList(new Estudante("Emerson"), new Estudante("Wagner")));
			listaEstudantes = new ArrayList<Estudante>(_estudantesRepository.queryForAll());
			ListView lvEstudantes = EstudantesListAdapter.BuildAdapterOnActivity(this, listaEstudantes, R.id.lvEstudantes);
			lvEstudantes.setOnItemClickListener(new OnItemClickListener() {
				
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					estudanteOnForm = listaEstudantes.get(position);
					estudantePosition = position;
					edtNomeEstudante.setText(estudanteOnForm.getNome());
					getTabHost().setCurrentTab(1);
					changeTabText(1,"Editando id = "+estudanteOnForm.getId());
				}
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.estudantes, menu);
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
	
	

	public static void buildIntentAndStart(Context ctx) {
		ctx.startActivity(new Intent(ctx,  EstudantesActivity.class));
		
	}

	public void btnSalvar_Click(View v){		
		
		
		try {
			if (estudanteOnForm == null) {
				estudanteOnForm = new Estudante(edtNomeEstudante.getText().toString());
				_estudantesRepository.create(estudanteOnForm);
				if (estudanteOnForm.getId() > 0) {
					listaEstudantes.add(estudanteOnForm);
				}
			}else{
				Estudante estudante = new Estudante(edtNomeEstudante.getText().toString());
				estudante.setId(estudanteOnForm.getId());
				_estudantesRepository.update(estudante);
				listaEstudantes.set(estudantePosition, estudante);
			}

			getTabHost().setCurrentTab(0);
			estudanteOnForm = null;
			estudantePosition = 0;
			edtNomeEstudante.setText("");
			changeTabText(1,"Novo");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void editarEstudante(Estudante estudante) {
		estudanteOnForm = estudante;
		getTabHost().setCurrentTab(1);		
	}
	
	void changeTabText(int tabOrder, String text){
		TextView tv = (TextView) tabHost.getTabWidget().getChildAt(tabOrder).findViewById(android.R.id.title);
		tv.setText(text);
	}
}
