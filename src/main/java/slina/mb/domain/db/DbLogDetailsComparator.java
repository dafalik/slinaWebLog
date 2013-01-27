package slina.mb.domain.db;

import java.util.Comparator;

public class DbLogDetailsComparator implements Comparator<DbLogDetailsImpl> {

	@Override
	public int compare(DbLogDetailsImpl arg0, DbLogDetailsImpl arg1) {
		
		Long id0 = arg0.getLogDetailLineNumber();
		long id1 = arg1.getLogDetailLineNumber();
		
		if (id0 > id1){
            return +1;
        }else if (id0 < id1){
            return -1;
        }
		
        return 0;
	}

}
