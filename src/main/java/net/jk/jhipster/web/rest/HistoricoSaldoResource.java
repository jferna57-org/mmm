package net.jk.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.jk.jhipster.domain.HistoricoSaldo;
import net.jk.jhipster.repository.HistoricoSaldoRepository;
import net.jk.jhipster.repository.search.HistoricoSaldoSearchRepository;
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
}
