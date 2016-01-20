package net.jk.jhipster.web.rest.dto;

import java.time.Year;
import java.util.List;

/**
 * Created by jk on 19/01/16.
 */
public class SaldoActivoAllYearsDTO {

    List<Integer> years;

    List<Double> importes;


    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public List<Double> getImportes() {
        return importes;
    }

    public void setImportes(List<Double> importes) {
        this.importes = importes;
    }

    @Override
    public String toString() {
        return "SaldoActivoAllYearsDTO{" +
            "years=" + years +
            ", importes=" + importes +
            '}';
    }

}
