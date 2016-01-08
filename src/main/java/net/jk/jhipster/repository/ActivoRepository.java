package net.jk.jhipster.repository;

import net.jk.jhipster.domain.Activo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Activo entity.
 */
public interface ActivoRepository extends JpaRepository<Activo,Long> {

    @Query("select activo from Activo activo where activo.user.login = ?#{principal.username}")
    List<Activo> findByUserIsCurrentUser();

    @Query("select activo from Activo activo where activo.user.login = ?#{principal.username}")
	Page<Activo> findAllForCurrentUser(Pageable pageable);

}
