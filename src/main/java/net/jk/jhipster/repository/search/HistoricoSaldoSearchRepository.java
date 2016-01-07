package net.jk.jhipster.repository.search;

import net.jk.jhipster.domain.HistoricoSaldo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HistoricoSaldo entity.
 */
public interface HistoricoSaldoSearchRepository extends ElasticsearchRepository<HistoricoSaldo, Long> {
}
