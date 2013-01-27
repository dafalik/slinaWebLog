package slina.mb.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import slina.mb.domain.db.DbLogEventImpl;

public interface SlinaDbEventRepository extends JpaRepository<DbLogEventImpl, Long> {

}
