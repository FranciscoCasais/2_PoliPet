import java.time.LocalDate;
import java.util.HashSet;
public class Animal {
    private HashSet<Vacuna> vacunas;
    private LocalDate fechaNacimiento;
    private String descripcion,especie,genero,nombre,raza;
    public Animal() {
        this.vacunas=new HashSet<Vacuna>();
        this.fechaNacimiento=null;
        this.descripcion="";
        this.especie="";
        this.genero="";
        this.nombre="";
        this.raza="";
    }
    public Animal(HashSet<Vacuna> vacunas,LocalDate fechaNacimiento,String descripcion,String especie,String genero,String nombre,String raza) {
        this.vacunas=vacunas;
        this.fechaNacimiento=fechaNacimiento;
        this.descripcion=descripcion;
        this.especie=especie;
        this.genero=genero;
        this.nombre=nombre;
        this.raza=raza;
    }
    public HashSet<Vacuna> getVacunas() {
        return vacunas;
    }
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String getEspecie() {
        return especie;
    }
    public String getGenero() {
        return genero;
    }
    public String getNombre() {
        return nombre;
    }
    public String getRaza() {
        return raza;
    }
    public void setVacunas(HashSet<Vacuna> vacunas) {
        this.vacunas=vacunas;
    }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento=fechaNacimiento;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion=descripcion;
    }
    public void setEspecie(String especie) {
        this.especie=especie;
    }
    public void setGenero(String genero) {
        this.genero=genero;
    }
    public void setNombre(String nombre) {
        this.nombre=nombre;
    }
    public void setRaza(String raza) {
        this.raza=raza;
    }

    public int calcularEdad() { // Calcula la edad en años de un animal con su fecha de nacimiento
        int edad;
        if(LocalDate.now().getDayOfYear()>=fechaNacimiento.getDayOfYear()){
            edad=LocalDate.now().getYear()-fechaNacimiento.getYear();
        }
        else{
            edad=LocalDate.now().getYear()-fechaNacimiento.getYear()-1;
        }
        return edad;
    }
    @Override
    public String toString() {
        return "Nombre: " + nombre + ", especie: " + especie + ", raza: " + raza + ", genero: " + genero + ", edad: " + calcularEdad() + " años";
    }

    public Boolean verificarVacuna() { // Verifica si el animal tiene alguna vacuna aplicada
        for(Vacuna vacuna:vacunas) {
            if(vacuna.getFecha().getYear()==LocalDate.now().getYear()) { return true; }
        }
        return false;
    }
    public boolean esIgual(HashSet<Vacuna> vacunas,LocalDate fechaNacimiento,String descripcion,String especie,String genero,String nombre,String raza) { // Verifica si dos animales son iguales
        if (this.fechaNacimiento.equals(fechaNacimiento) && this.descripcion.equals(descripcion) && this.especie.equals(especie) && this.genero.equals(genero) && this.nombre.equals(nombre) && this.raza.equals(raza)){
            return true;
        }
        return false;
    }
}