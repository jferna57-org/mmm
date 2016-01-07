package net.jk.jhipster.web.rest;

import net.jk.jhipster.Application;
import net.jk.jhipster.domain.HistoricoSaldo;
import net.jk.jhipster.repository.HistoricoSaldoRepository;
import net.jk.jhipster.repository.search.HistoricoSaldoSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the HistoricoSaldoResource REST controller.
 *
 * @see HistoricoSaldoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HistoricoSaldoResourceIntTest {


    private static final Integer DEFAULT_SALDO = 1;
    private static final Integer UPDATED_SALDO = 2;

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_NOTAS = "AAAAA";
    private static final String UPDATED_NOTAS = "BBBBB";

    @Inject
    private HistoricoSaldoRepository historicoSaldoRepository;

    @Inject
    private HistoricoSaldoSearchRepository historicoSaldoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHistoricoSaldoMockMvc;

    private HistoricoSaldo historicoSaldo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HistoricoSaldoResource historicoSaldoResource = new HistoricoSaldoResource();
        ReflectionTestUtils.setField(historicoSaldoResource, "historicoSaldoSearchRepository", historicoSaldoSearchRepository);
        ReflectionTestUtils.setField(historicoSaldoResource, "historicoSaldoRepository", historicoSaldoRepository);
        this.restHistoricoSaldoMockMvc = MockMvcBuilders.standaloneSetup(historicoSaldoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        historicoSaldo = new HistoricoSaldo();
        historicoSaldo.setSaldo(DEFAULT_SALDO);
        historicoSaldo.setFecha(DEFAULT_FECHA);
        historicoSaldo.setNotas(DEFAULT_NOTAS);
    }

    @Test
    @Transactional
    public void createHistoricoSaldo() throws Exception {
        int databaseSizeBeforeCreate = historicoSaldoRepository.findAll().size();

        // Create the HistoricoSaldo

        restHistoricoSaldoMockMvc.perform(post("/api/historicoSaldos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(historicoSaldo)))
                .andExpect(status().isCreated());

        // Validate the HistoricoSaldo in the database
        List<HistoricoSaldo> historicoSaldos = historicoSaldoRepository.findAll();
        assertThat(historicoSaldos).hasSize(databaseSizeBeforeCreate + 1);
        HistoricoSaldo testHistoricoSaldo = historicoSaldos.get(historicoSaldos.size() - 1);
        assertThat(testHistoricoSaldo.getSaldo()).isEqualTo(DEFAULT_SALDO);
        assertThat(testHistoricoSaldo.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testHistoricoSaldo.getNotas()).isEqualTo(DEFAULT_NOTAS);
    }

    @Test
    @Transactional
    public void checkSaldoIsRequired() throws Exception {
        int databaseSizeBeforeTest = historicoSaldoRepository.findAll().size();
        // set the field null
        historicoSaldo.setSaldo(null);

        // Create the HistoricoSaldo, which fails.

        restHistoricoSaldoMockMvc.perform(post("/api/historicoSaldos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(historicoSaldo)))
                .andExpect(status().isBadRequest());

        List<HistoricoSaldo> historicoSaldos = historicoSaldoRepository.findAll();
        assertThat(historicoSaldos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = historicoSaldoRepository.findAll().size();
        // set the field null
        historicoSaldo.setFecha(null);

        // Create the HistoricoSaldo, which fails.

        restHistoricoSaldoMockMvc.perform(post("/api/historicoSaldos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(historicoSaldo)))
                .andExpect(status().isBadRequest());

        List<HistoricoSaldo> historicoSaldos = historicoSaldoRepository.findAll();
        assertThat(historicoSaldos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHistoricoSaldos() throws Exception {
        // Initialize the database
        historicoSaldoRepository.saveAndFlush(historicoSaldo);

        // Get all the historicoSaldos
        restHistoricoSaldoMockMvc.perform(get("/api/historicoSaldos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(historicoSaldo.getId().intValue())))
                .andExpect(jsonPath("$.[*].saldo").value(hasItem(DEFAULT_SALDO)))
                .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
                .andExpect(jsonPath("$.[*].notas").value(hasItem(DEFAULT_NOTAS.toString())));
    }

    @Test
    @Transactional
    public void getHistoricoSaldo() throws Exception {
        // Initialize the database
        historicoSaldoRepository.saveAndFlush(historicoSaldo);

        // Get the historicoSaldo
        restHistoricoSaldoMockMvc.perform(get("/api/historicoSaldos/{id}", historicoSaldo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(historicoSaldo.getId().intValue()))
            .andExpect(jsonPath("$.saldo").value(DEFAULT_SALDO))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.notas").value(DEFAULT_NOTAS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHistoricoSaldo() throws Exception {
        // Get the historicoSaldo
        restHistoricoSaldoMockMvc.perform(get("/api/historicoSaldos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistoricoSaldo() throws Exception {
        // Initialize the database
        historicoSaldoRepository.saveAndFlush(historicoSaldo);

		int databaseSizeBeforeUpdate = historicoSaldoRepository.findAll().size();

        // Update the historicoSaldo
        historicoSaldo.setSaldo(UPDATED_SALDO);
        historicoSaldo.setFecha(UPDATED_FECHA);
        historicoSaldo.setNotas(UPDATED_NOTAS);

        restHistoricoSaldoMockMvc.perform(put("/api/historicoSaldos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(historicoSaldo)))
                .andExpect(status().isOk());

        // Validate the HistoricoSaldo in the database
        List<HistoricoSaldo> historicoSaldos = historicoSaldoRepository.findAll();
        assertThat(historicoSaldos).hasSize(databaseSizeBeforeUpdate);
        HistoricoSaldo testHistoricoSaldo = historicoSaldos.get(historicoSaldos.size() - 1);
        assertThat(testHistoricoSaldo.getSaldo()).isEqualTo(UPDATED_SALDO);
        assertThat(testHistoricoSaldo.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testHistoricoSaldo.getNotas()).isEqualTo(UPDATED_NOTAS);
    }

    @Test
    @Transactional
    public void deleteHistoricoSaldo() throws Exception {
        // Initialize the database
        historicoSaldoRepository.saveAndFlush(historicoSaldo);

		int databaseSizeBeforeDelete = historicoSaldoRepository.findAll().size();

        // Get the historicoSaldo
        restHistoricoSaldoMockMvc.perform(delete("/api/historicoSaldos/{id}", historicoSaldo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HistoricoSaldo> historicoSaldos = historicoSaldoRepository.findAll();
        assertThat(historicoSaldos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
