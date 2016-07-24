package horasdecampo.orm;


import java.util.ArrayList;
import java.util.List;

import horasdecampo.models.Estudante;
import horasdecampo.models.Registro;
import horasdecampo.models.RevisitaEstudante;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.cadnunsdev.horasdecampo.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class OrmHelper extends OrmLiteSqliteOpenHelper {

	private static final String databaseName = "orm.db";
	private static final int databaseVersion = 7;

	public static ConnectionSource buildConnectionSource(Context context){
		return new OrmHelper(context).getConnectionSource();
	}
	
	public OrmHelper(Context context){
		super(context, databaseName, null, databaseVersion);
	}
	
	@Override
	public void onCreate(SQLiteDatabase bancoDados, ConnectionSource conn){
		try {			
			TableUtils.createTable(conn, Registro.class);
			TableUtils.createTable(conn, Estudante.class);
			TableUtils.createTable(conn, RevisitaEstudante.class);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void onUpgrade(SQLiteDatabase bancoDados, ConnectionSource conn, int newVersion,
			int arg3) {
		try {
			dropTables(bancoDados, conn);
			onCreate(bancoDados, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (java.sql.SQLException e) {
			e.printStackTrace();
		}	
	}

	private void dropTables(SQLiteDatabase bancoDados, ConnectionSource conn) throws java.sql.SQLException {
		
		TableUtils.dropTable(conn, RevisitaEstudante.class, true);
		TableUtils.dropTable(conn, Estudante.class, true);
		TableUtils.dropTable(conn, Registro.class, true);
	}	
}
