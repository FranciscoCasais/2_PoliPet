import java.time.LocalDateTime;
import java.util.Date;

public class Vacuna {
    private Date fecha;
    private String dosis;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public Vacuna(Date fecha, String dosis) {
        this.fecha = fecha;
        this.dosis = dosis;
    }
    public Vacuna() {
        this.fecha = new Date();
        this.dosis = "";
    }
}
