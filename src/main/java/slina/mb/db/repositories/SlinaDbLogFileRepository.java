package slina.mb.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import slina.mb.domain.db.DbLogFileImpl;


public interface SlinaDbLogFileRepository extends JpaRepository<DbLogFileImpl, Long> {

}
