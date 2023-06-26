
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
        for (Animal animal : sist.getAnimales()) System.out.println(animal.getNombre());
        /*for(Animal a:sist.getAnimales()){
            System.out.println(a.toString());
        }
        for(Persona p: sist.getPersonas()){
            System.out.println(p.toString());
        }
        for(Solicitud s: sist.getSolicitudes()){
            System.out.println(s.toString());
        }*/
    }
}
