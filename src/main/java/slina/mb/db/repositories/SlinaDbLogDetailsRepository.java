package slina.mb.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import slina.mb.domain.db.DbLogDetailsImpl;

public interface SlinaDbLogDetailsRepository extends JpaRepository<DbLogDetailsImpl, Long> {

}
