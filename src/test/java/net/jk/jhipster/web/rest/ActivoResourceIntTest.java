package net.jk.jhipster.web.rest;

import net.jk.jhipster.Application;
import net.jk.jhipster.domain.Activo;
import net.jk.jhipster.repository.ActivoRepository;
import net.jk.jhipster.repository.search.ActivoSearchRepository;

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
 * Test class for the ActivoResource REST controller.
 *
 * @see ActivoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ActivoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAA";
    private static final String UPDATED_NOMBRE = "BBBB";
    private static final String DEFAULT_DESCRIPCION = "AAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBB";

    private static final Integer DEFAULT_SALDO = 1;
    private static final Integer UPDATED_SALDO = 2;

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ACTIVO = 0;
    private static final Integer UPDATED_ACTIVO = 1;
    private static final String DEFAULT_NOTAS = "AAAAA";
    private static final String UPDATED_NOTAS = "BBBBB";

    private static final LocalDate DEFAULT_FECHA_ALTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ALTA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_BAJA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_BAJA = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private ActivoRepository activoRepository;

    @Inject
    private ActivoSearchRepository activoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restActivoMockMvc;

    private Activo activo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ActivoResource activoResource = new ActivoResource();
        ReflectionTestUtils.setField(activoResource, "activoSearchRepository", activoSearchRepository);
        ReflectionTestUtils.setField(activoResource, "activoRepository", activoRepository);
        this.restActivoMockMvc = MockMvcBuilders.standaloneSetup(activoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        activo = new Activo();
        activo.setNombre(DEFAULT_NOMBRE);
        activo.setDescripcion(DEFAULT_DESCRIPCION);
        activo.setSaldo(DEFAULT_SALDO);
        activo.setFecha(DEFAULT_FECHA);
        activo.setActivo(DEFAULT_ACTIVO);
        activo.setNotas(DEFAULT_NOTAS);
        activo.setFechaAlta(DEFAULT_FECHA_ALTA);
        activo.setFechaBaja(DEFAULT_FECHA_BAJA);
    }

    @Test
    @Transactional
    public void createActivo() throws Exception {
        int databaseSizeBeforeCreate = activoRepository.findAll().size();

        // Create the Activo

        restActivoMockMvc.perform(post("/api/activos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activo)))
                .andExpect(status().isCreated());

        // Validate the Activo in the database
        List<Activo> activos = activoRepository.findAll();
        assertThat(activos).hasSize(databaseSizeBeforeCreate + 1);
        Activo testActivo = activos.get(activos.size() - 1);
        assertThat(testActivo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testActivo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testActivo.getSaldo()).isEqualTo(DEFAULT_SALDO);
        assertThat(testActivo.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testActivo.getActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testActivo.getNotas()).isEqualTo(DEFAULT_NOTAS);
        assertThat(testActivo.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testActivo.getFechaBaja()).isEqualTo(DEFAULT_FECHA_BAJA);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = activoRepository.findAll().size();
        // set the field null
        activo.setNombre(null);

        // Create the Activo, which fails.

        restActivoMockMvc.perform(post("/api/activos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activo)))
                .andExpect(status().isBadRequest());

        List<Activo> activos = activoRepository.findAll();
        assertThat(activos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSaldoIsRequired() throws Exception {
        int databaseSizeBeforeTest = activoRepository.findAll().size();
        // set the field null
        activo.setSaldo(null);

        // Create the Activo, which fails.

        restActivoMockMvc.perform(post("/api/activos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activo)))
                .andExpect(status().isBadRequest());

        List<Activo> activos = activoRepository.findAll();
        assertThat(activos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = activoRepository.findAll().size();
        // set the field null
        activo.setFecha(null);

        // Create the Activo, which fails.

        restActivoMockMvc.perform(post("/api/activos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activo)))
                .andExpect(status().isBadRequest());

        List<Activo> activos = activoRepository.findAll();
        assertThat(activos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = activoRepository.findAll().size();
        // set the field null
        activo.setActivo(null);

        // Create the Activo, which fails.

        restActivoMockMvc.perform(post("/api/activos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activo)))
                .andExpect(status().isBadRequest());

        List<Activo> activos = activoRepository.findAll();
        assertThat(activos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaAltaIsRequired() throws Exception {
        int databaseSizeBeforeTest = activoRepository.findAll().size();
        // set the field null
        activo.setFechaAlta(null);

        // Create the Activo, which fails.

        restActivoMockMvc.perform(post("/api/activos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activo)))
                .andExpect(status().isBadRequest());

        List<Activo> activos = activoRepository.findAll();
        assertThat(activos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActivos() throws Exception {
        // Initialize the database
        activoRepository.saveAndFlush(activo);

        // Get all the activos
        restActivoMockMvc.perform(get("/api/activos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(activo.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
                .andExpect(jsonPath("$.[*].saldo").value(hasItem(DEFAULT_SALDO)))
                .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
                .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO)))
                .andExpect(jsonPath("$.[*].notas").value(hasItem(DEFAULT_NOTAS.toString())))
                .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
                .andExpect(jsonPath("$.[*].fechaBaja").value(hasItem(DEFAULT_FECHA_BAJA.toString())));
    }

    @Test
    @Transactional
    public void getActivo() throws Exception {
        // Initialize the database
        activoRepository.saveAndFlush(activo);

        // Get the activo
        restActivoMockMvc.perform(get("/api/activos/{id}", activo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(activo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.saldo").value(DEFAULT_SALDO))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO))
            .andExpect(jsonPath("$.notas").value(DEFAULT_NOTAS.toString()))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()))
            .andExpect(jsonPath("$.fechaBaja").value(DEFAULT_FECHA_BAJA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActivo() throws Exception {
        // Get the activo
        restActivoMockMvc.perform(get("/api/activos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivo() throws Exception {
        // Initialize the database
        activoRepository.saveAndFlush(activo);

		int databaseSizeBeforeUpdate = activoRepository.findAll().size();

        // Update the activo
        activo.setNombre(UPDATED_NOMBRE);
        activo.setDescripcion(UPDATED_DESCRIPCION);
        activo.setSaldo(UPDATED_SALDO);
        activo.setFecha(UPDATED_FECHA);
        activo.setActivo(UPDATED_ACTIVO);
        activo.setNotas(UPDATED_NOTAS);
        activo.setFechaAlta(UPDATED_FECHA_ALTA);
        activo.setFechaBaja(UPDATED_FECHA_BAJA);

        restActivoMockMvc.perform(put("/api/activos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activo)))
                .andExpect(status().isOk());

        // Validate the Activo in the database
        List<Activo> activos = activoRepository.findAll();
        assertThat(activos).hasSize(databaseSizeBeforeUpdate);
        Activo testActivo = activos.get(activos.size() - 1);
        assertThat(testActivo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testActivo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testActivo.getSaldo()).isEqualTo(UPDATED_SALDO);
        assertThat(testActivo.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testActivo.getActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testActivo.getNotas()).isEqualTo(UPDATED_NOTAS);
        assertThat(testActivo.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testActivo.getFechaBaja()).isEqualTo(UPDATED_FECHA_BAJA);
    }

    @Test
    @Transactional
    public void deleteActivo() throws Exception {
        // Initialize the database
        activoRepository.saveAndFlush(activo);

		int databaseSizeBeforeDelete = activoRepository.findAll().size();

        // Get the activo
        restActivoMockMvc.perform(delete("/api/activos/{id}", activo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Activo> activos = activoRepository.findAll();
        assertThat(activos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
