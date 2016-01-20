package net.jk.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.jk.jhipster.domain.HistoricoSaldo;
import net.jk.jhipster.repository.HistoricoSaldoRepository;
import net.jk.jhipster.repository.search.HistoricoSaldoSearchRepository;
import net.jk.jhipster.web.rest.dto.SaldoActivoAllYearsDTO;
import net.jk.jhipster.web.rest.dto.SaldoActivoLastYearDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing HistoricoSaldo.
 */
@RestController
@RequestMapping("/api")
public class HistoricoSaldoResource {

    private final Logger log = LoggerFactory.getLogger(HistoricoSaldoResource.class);

    @Inject
    private HistoricoSaldoRepository historicoSaldoRepository;

    @Inject
    private HistoricoSaldoSearchRepository historicoSaldoSearchRepository;

    /**
     * POST  /historicoSaldos -> Create a new historicoSaldo.
     */
    @RequestMapping(value = "/historicoSaldos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HistoricoSaldo> createHistoricoSaldo(@Valid @RequestBody HistoricoSaldo historicoSaldo) throws URISyntaxException {
        log.debug("REST request to save HistoricoSaldo : {}", historicoSaldo);
        if (historicoSaldo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("historicoSaldo", "idexists", "A new historicoSaldo cannot already have an ID")).body(null);
        }
        HistoricoSaldo result = historicoSaldoRepository.save(historicoSaldo);
        historicoSaldoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/historicoSaldos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("historicoSaldo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /historicoSaldos -> Updates an existing historicoSaldo.
     */
    @RequestMapping(value = "/historicoSaldos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HistoricoSaldo> updateHistoricoSaldo(@Valid @RequestBody HistoricoSaldo historicoSaldo) throws URISyntaxException {
        log.debug("REST request to update HistoricoSaldo : {}", historicoSaldo);
        if (historicoSaldo.getId() == null) {
            return createHistoricoSaldo(historicoSaldo);
        }
        HistoricoSaldo result = historicoSaldoRepository.save(historicoSaldo);
        historicoSaldoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("historicoSaldo", historicoSaldo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /historicoSaldos -> get all the historicoSaldos.
     */
    @RequestMapping(value = "/historicoSaldos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HistoricoSaldo>> getAllHistoricoSaldos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HistoricoSaldos");
        Page<HistoricoSaldo> page = historicoSaldoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/historicoSaldos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /historicoSaldos/:id -> get the "id" historicoSaldo.
     */
    @RequestMapping(value = "/historicoSaldos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HistoricoSaldo> getHistoricoSaldo(@PathVariable Long id) {
        log.debug("REST request to get HistoricoSaldo : {}", id);
        HistoricoSaldo historicoSaldo = historicoSaldoRepository.findOne(id);
        return Optional.ofNullable(historicoSaldo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /historicoSaldos/:id -> delete the "id" historicoSaldo.
     */
    @RequestMapping(value = "/historicoSaldos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHistoricoSaldo(@PathVariable Long id) {
        log.debug("REST request to delete HistoricoSaldo : {}", id);
        historicoSaldoRepository.delete(id);
        historicoSaldoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("historicoSaldo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/historicoSaldos/:query -> search for the historicoSaldo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/historicoSaldos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HistoricoSaldo> searchHistoricoSaldos(@PathVariable String query) {
        log.debug("REST request to search HistoricoSaldos for query {}", query);
        return StreamSupport
            .stream(historicoSaldoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET /historicoSaldo/lastYear -> get lastYear historicoSaldo
     */
    @RequestMapping (value ="/historicoSaldos/lastYear",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SaldoActivoLastYearDTO> getHistoricoSaldoLastYear(Pageable pageable) {

        // Init SaldoActivoLastYearDTO
        SaldoActivoLastYearDTO result = new SaldoActivoLastYearDTO();

        // Get time and date
        ZoneId id = ZoneId.of("Europe/Paris");
        LocalDate now = LocalDate.now(id);

        // Get actual year
        int thisYear = now.getYear();

        // Get actual month
        int thisMonth = now.getMonthValue();

        // For this month to first month of the year then
        List<Double> importesByMonth = new ArrayList<>();
        for (int i = 1; i <= thisMonth ; i++) {

            // Get first day of the month
            LocalDate firstDayOfMonth = now.withMonth(i).withDayOfMonth(1);

            // Get last day of the month
            LocalDate lastDayOfMonth = now.withMonth(i).withDayOfMonth(now.withMonth(i).lengthOfMonth());

            // filter by current user and sum importes
            //List<HistoricoSaldo> historicoSaldoByMonth =  historicoSaldoRepository.findAllByFechaBetweenAndUserLogin(firstDayOfMonth, lastDayOfMonth, SecurityUtils.getCurrentUserLogin());
            List<HistoricoSaldo> historicoSaldoByMonth =  historicoSaldoRepository.findAllByFechaBetween(firstDayOfMonth, lastDayOfMonth);

            // Get total amount of this month
            Double saldoTotal = historicoSaldoByMonth.stream().mapToDouble(p -> p.getSaldo().doubleValue()).sum();

            // Add this amount to SaldoActivoLastYearDTO
            importesByMonth.add(saldoTotal);
        }

        result.setImportesByMonth(importesByMonth);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    /**
     * GET /historicoSaldo/lastYear -> get lastYear historicoSaldo
     */
    @RequestMapping (value ="/historicoSaldos/allYears",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SaldoActivoAllYearsDTO> getHistoricoSaldoAllYears(Pageable pageable) {

        // Init SaldoActivoLastYearDTO
        SaldoActivoAllYearsDTO result = new SaldoActivoAllYearsDTO();

        // Get time and date
        ZoneId id = ZoneId.of("Europe/Paris");
        LocalDate now = LocalDate.now(id);

        log.debug("Actual Date {}",now);

        // Get actual year
        int thisYear = now.getYear();

        // Init variables
        List<Double> importesByYear = new ArrayList<Double>();
        List<Integer> allYears = new ArrayList<Integer>();

        // For last 5 years getSaldos
        for (int i = thisYear - 8; i <= thisYear ; i++) {

            // Get year
            LocalDate iYear = now.withYear(i);

            // Get first day of last month of year
            LocalDate firstDayOfLastMonthOfYear = iYear.withMonth(12).withDayOfMonth(1);
            log.debug("First day of year {} is {} ",i, firstDayOfLastMonthOfYear);

            // Get last day of of last month of year
            LocalDate lastDayOfLastMonthOfYear = iYear.withMonth(12).withDayOfMonth(31);
            log.debug("Last day of year {} is {} ",thisYear, lastDayOfLastMonthOfYear);

            // filter by current user and sum importes
            //List<HistoricoSaldo> historicoSaldoByMonth =  historicoSaldoRepository.findAllByFechaBetweenAndUserLogin(firstDayOfMonth, lastDayOfMonth, SecurityUtils.getCurrentUserLogin());
            List<HistoricoSaldo> historicoSaldoByMonth =  historicoSaldoRepository.findAllByFechaBetween(firstDayOfLastMonthOfYear, lastDayOfLastMonthOfYear);
            log.debug("Num of rows {} ",historicoSaldoByMonth.size());

            // Get total amount of this month
            Double saldoTotal = historicoSaldoByMonth.stream().mapToDouble(p -> p.getSaldo().doubleValue()).sum();
            log.debug("Total amount of year {}  = {} ",i);

            // Add this amount to SaldoActivoLastYearDTO
            importesByYear.add(saldoTotal);
            allYears.add(i);
        }

        result.setYears(allYears);
        result.setImportes(importesByYear);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }
}
