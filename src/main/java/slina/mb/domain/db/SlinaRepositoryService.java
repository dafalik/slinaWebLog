package slina.mb.domain.db;

import java.util.List;

public interface SlinaRepositoryService {

	public abstract List<DbLogFileImpl> findAll();

	 public DbLogFileImpl save(DbLogFileImpl t) ;

	 public void delete(Long  id);

	 public DbLogFileImpl findOne(Long id);
	 
	 public List<DbLogEventImpl> getLogEvents();

}