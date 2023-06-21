import java.time.LocalDate;
public class Vacuna {
    private String nombre;
    private LocalDate fecha;
    private String dosis;
    public Vacuna() {
        this.fecha=null;
        this.dosis="";
        this.nombre="";
    }
    public Vacuna(LocalDate fecha,String dosis, String nombre) {
        this.fecha=fecha;
        this.dosis=dosis;
        this.nombre=nombre;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public String getDosis() {
        return dosis;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha=fecha;
    }
    public void setDosis(String dosis) {
        this.dosis=dosis;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}