import java.util.HashSet;
import java.util.Date;

public class Animal {
    private HashSet<Vacuna> vacunas;
    private Date fechaNacimiento;
    private String descripcion;
    private String especie;
    private String genero;
    private String nombre;
    private String raza;

    public HashSet<Vacuna> getVacunas() {
        return vacunas;
    }

    public void setVacunas(HashSet<Vacuna> vacunas) {
        this.vacunas = vacunas;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Animal(HashSet<Vacuna> vacunas, Date fechaNacimiento, String descripcion, String especie, String genero, String nombre, String raza) {
        this.vacunas = vacunas;
        this.fechaNacimiento = fechaNacimiento;
        this.descripcion = descripcion;
        this.especie = especie;
        this.genero = genero;
        this.nombre = nombre;
        this.raza = raza;
    }
    public Animal() {
        this.vacunas = new HashSet<Vacuna>();
        this.fechaNacimiento = new Date();
        this.descripcion = "";
        this.especie = "";
        this.genero = "";
        this.nombre = "";
        this.raza = "";
    }

    public Boolean verificarVacuna(){
        Boolean verif=false;
        return verif;
    }
}
