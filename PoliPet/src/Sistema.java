import java.util.ArrayList;
import java.util.HashSet;
import java.time.LocalDate;
import java.time.Period;
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
        HashSet<Solicitud> solicitudes_ = new HashSet<Solicitud>();//O(1)
        for(Solicitud s : solicitudes){
            if(!(s.getSolicitante().mayorDeEdad()) || !(s.getAnimal().verificarVacuna())){
                //System.out.println(s.toString());//O(1)
                solicitudes_.add(s);//O(1)
            }//O(1)
        }//O(n)     n = cantidad de solicitudes
        return solicitudes_;//O(1)
    }//O(n)
    public void corregirFechas(){//D
        for (Animal animal : animales){
            for (Vacuna vacuna : animal.getVacunas()){
                vacuna.setFecha(vacuna.getFecha().minusMonths(1));//O(1)
            }//O(o)     o = cantidad de vacunas por persona
        }//O(m * o)     m = cantidad de animales
    }//O(m * o)
       public HashSet<Persona> rompeRegla(){//E
            HashSet<Persona> lista = new HashSet<Persona>();//O(1)
            for(Solicitud s : solicitudes){
                for(Solicitud solicitud : solicitudes){
                    if(s.getSolicitante() == solicitud.getSolicitante() && !s.equals(solicitud)){
                        Period periodo;//O(1)
                        if(solicitud.getFechaAdopcion().isAfter(s.getFechaAdopcion())){//O(1)
                            periodo=s.getFechaAdopcion().until(solicitud.getFechaAdopcion());//O(1)
                        }else{
                            periodo=solicitud.getFechaAdopcion().until(s.getFechaAdopcion());//O(1)
                        }//O(1)
                        if(periodo.getMonths()>=1 && !lista.contains(s.getSolicitante())){
                            lista.add(s.getSolicitante());//O(1)
                        }//O(1)
                    }//O(1)
                }//O(n)
            }//O(n * n)
            return lista;//O(1)
        }//O(n * n)
    public HashSet<Persona> personasExperimentadas(){//F
        HashSet<Persona> personasExperimentadas_ = new HashSet<Persona>();//O(1)
        for (Solicitud solicitud : solicitudes) if (solicitud.getSolicitante().getExperiencia()) personasExperimentadas_.add(solicitud.getSolicitante());//O(n)
        return personasExperimentadas_;//O(1)
    }//O(n)
    public HashSet<String> especiesSolicitadas(){//G
        HashSet<String> especies = new HashSet<String>();//O(1)
        for (Solicitud solicitud : solicitudes) if (!especies.contains(solicitud.getAnimal().getEspecie())) especies.add(solicitud.getAnimal().getEspecie());//O(n)
        return especies;//O(1)
    }//O(n)
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
