package com.cadnunsdev.horasdecampo;

import android.os.Bundle;
import android.widget.TextView;

import cadnunsdev.androidutils.BaseActivity;
import horasdecampo.models.Mes;

public class RelatorioMensalActivity extends BaseActivity {

    private Mes mesSelecionado;
    private TextView tvHoras;
    private TextView tvPublic;
    private TextView tvVideos;
    private TextView tvRevisitas;
    private TextView tvEstudos;
    private TextView tvObs;
    private TextView tvMesRelatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_mensal);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        loadComponents();
        bindData();
        mesSelecionado = (Mes)getIntent().getSerializableExtra(Mes.class.getName());
        tvMesRelatorio.setText("Relatorio Mês "+mesSelecionado.getMes()+"/"+mesSelecionado.getAno());
        setTitle("Dados do Mes selecionado:");
        tvPublic.setText(mesSelecionado.somarPublicacoes().toString());
        tvVideos.setText(mesSelecionado.getTotalVideos().toString());
        tvHoras.setText(mesSelecionado.getTotalHoras().toString());
        tvRevisitas.setText(mesSelecionado.somarRevisitas().toString());
        //tvEstudos.setText(mesSelecionado.somarEstudosDirigidos().toString());
    }

    private void loadComponents() {
        tvMesRelatorio = findViewById(TextView.class, R.id.tvMesRelatorio);
        tvPublic = findViewById(TextView.class, R.id.tvPublicacoes);
        tvVideos = findViewById(TextView.class, R.id.tvVideos);
        tvHoras = findViewById(TextView.class, R.id.tvHoras);
        tvRevisitas = findViewById(TextView.class, R.id.tvRevisitas);
        tvEstudos = findViewById(TextView.class, R.id.tvEstudos);
        tvObs = findViewById(TextView.class, R.id.tvObs);
    }

    private void bindData() {

    }
}
