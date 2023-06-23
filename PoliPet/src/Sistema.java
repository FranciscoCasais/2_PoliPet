import java.util.HashSet;
public class Sistema {
    private HashSet<Animal> animales;
    private HashSet<Persona> personas;
    private HashSet<Solicitud> solicitudes;
    public Sistema() {
        this.animales=new HashSet<Animal>();
        this.personas=new HashSet<Persona>();
        this.solicitudes=new HashSet<Solicitud>();
    }
    public Sistema(HashSet<Animal> animales,HashSet<Persona> personas,HashSet<Solicitud> solicitudes) {
        this.animales=animales;
        this.personas=personas;
        this.solicitudes=solicitudes;
    }
    private AccesoDB accesoDB;
    public HashSet<Animal> getAnimales() {
        return animales;
    }
    public HashSet<Persona> getPersonas() {
        return personas;
    }
    public HashSet<Solicitud> getSolicitudes() {
        return solicitudes;
    }
    public void setAnimales(HashSet<Animal> animales) {
        this.animales=animales;
    }
    public void setPersonas(HashSet<Persona> personas) {
        this.personas=personas;
    }
    public void setSolicitudes(HashSet<Solicitud> solicitudes) {
        this.solicitudes=solicitudes;
    }

    public void animalesSolicitud(){
        HashSet<Animal> animalesSol=new HashSet<Animal>();//Es un HashSet por si hay varias solicutudes con el mismo animal
        for(Animal an : animales){
            for(Solicitud sol : solicitudes){
                if(sol.getAnimal()==an){
                    animalesSol.add(an);
                }
            }
        }
        for(Animal a : animalesSol){
            System.out.println(a.toString());
        }
    }
    public Animal animalMenorEdad(){//B
        Animal menor=new Animal();
        int edad=0;
        int primero=Integer.MAX_VALUE;
        for(Animal an:animales){
            edad=an.calcularEdad();
            if(edad<=primero){
                menor=an;
                primero=edad;
            }
        }
        return menor;
    }
    public void solicitudesNoValidas(){
        for(Solicitud s : solicitudes){
            if(!s.getSolicitante().mayorDeEdad()){
                if(!s.getAnimal().verificarVacuna()){
                    System.out.println(s.toString());
                }
            }
        }
          //Si quiere cambienlo a que retorne un HashSet
    }
    public void corregirFechas(){//D
        for (Animal animal : animales){
            for (Vacuna vacuna : animal.getVacunas()){
                vacuna.setFecha(vacuna.getFecha().minusMonths(1));
            }
        }
    }
    public HashSet<Persona> personasExperimentadas(){//F
        HashSet<Persona> personasExperimentadas_ = new HashSet<Persona>();
        for (Solicitud solicitud : solicitudes) if (solicitud.getSolicitante().getExperiencia()) personasExperimentadas_.add(solicitud.getSolicitante());
        return personasExperimentadas_;
    }
    public HashSet<String> especiesSolicitadas(){//G
        HashSet<String> especies = new HashSet<String>();
        for (Solicitud solicitud : solicitudes) if (!especies.contains(solicitud.getAnimal().getEspecie())) especies.add(solicitud.getAnimal().getEspecie());
        return especies;
    }
     public Animal animalMasReciente(){//H
        Animal masReciente = null;
        LocalDate fecha = null;
        for(Solicitud s: solicitudes){
            if(masReciente==null){
                masReciente=s.getAnimal();
                fecha=s.getFechaAdopcion();
            }else if(s.getEstado()==Estado.APROBADA && s.getFechaAdopcion().isAfter(fecha)){
                masReciente=s.getAnimal();
                fecha=s.getFechaAdopcion();
            }
        }
        return masReciente;
    }
}
