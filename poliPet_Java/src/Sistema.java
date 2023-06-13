import java.util.HashSet;

public class Sistema {
    private HashSet<Animal> animales;
    private HashSet<Persona> personas;
    private HashSet<Solicitud> solicitudes;

    public HashSet<Animal> getAnimales() {
        return animales;
    }
    public void setAnimales(HashSet<Animal> animales) {
        this.animales = animales;
    }
    public HashSet<Persona> getPersonas() {
        return personas;
    }
    public void setPersonas(HashSet<Persona> personas) {
        this.personas = personas;
    }
    public HashSet<Solicitud> getSolicitudes() {
        return solicitudes;
    }
    public void setSolicitudes(HashSet<Solicitud> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public Sistema(HashSet<Animal> animales, HashSet<Persona> personas, HashSet<Solicitud> solicitudes) {
        this.animales = animales;
        this.personas = personas;
        this.solicitudes = solicitudes;
    }
    public Sistema() {
        this.animales = new HashSet<Animal>();
        this.personas = new HashSet<Persona>();
        this.solicitudes = new HashSet<Solicitud>();
    }
}