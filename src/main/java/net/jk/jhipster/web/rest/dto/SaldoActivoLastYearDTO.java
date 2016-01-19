package net.jk.jhipster.web.rest.dto;

import java.util.List;

/**
 * Created by jk on 19/01/16.
 */
public class SaldoActivoLastYearDTO {

    List<Double> importesByMonth;

    public SaldoActivoLastYearDTO(List<Double> importesByMonth) {
        this.importesByMonth = importesByMonth;
    }

    public List<Double> getImportesByMonth() {
        return importesByMonth;
    }

    public void setImportesByMonth(List<Double> importesByMonth) {
        this.importesByMonth = importesByMonth;
    }

    @Override
    public String toString() {
        return "SaldoActivoLastYearDTO{" +
            "importesByMonth=" + importesByMonth +
            '}';
    }
}
