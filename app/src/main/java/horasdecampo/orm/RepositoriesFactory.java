package horasdecampo.orm;

import horasdecampo.models.Estudante;
import horasdecampo.models.Mes;
import horasdecampo.models.Registro;
import horasdecampo.models.RevisitaEstudante;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import android.content.Context;
import android.widget.ArrayAdapter;

public class RepositoriesFactory {
	private static ConnectionSource conn;
	public static ConnectionSource getConnection(Context context){
		conn = (conn != null)? conn : OrmHelper.buildConnectionSource(context);
		return conn;
	}
	public static RepositoryBase<Estudante> getEstudanteRepository(Context context) throws SQLException{
		ConnectionSource conn = getConnection(context);
		return new RepositoryBase<Estudante>(conn, Estudante.class);
	}
	public static RepositoryBase<Registro> getRegistroRepository(Context context) throws SQLException{
		ConnectionSource conn = getConnection(context);
		return new RepositoryBase<Registro>(conn, Registro.class);
	}
	
	public static List<Mes> getGroupByMeses(Context context) throws SQLException{
		RepositoryBase<Registro> registrosRepo = getRegistroRepository(context);
		GenericRawResults<String[]> query= registrosRepo.queryRaw("select count(1), strftime('%m', data), strftime('%Y', data) from "+registrosRepo.getTableInfo().getTableName()+" group by strftime('%m/%Y', data)");
		
		List<Mes> list = new ArrayList<Mes>();
		List<String[]> rows = query.getResults();
		
		for (String[] tupla : rows) {
			if(tupla!= null && tupla[1] != null && tupla[2] != null ){
				
				List<Registro> reg = registrosRepo.queryBuilder().where().raw("strftime('%m/%Y', data) = '"+tupla[1]+"/"+tupla[2]+"'").query();
				Mes mes = new Mes(Integer.parseInt(tupla[1]), Integer.parseInt(tupla[2]), reg);
				mes.setQuantRegistros(Integer.parseInt(tupla[0]));
				list.add(mes);
			}			
		}
		
		return list;		
	}
	public static RepositoryBase<RevisitaEstudante> getRevisitasEstudanteRepository(Context context) throws SQLException {
		ConnectionSource conn = getConnection(context);
		return new RepositoryBase<RevisitaEstudante>(conn, RevisitaEstudante.class);
	}
}
