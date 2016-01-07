package net.jk.jhipster.repository.search;

import net.jk.jhipster.domain.Activo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Activo entity.
 */
public interface ActivoSearchRepository extends ElasticsearchRepository<Activo, Long> {
}
