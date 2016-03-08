package net.jk.jhipster.repository;

import net.jk.jhipster.domain.Activo;
import net.jk.jhipster.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the Activo entity.
 */
public interface ActivoRepository extends JpaRepository<Activo, Long> {

	@Query("select activo from Activo activo where activo.user.login = ?#{principal.username} ")
	List<Activo> findByUserIsCurrentUser();

	@Query("select activo from Activo activo where activo.user.login = ?#{principal.username} ")
	Page<Activo> findAllForCurrentUser(Pageable pageable);

	List<Activo> findAllByFechaBetweenAndUserLoginAndActivo(LocalDate firstDate, LocalDate lastDate, String login,Integer activo);

    List<Activo> findAllByFechaBetweenAndActivo(LocalDate firstDate, LocalDate lastDate, Integer activo);

}
