import java.util.ArrayList;
import java.util.HashSet;
import java.time.LocalDate;
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

    public void animalesSolicitud(){//A
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
        Boolean verif=true;
        for(Animal an:animales){
            if(verif){
                menor =an;
                verif=false;
            }else {
                if (an.getFechaNacimiento().getYear() > menor.getFechaNacimiento().getYear()) {
                    if (an.getFechaNacimiento().getDayOfYear() > menor.getFechaNacimiento().getDayOfYear()) {
                        menor = an;
                    }
                }
            }
        }
        return menor;
    }
    public HashSet<Solicitud> solicitudesNoValidas(){//C
        HashSet<Solicitud> solicitudes_ = new HashSet<Solicitud>();
        for(Solicitud s : solicitudes){
            if(!s.getSolicitante().mayorDeEdad()){
                if(!s.getAnimal().verificarVacuna()){
                    System.out.println(s.toString());
                    solicitudes_.add(s);
                }
            }
        }
        return solicitudes_;
    }
    public void corregirFechas(){//D
        for (Animal animal : animales){
            for (Vacuna vacuna : animal.getVacunas()){
                vacuna.setFecha(vacuna.getFecha().minusMonths(1));
            }
        }
    }
    public HashSet<Persona> rompeReglas(){//E
        HashSet<Persona> personas_ = new HashSet<Persona>();
        for (Persona persona : personas){
            HashSet<Solicitud> aux = new HashSet<Solicitud>();
            ArrayList<Solicitud> solicitudes_ = new ArrayList<Solicitud>();
            for (Solicitud solicitud : solicitudes)if (solicitud.getSolicitante().equals(persona)) aux.add(solicitud);
            while (aux.size() >= 1){
                Solicitud solicitudMasAntigua = new Solicitud();
                boolean primer = true;
                for (Solicitud solicitud : aux){
                    if (primer){
                        primer = false;
                        solicitudMasAntigua = solicitud;
                    }
                    else if (solicitudMasAntigua.getFechaAdopcion().isBefore(solicitud.getFechaAdopcion())){
                        solicitudMasAntigua = solicitud;
                    }
                }
                aux.remove(solicitudMasAntigua);
                solicitudes_.add(solicitudMasAntigua);
            }
            int anio=0, mes=0, cant=0;
            for (Solicitud solicitud : solicitudes_){
                if (anio == 0 && mes == 0){
                    cant++;
                    anio = solicitud.getFechaAdopcion().getYear();
                    mes = solicitud.getFechaAdopcion().getMonthValue();
                }
                else if (anio == solicitud.getFechaAdopcion().getYear() && mes == solicitud.getFechaAdopcion().getMonthValue()){
                    cant++;
                    if (cant > 1){
                        personas_.add(solicitud.getSolicitante());
                        break;
                    }
                }
                else{
                    cant = 0;
                    anio = solicitud.getFechaAdopcion().getYear();
                    mes = solicitud.getFechaAdopcion().getMonthValue();
                }
            }
        }
        return personas_;
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
