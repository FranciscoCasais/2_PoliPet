import java.time.LocalDate;
public class Solicitud {
    private Animal animal;
    private Estado estado;
    private LocalDate fechaAdopcion,fechaEnvio;
    private Persona solicitante;
    public Solicitud() {
        this.animal=new Animal();
        this.estado=null;
        this.fechaAdopcion=null;
        this.fechaEnvio=null;
        this.solicitante=new Persona();
    }
    public Solicitud(Persona solicitante, Animal animal, LocalDate fechaEnvio, Estado estado, LocalDate fechaAdopcion) {
        this.animal=animal;
        this.estado=estado;
        this.fechaAdopcion=fechaAdopcion;
        this.fechaEnvio=fechaEnvio;
        this.solicitante=solicitante;
    }
    public Animal getAnimal() {
        return animal;
    }
    public Estado getEstado() {
        return estado;
    }
    public LocalDate getFechaAdopcion() {
        return fechaAdopcion;
    }
    public LocalDate getFechaEnvio() {
        return fechaEnvio;
    }
    public Persona getSolicitante() {
        return solicitante;
    }
    public void setAnimal(Animal animal) {
        this.animal=animal;
    }
    public void setEstado(Estado estado) {
        this.estado=estado;
    }
    public void setFechaAdopcion(LocalDate fechaAdopcion) {
        this.fechaAdopcion = fechaAdopcion;
    }
    public void setFechaEnvio(LocalDate fechaEnvio) {
        this.fechaEnvio=fechaEnvio;
    }
    public void setSolicitante(Persona solicitante) {
        this.solicitante=solicitante;
    }

    @Override
    public String toString() {
        return  "solicitante: " + solicitante.getNombre() + ", animal: " + animal.getNombre() + ", fechaAdopcion: " + fechaAdopcion;
    }
}