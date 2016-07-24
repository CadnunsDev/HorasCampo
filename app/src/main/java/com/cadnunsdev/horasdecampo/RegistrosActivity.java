package com.cadnunsdev.horasdecampo;


import horasdecampo.models.Mes;
import horasdecampo.models.Registro;

import com.cadnunsdev.horasdecampo.partials.RegistrosListAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class RegistrosActivity extends Activity {

	private static Mes mesSelecionado;
    private Button btnRelatorioMensal;
    private ListView listview;

    public static Intent buildIntent(Context ctx){
		return  new Intent(ctx, RegistrosActivity.class);
	}
	public static void buildIntentAndStart(Context ctx, Mes mes){
		mesSelecionado = mes;
		ctx.startActivity(buildIntent(ctx));		
	}

	private <T>T findViewById(Class<T> type, int id){
	    return type.cast(findViewById(id));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registros);
		listview = RegistrosListAdapter.BuildAdapterOnActivity(this, mesSelecionado.getRegistros(), R.id.lstRegistros);
        btnRelatorioMensal = findViewById(Button.class, R.id.btnRelatorioMensal);

		setTitle(mesSelecionado.toString());
		final Activity act = this;
        btnRelatorioMensal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(act, RelatorioMensalActivity.class);
                i.putExtra(Mes.class.getName(), mesSelecionado);
                act.startActivity(i);
            }
        });
		listview.setOnItemClickListener(new OnItemClickListener() {

			 
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Registro rg = mesSelecionado.getRegistros().get(position);
				
				Intent i = new Intent(act, RegistroDetailsActivity.class);
				i.putExtra("Registro", rg);
				act.startActivity(i);
			}
		});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registros, menu);
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
}
