
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<String> tablas = Arrays.asList("Estado", "Persona", "Solicitud", "Animal", "DetallesVacuna", "Vacuna");

        AccesoDB bdd = new AccesoDB("PoliPet", tablas);
        try {
            bdd.conectar("alumno","alumnoipm");

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        Sistema sist=new Sistema();

        sist.setAnimales(bdd.obtenerValoresAnimal());
        sist.setPersonas(bdd.obtenerValoresPersona());
        sist.setSolicitudes(bdd.obtenerValoresSolicitud(sist.getAnimales(), sist.getPersonas()));
        Animal animal = sist.animalMenorEdad();
        HashSet<Solicitud> solicituds = sist.solicitudesNoValidas();

        System.out.println("\nEjercicio A: Lista de todos los animales presentes en una solicitud de adopcion");
        sist.animalesSolicitud();

        System.out.println("\nEjercicio B: El animal de menor edad disponible para adopcion");
        System.out.println(animal.toString());

        System.out.println("\nEjercicio C: Lista de solicitudes no validas");
        if(solicituds.size()==0){
            System.out.println("No hay valores");
        }
        else {
            for (Solicitud s : solicituds) {
                System.out.println(s.toString());
            }
        }

        System.out.println("\nEjercicio D: Corrige las fechas mal cargadas");
        sist.corregirFechas();

        System.out.println("\nEjercicio E: Muestra las personas que rompen la regla de adoptar una vez al mes");
        HashSet<Persona> rompReg=sist.rompeRegla();
        if(rompReg.size()==0){
            System.out.println("No hay valores");
        }
        else {
            for (Persona p : rompReg) {
                System.out.println(p.toString());
            }
        }

        System.out.println("\nEjercicio F: Muestra las personas con experiencia en el cuidado de mascotas por solicitud de adopción");
        HashSet<Persona> experimentads=sist.personasExperimentadas();
        if(experimentads.size()==0){
            System.out.println("No hay valores");
        }
        else {
            for (Persona p : experimentads) {
                System.out.println(p.toString());
            }
        }

        System.out.println("\nEjercicio G: Muestra las species de animales en cada solicitud de adopción");
        HashSet<String> especies=sist.especiesSolicitadas();
        if(especies.size()==0){
            System.out.println("No hay valores");
        }
        else {
            for (String s : especies) {
                System.out.println(s);
            }
        }

        System.out.println("\nEjercicio H: Muestra el animal más recientemente adoptado en el sistema");
        System.out.println(sist.animalMasReciente().toString());
    }
}
