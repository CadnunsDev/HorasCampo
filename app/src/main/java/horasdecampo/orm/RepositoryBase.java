package horasdecampo.orm;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class RepositoryBase<T> extends BaseDaoImpl<T, Integer>{
	
	public RepositoryBase(ConnectionSource conn, Class<T> classType) throws SQLException{
		super(classType);
		setConnectionSource(conn);
		initialize();
	}

	public void truncateAndCreateMany(List<T> list) throws SQLException {
		TableUtils.clearTable(getConnectionSource(), getDataClass());
		create(list);
	}
}
