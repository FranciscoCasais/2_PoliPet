import java.util.Date;
public class Solicitud {
    private Persona solicitante;
    private Animal animal;
    private Date fechaEnvio;
    private Estado estado;
    private Date fechaAdopcion;

    public Persona getSolicitante() {
        return solicitante;
    }
    public void setSolicitante(Persona solicitante) {
        this.solicitante = solicitante;
    }
    public Animal getAnimal() {
        return animal;
    }
    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
    public Date getFechaEnvio() {
        return fechaEnvio;
    }
    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
    public Estado getEstado() {
        return estado;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    public Date getFechaAdopcion() {
        return fechaAdopcion;
    }
    public void setFechaAdopcion(Date fechaAdopcion) {
        this.fechaAdopcion = fechaAdopcion;
    }

    public Solicitud(Persona solicitante, Animal animal, Date fechaEnvio, Estado estado, Date fechaAdopcion) {
        this.solicitante = solicitante;
        this.animal = animal;
        this.fechaEnvio = fechaEnvio;
        this.estado = estado;
        this.fechaAdopcion = fechaAdopcion;
    }
    public Solicitud() {
        this.solicitante = new Persona();
        this.animal = new Animal();
        this.fechaEnvio = new Date();
        this.estado = null;
        this.fechaAdopcion = new Date();
    }
}
