package com.cadnunsdev.horasdecampo.partials;

import horasdecampo.models.Mes;
import horasdecampo.models.Registro;

import java.util.List;

import com.cadnunsdev.horasdecampo.*;
import com.cadnunsdev.horasdecampo.R.id;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class RegistrosListAdapter extends BaseAdapter {

	private Context activity;
	private List<Registro> registros;

	public RegistrosListAdapter(Activity activity, List<Registro> registros) {
		this.activity = activity;
		this.registros = registros;
		
	}
	public static ListView BuildAdapterOnActivity(Activity activity, List<Registro> listaMeses, int listViewId){
		ListView listView = (ListView)activity.findViewById(listViewId);
		BaseAdapter adapter = new RegistrosListAdapter(activity, listaMeses);
		listView.setAdapter(adapter);
		return listView;
	}
	@Override
	public int getCount() {
		return registros.size();
	}

	@Override
	public Object getItem(int position) {
		return registros.get(position);
	}

	@Override
	public long getItemId(int position) {		
		return getItem(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Registro registro = registros.get(position);
		LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.registro_list_item, null);
		final LinearLayout formsControls = ((LinearLayout)layout.findViewById(R.id.formsControls));
		final LinearLayout displayTextControls = ((LinearLayout)layout.findViewById(R.id.displayTextControls));
		
		TextView tvDisplayInfos = (TextView)layout.findViewById(R.id.displayInfos);
		TextView displayDate = (TextView)layout.findViewById(R.id.displayDate);
		
		EditText edtHs = (EditText)layout.findViewById(R.id.edtHs);
		EditText edtMin = (EditText)layout.findViewById(R.id.edtMin);
		EditText edtRevistas = (EditText)layout.findViewById(R.id.edtRevistas);
		EditText edtLvs = (EditText)layout.findViewById(R.id.edtLvs);
		EditText edtVideos = (EditText)layout.findViewById(R.id.edtVideos);		
		
		set(edtHs, registro.getSomenteHoras());
		set(edtMin, registro.getSomenteMin());
		set(tvDisplayInfos,registro.toString());
		set(edtRevistas, registro.getRevistas());
		set(displayDate, registro.getDataCadastroString("dd/MM/yyyy"));
		
		layout.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
//				formsControls.setVisibility(View.VISIBLE);
//				displayTextControls.setVisibility(View.GONE);
//				return true;
//				Registro rg = registros.getRegistros().get(position);
				
				Intent i = new Intent(activity, RegistroDetailsActivity.class);
				i.putExtra("Registro", registro);
				activity.startActivity(i);
			}

			
		});
		return layout;
	}
	private void set(TextView view, Object value) {
		view.setText(value.toString());
	}
	
	
}
