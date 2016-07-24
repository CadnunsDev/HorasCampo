package com.cadnunsdev.horasdecampo.partials;

import horasdecampo.models.Mes;

import java.util.List;

import com.cadnunsdev.horasdecampo.*;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MesesListAdapter extends BaseAdapter {

	private Context activity;
	private List<Mes> listaMeses;

	public MesesListAdapter(Activity activity, List<Mes> listaMeses) {
		this.activity = activity;
		this.listaMeses = listaMeses;
		
	}
	public static ListView BuildAdapterOnActivity(Activity activity, List<Mes> listaMeses, int listViewId){
		ListView listView = (ListView)activity.findViewById(listViewId);
		BaseAdapter adapter = new MesesListAdapter(activity, listaMeses);
		listView.setAdapter(adapter);
		return listView;
	}
	@Override
	public int getCount() {
		return listaMeses.size();
	}

	@Override
	public Object getItem(int position) {
		return listaMeses.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return getItem(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Mes mesSelecionado = listaMeses.get(position);
		LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = //convertView.inflate(activity, R.layout.registro_list_item, parent);
		inflater.inflate(R.layout.mes_list_item, null);
		
		TextView tvMes = (TextView)layout.findViewById(R.id.tvMes);
		TextView tvAno = (TextView)layout.findViewById(R.id.tvMAno);
		Button btVerRegistros = (Button)layout.findViewById(R.id.btOpenRegistros);
		
		tvMes.setText(String.format("%02d", mesSelecionado.getMes()));
		tvAno.setText(String.format("%04d", mesSelecionado.getAno()));
		
		btVerRegistros.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				RegistrosActivity.buildIntentAndStart(activity, mesSelecionado);
			}
		});
		
		return layout;
	}

}
