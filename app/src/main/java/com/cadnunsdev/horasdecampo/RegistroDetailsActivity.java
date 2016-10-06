package com.cadnunsdev.horasdecampo;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import cadnunsdev.PtBrUtils.DateTimeUtils;
import cadnunsdev.androidutils.Dialogs;
import cadnunsdev.androidutils.ResourcesHelper;
import cadnunsdev.androidutils.ToastExt;

import com.cadnunsdev.horasdecampo.R.id;
import com.j256.ormlite.dao.CloseableIterator;

import horasdecampo.models.Estudante;
import horasdecampo.models.Registro;
import horasdecampo.models.RevisitaEstudante;
import horasdecampo.orm.RepositoriesFactory;
import horasdecampo.orm.RepositoryBase;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class RegistroDetailsActivity extends Activity {

	private RepositoryBase<Registro> repositorio;
	private Registro registroModel;
	private RepositoryBase<Estudante> estudantesRepo;
	private RepositoryBase<RevisitaEstudante> revistasRepository;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			repositorio = RepositoriesFactory.getRegistroRepository(this);
			estudantesRepo = RepositoriesFactory.getEstudanteRepository(this);
			revistasRepository = RepositoriesFactory.getRevisitasEstudanteRepository(this);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setContentView(R.layout.activity_registro_details);
		registroModel = (Registro)getIntent().getSerializableExtra("Registro");
		try {
			registroModel = repositorio.queryForId((int)registroModel.getId());
			for (RevisitaEstudante	 estudoDirigido : registroModel.getEstudosDirigidos()) {
				estudoDirigido.setEstudante(estudantesRepo.queryForId(estudoDirigido.getEstudante().getId()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setTitle(registroModel.toString());
		TextView tvHtml = (TextView)findViewById(id.tvHtml);
		WebView wbInfos = (WebView)findViewById(id.wbInfos);
		//tvHtml.setText(getDefinitionListHtml(registroModel));
		//tvHtml.setTextSize(20);
		String template = ResourcesHelper.LoadFile(this, "registro_detalhes.html");
		template = template.replace("{{conteudo}}", getDefinitionListHtml(registroModel));
		wbInfos.loadData(template,  "text/html", "UTF-8");
	}

	
	
	private String getDefinitionListHtml(Registro rg) {
		StringBuilder sb = new StringBuilder();
		sb.append("<h2>Data "+rg.getDataCadastroString("dd/MM/yyyy")+"<h2>");
		sb.append("<table><tr>");
		sb.append("<td>Horas</td>");
		sb.append("<td>Revistas</td>");
		sb.append("<td>Revisitas</td>");
		sb.append("<td>Videos</td>");
		sb.append("</tr><tr>");
		sb.append("<td>"+rg.getSomenteHoras()+"hs"+rg.getSomenteMin()+"</td>");
		sb.append("<td>"+rg.getRevistas()+"</td>");
		sb.append("<td>"+rg.getRevisitas()+"</td>");
		sb.append("<td>"+rg.getVideoExibidos()+"</td>");		
//		RevisitaEstudante[] estudos = (RevisitaEstudante[])rg.getEstudosDirigidos().toArray();
		
//		List <RevisitaEstudante> rev;
//		try {
//			rev = revistasRepository.queryForAll();
//			@SuppressWarnings("unused")
//			int size = rev.size();
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		CloseableIterator<RevisitaEstudante> itinerator = rg.getEstudosDirigidos().closeableIterator();
			sb.append("</tr></table>");
			sb.append("<h4>Estudos</h4>");
			sb.append("<ul>");
			while(itinerator.hasNext()){
				
				RevisitaEstudante estudante;
				try {
					estudante = itinerator.current();
					estudante.setEstudante(estudantesRepo.queryForId(estudante.getEstudante().getId()));
					sb.append("<li>" + estudante.getEstudante().getNome() + "</li>");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			sb.append("</ul>");
		
//		if (estudos.length > 0) {
//			sb.append("</tr></table>");
//			sb.append("<h4>Estudos</h4>");
//			sb.append("<ul>");
//			for (RevisitaEstudante estudante : estudos) {
//				sb.append("<li>" + estudante.getEstudante().getNome() + "</li>");
//			}
//			sb.append("</ul>");
//		}else{
//			sb.append("Sem estudos dirigidos");
//		}
		return sb.toString();
		
//		sb.append("<p>"+rg.getDataCadastroString()+"</p>");
//		sb.append("<h1>Horas</h1>"+
//				  "<p>"+rg.getSomenteHoras()+"hs"+rg.getSomenteMin()+"</p>");
//		sb.append("<h1>Revistas</h1>"+
//				  "<p>"+rg.getRevistas()+"</p>");
//		sb.append("<h1>Revisitas</h1>"+
//				  "<p>"+rg.getRevisitas()+"</p>");
//		sb.append("<h1>Videos</h1>"+
//				  "<p>"+rg.getVideoExibidos()+"</p>");
//		sb.append("<h1>Revistas</h1>"+
//				  "<p>"+rg.getSomenteHoras()+"hs"+rg.getSomenteMin()+"</p>"+
//				"</td></tr></table>");
		//return Html.fromHtml(sb.toString());
	}

	public void btnDelete_Click(View v){
		final Activity act = this;
		Dialogs.Confirm(this, "deseja deletar?", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					repositorio.delete(registroModel);
					ToastExt.ShowMsg(act, "Excluido");
					MesesActivity.buildIntentAndStart(act);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			 	
			}
		});
//		ToastExt.ShowMsg(this , "executand,,");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro_details, menu);
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
