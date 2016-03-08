package net.jk.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.jk.jhipster.domain.Activo;
import net.jk.jhipster.domain.HistoricoSaldo;
import net.jk.jhipster.repository.ActivoRepository;
import net.jk.jhipster.repository.HistoricoSaldoRepository;
import net.jk.jhipster.repository.UserRepository;
import net.jk.jhipster.repository.search.ActivoSearchRepository;
import net.jk.jhipster.security.AuthoritiesConstants;
import net.jk.jhipster.security.SecurityUtils;
import net.jk.jhipster.web.rest.dto.SaldoLastMonth;
import net.jk.jhipster.web.rest.util.HeaderUtil;
import net.jk.jhipster.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Activo.
 */
@RestController
@RequestMapping("/api")
public class ActivoResource {

    private final Logger log = LoggerFactory.getLogger(ActivoResource.class);

    @Inject
    private ActivoRepository activoRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private HistoricoSaldoRepository historicoSaldoRepository;

    @Inject
    private ActivoSearchRepository activoSearchRepository;

    /**
     * POST  /activos -> Create a new activo.
     */
    @RequestMapping(value = "/activos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Activo> createActivo(@Valid @RequestBody Activo activo) throws URISyntaxException {
        log.debug("REST request to save Activo : {}", activo);
        if (activo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("activo", "idexists", "A new activo cannot already have an ID")).body(null);
        }

        // CHANGE: Set login user to the activo entity.
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            log.debug("No user passed in, using current user : {}", SecurityUtils.getCurrentUser());
           activo.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        }

        Activo result = activoRepository.save(activo);
        activoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/activos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("activo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /activos -> Updates an existing activo.
     */
    @RequestMapping(value = "/activos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Activo> updateActivo(@Valid @RequestBody Activo activo) throws URISyntaxException {
        log.debug("REST request to update Activo : {}", activo);
        if (activo.getId() == null) {
            return createActivo(activo);
        }
        Activo result = activoRepository.save(activo);
        activoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("activo", activo.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /activos -> Updates an existing activo.
     */
    @RequestMapping(value = "/historicize",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Activo> historizeActivo(@Valid @RequestBody Activo activo) throws URISyntaxException {
        log.debug("REST request to historicize Activo : {}", activo);

        // Get last saldo and data
        Activo oldActivo = activoRepository.findOne(activo.getId());

        // TODO: Check if exist a historicSaldo for this activo on this month
        List<HistoricoSaldo> historicosSaldos = historicoSaldoRepository.findAllByFechaAndActivo(oldActivo.getFecha(),oldActivo);

        if (historicosSaldos.isEmpty()) {


            // Set historicSaldo data
            HistoricoSaldo historicoSaldo = new HistoricoSaldo();
            historicoSaldo.setActivo(activo);
            historicoSaldo.setFecha(oldActivo.getFecha());
            historicoSaldo.setSaldo(oldActivo.getSaldo());
            historicoSaldo.setNotas(oldActivo.getNotas());
            historicoSaldo.setUser(oldActivo.getUser());

            // Save Historic Saldo
            historicoSaldoRepository.save(historicoSaldo);

            // update activo entity
            Activo result = activoRepository.save(activo);
            activoSearchRepository.save(result);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("activo", activo.getId().toString()))
                .body(result);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET  /activos -> get all the activos.
     */
    @RequestMapping(value = "/activos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Activo>> getAllActivos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Activos");


        Page<Activo> page;

        // TODO: If logged user is admin then return all records, else only yours
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            log.debug("Is Admin");
            page = activoRepository.findAll(pageable);
        } else {
            log.debug("----->>> NOT Admin user");
            page = activoRepository.findAllForCurrentUser(pageable);
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/activos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /activos/:id -> get the "id" activo.
     */
    @RequestMapping(value = "/activos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Activo> getActivo(@PathVariable Long id) {
        log.debug("REST request to get Activo : {}", id);
        Activo activo = activoRepository.findOne(id);
        return Optional.ofNullable(activo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /activos/:id -> delete the "id" activo.
     */
    @RequestMapping(value = "/activos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteActivo(@PathVariable Long id) {
        log.debug("REST request to delete Activo : {}", id);
        activoRepository.delete(id);
        activoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("activo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/activos/:query -> search for the activo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/activos/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Activo> searchActivos(@PathVariable String query) {
        log.debug("REST request to search Activos for query {}", query);
        return StreamSupport
            .stream(activoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

	/**
	 * GET /activos -> get all the activos for the current month
	 */

	@RequestMapping(value = "/saldo-this-month",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<SaldoLastMonth> getSaldoThisMonth() {

		// Get current date
		ZoneId id = ZoneId.of("Europe/Paris");
		LocalDate now = LocalDate.now(id);

		// Get first day of the month
		LocalDate startOfMonth = now.withDayOfMonth(1);

		// Get last day of the month
		LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());

		log.debug("looking for activos from user {} between : {} and {}", SecurityUtils.getCurrentUserLogin(), startOfMonth, endOfMonth);

		// filter by current user and sum importes
		List<Activo> listActivos = activoRepository.findAllByFechaBetweenAndUserLoginAndActivo(startOfMonth, endOfMonth, SecurityUtils.getCurrentUserLogin(),1);

		// Sum all saldo value of the month
		Double saldoTotal = listActivos.stream().mapToDouble(p -> p.getSaldo().doubleValue()).sum();

		SaldoLastMonth result = new SaldoLastMonth(now, saldoTotal);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
