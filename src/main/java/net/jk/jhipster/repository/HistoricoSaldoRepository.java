package net.jk.jhipster.repository;

import net.jk.jhipster.domain.HistoricoSaldo;

import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the HistoricoSaldo entity.
 */
public interface HistoricoSaldoRepository extends JpaRepository<HistoricoSaldo,Long> {

    @Query("select historicoSaldo from HistoricoSaldo historicoSaldo where historicoSaldo.user.login = ?#{principal.username}")
    List<HistoricoSaldo> findByUserIsCurrentUser();

    List<HistoricoSaldo> findAllByFechaBetweenAndUserLogin(LocalDate firstFecha, LocalDate lastFecha, String login);

    List<HistoricoSaldo> findAllByFechaBetween(LocalDate firstFecha, LocalDate lastFecha);


}
