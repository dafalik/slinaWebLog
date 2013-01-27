package slina.mb.domain.db;

import java.util.Comparator;

public class DbLogEventComparator implements Comparator<DbLogEventImpl> {

	@Override
	public int compare(DbLogEventImpl o1, DbLogEventImpl o2) {
		
		Long id0 = o1.getId();
		long id1 = o2.getId();
		
		if (id0 > id1){
            return +1;
        }else if (id0 < id1){
            return -1;
        }
		
        return 0;
	}

}
