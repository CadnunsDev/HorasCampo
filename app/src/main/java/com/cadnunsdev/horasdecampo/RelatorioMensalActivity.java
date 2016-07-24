package com.cadnunsdev.horasdecampo;

import android.os.Bundle;
import android.app.Activity;

import horasdecampo.models.Mes;

public class RelatorioMensalActivity extends Activity {

    private Mes mesSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_mensal);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        mesSelecionado = (Mes)getIntent().getSerializableExtra(Mes.class.getName());
        setTitle("Relatorio Mês "+mesSelecionado.getMes()+"/"+mesSelecionado.getAno());
    }
}
