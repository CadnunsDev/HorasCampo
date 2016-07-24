package com.cadnunsdev.horasdecampo.partials;

import horasdecampo.models.Estudante;
import horasdecampo.models.Registro;

import java.util.ArrayList;
import java.util.List;

import com.cadnunsdev.horasdecampo.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class EstudantesListAdapter extends BaseAdapter {

	private ArrayList<Estudante> estudantes;
	private Activity activity;
	public EstudantesListAdapter(Activity activity,
			ArrayList<Estudante> listaestudantes) {
				this.activity = activity;
				estudantes = listaestudantes;
		
	}
	public static ListView BuildAdapterOnActivity(Activity activity, ArrayList<Estudante> listaestudantes, int listViewId){
		ListView listView = (ListView)activity.findViewById(listViewId);
		BaseAdapter adapter = new EstudantesListAdapter(activity, listaestudantes);
		listView.setAdapter(adapter);
		return listView;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return estudantes.size();
	}

	@Override
	public Object getItem(int position) {
		return estudantes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return estudantes.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Estudante registro = estudantes.get(position);
		LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.estudante_list_item, null);
		
		TextView tvInfo = (TextView)layout.findViewById(R.id.tvEstudanteInfo);
		tvInfo.setText(registro.toString());
		return layout;
	}

	public interface EstudantesListListener{
		void editarEstudante(Estudante estudante);
	}
}
