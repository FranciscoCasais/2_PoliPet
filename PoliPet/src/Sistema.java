import java.sql.SQLException;
import java.util.*;
import java.time.LocalDate;
import java.time.Period;

public class Sistema {
    private HashSet<Animal> animales;
    private HashSet<Persona> personas;
    private HashSet<Solicitud> solicitudes;
    private AccesoDB accesoDB;
    public Sistema() {
        this.animales=new HashSet<Animal>();
        this.personas=new HashSet<Persona>();
        this.solicitudes=new HashSet<Solicitud>();

        List<String> tablas = Arrays.asList("Estado", "Persona", "Solicitud", "Animal", "DetallesVacuna", "Vacuna");
        accesoDB = new AccesoDB("PoliPet", tablas);
        try { accesoDB.conectar("alumno","alumnoipm"); }
        catch (SQLException ex) { System.out.println(ex); }
    }
    public Sistema(HashSet<Animal> animales,HashSet<Persona> personas,HashSet<Solicitud> solicitudes) {
        this.animales=animales;
        this.personas=personas;
        this.solicitudes=solicitudes;

        List<String> tablas = Arrays.asList("Estado", "Persona", "Solicitud", "Animal", "DetallesVacuna", "Vacuna");
        accesoDB = new AccesoDB("PoliPet", tablas);
        try { accesoDB.conectar("alumno","alumnoipm"); }
        catch (SQLException ex) { System.out.println(ex); }
    }

    public AccesoDB getAccesoDB() {
        return accesoDB;
    }
    public void setAccesoDB(AccesoDB accesoDB) {
        this.accesoDB = accesoDB;
    }
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
    public Animal buscarAnimal(LocalDate fechaNacimiento,String descripcion,String especie,String genero,String nombre,String raza){ // Se le ingresan todos los datos de un animal y busca un animal en Java que coincida
        for (Animal animal : animales){
            if (animal.esIgual(fechaNacimiento, descripcion, especie, genero, nombre, raza)){
                return animal;
            }
        }
        return new Animal();
    }
    public Persona buscarPersona(boolean experiencia,LocalDate nacimiento,String correo,String direccion,String nombre,String ocupacion,String telefono){ // Se le ingresan todos los datos de una persona y busca una persona en Java que coincida
        for (Persona persona : personas){
            if (persona.esIgual(experiencia, nacimiento, correo, direccion, nombre, ocupacion, telefono)){
                return persona;
            }
        }
        return new Persona();
    }
    public void cargarDatos(){
        HashMap<Integer, HashMap<String, Object>> animales = accesoDB.obtenerValoresAnimal();
        for (Map.Entry<Integer, HashMap<String, Object>> animal : animales.entrySet()){
            HashSet <Vacuna> vacunas = new HashSet<Vacuna>();
            for (Object vacuna : ((HashSet<Object>) animal.getValue().get("Vacunas"))){
                LocalDate fecha_ = LocalDate.now();
                String dosis_ = "";
                String nombre_ = "";
                for (Map.Entry<String, Object> obj : ((HashMap<String, Object>) vacuna).entrySet()){
                    if (obj.getKey().equals("Fecha")) fecha_ = ((LocalDate) obj.getValue());
                    else if (obj.getKey().equals("Dosis")) dosis_ = ((String) obj.getValue());
                    else if (obj.getKey().equals("Nombre")) nombre_ = ((String) obj.getValue());
                }
                vacunas.add(new Vacuna(fecha_, dosis_, nombre_));
            }
            this.animales.add(new Animal (vacunas, ((LocalDate) animal.getValue().get("Fecha_nacimiento")), ((String) animal.getValue().get("Descripcion")), ((String) animal.getValue().get("Especie")), ((String) animal.getValue().get("Genero")), ((String) animal.getValue().get("Nombre")), ((String) animal.getValue().get("Raza"))));
        }

        HashMap<Integer, HashMap<String, Object>> personas = accesoDB.obtenerValoresPersona();
        for (Map.Entry<Integer, HashMap<String, Object>> persona : personas.entrySet()){
            this.personas.add(new Persona(((boolean) persona.getValue().get("Experiencia_previa")), ((LocalDate) persona.getValue().get("Fecha_nacimiento")), ((String) persona.getValue().get("Email")), ((String) persona.getValue().get("Direccion")), ((String) persona.getValue().get("Nombre_completo")), ((String) persona.getValue().get("Ocupacion")), ((String) persona.getValue().get("Telefono"))));
        }

        HashMap<ArrayList<Integer>, HashMap<String, Object>> solicitudes = accesoDB.obtenerValoresSolicitud();{
            Persona persona = new Persona();
            Animal animal = new Animal();
            for (Map.Entry<ArrayList<Integer>, HashMap<String, Object>> solicitud : solicitudes.entrySet()){
                for (Map.Entry<Integer, HashMap<String, Object>> persona_ : accesoDB.obtenerPersonaPorIdEnJava(solicitud.getKey().get(0)).entrySet()){
                    persona = buscarPersona(((boolean) persona_.getValue().get("Experiencia_previa")), ((LocalDate) persona_.getValue().get("Fecha_nacimiento")), ((String) persona_.getValue().get("Email")), ((String) persona_.getValue().get("Direccion")), ((String) persona_.getValue().get("Nombre_completo")), ((String) persona_.getValue().get("Ocupacion")), ((String) persona_.getValue().get("Telefono")));
                }
                for (Map.Entry<Integer, HashMap<String, Object>> animal_ : accesoDB.obtenerAnimalPorIdEnJava(solicitud.getKey().get(1)).entrySet()){
                    animal = buscarAnimal(((LocalDate) animal_.getValue().get("Fecha_nacimiento")), ((String) animal_.getValue().get("Descripcion")), ((String) animal_.getValue().get("Especie")), ((String) animal_.getValue().get("Genero")), ((String) animal_.getValue().get("Nombre")), ((String) animal_.getValue().get("Raza")));
                }
                this.solicitudes.add(new Solicitud(persona, animal, ((LocalDate) solicitud.getValue().get("Fecha_envio")), ((Estado) solicitud.getValue().get("Estado")), ((LocalDate) solicitud.getValue().get("Fecha_adopcion"))));
            }
        }
    }
    public HashSet<Animal> animalesPorSolicitudDeAdopcion(){
        HashSet<Animal> animales = new HashSet<>();
        for (Map.Entry<Integer, HashMap<String, Object>> animal_ : accesoDB.procedureA().entrySet()){
            animales.add(buscarAnimal(((LocalDate) animal_.getValue().get("Fecha_nacimiento")), ((String) animal_.getValue().get("Descripcion")), ((String) animal_.getValue().get("Especie")), ((String) animal_.getValue().get("Genero")), ((String) animal_.getValue().get("Nombre")), ((String) animal_.getValue().get("Raza"))));
        }
        return animales;
    }
    public HashMap<String,Animal> menorAnimalPorEspecie() { // B - Devuelve el animal de menor edad disponible para adopción por especie
        HashMap<String,Animal> menorAnimalPorEspecie=new HashMap<>();
        HashSet<String> especiesSolicitadas=especiesSolicitadas();
        for(String especie:especiesSolicitadas) {
            Animal menorEdad=new Animal();
            Boolean entrar=true;
            for(Animal animal:animales) {
                if(animal.getEspecie().equals(especie)) {
                    if(entrar) {
                        entrar=false;
                        menorEdad=animal;
                    } else if(animal.calcularEdad()<menorEdad.calcularEdad()) { menorEdad=animal; }
                }
            }
            menorAnimalPorEspecie.put(especie,menorEdad);
        }
        return menorAnimalPorEspecie;
    }

    public HashSet<Solicitud> solicitudesNoValidas(){ // C - Devuelve las solicitudes que no cumplan los requisitos mínimos
        HashSet<Solicitud> solicitudes_ = new HashSet<Solicitud>();//O(1)
        for(Solicitud s : solicitudes){
            if(!(s.getSolicitante().mayorDeEdad()) || !(s.getAnimal().verificarVacuna())){
                solicitudes_.add(s);//O(1)
            }//O(1)
        }//O(n)     n = cantidad de solicitudes
        return solicitudes_;//O(1)
    }//O(n)
    public void corregirFechas(){ // D - Corrige las fechas mal cargadas en el sistema restándoles un mes
        boolean primer=true;
        for (Animal animal : animales){
            for (Vacuna vacuna : animal.getVacunas()){
                if(primer){
                    System.out.println("Antes: "+vacuna.getFecha());
                    vacuna.setFecha(vacuna.getFecha().minusMonths(1));
                    System.out.println("Despues: "+vacuna.getFecha());
                    primer=false;
                }
                else {
                    vacuna.setFecha(vacuna.getFecha().minusMonths(1));//O(1)
                }
            }//O(o)     o = cantidad de vacunas por persona
        }//O(m * o)     m = cantidad de animales
    }//O(m * o)
       public HashSet<Persona> rompeRegla(){ // E - Devuelve las personas que rompan la regla de no más de una adopción por mes
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
                        if(periodo.getMonths()<=1 && !lista.contains(s.getSolicitante())){
                            lista.add(s.getSolicitante());//O(1)
                        }//O(1)
                    }//O(1)
                }//O(n)
            }//O(n * n)
            return lista;//O(1)
        }//O(n * n)
    public HashSet<Persona> personasExperimentadas(){ // F - Devuelve las personas con experiencia previa
        HashSet<Persona> personasExperimentadas_ = new HashSet<Persona>();//O(1)
        for (Solicitud solicitud : solicitudes) if (solicitud.getSolicitante().getExperiencia()) personasExperimentadas_.add(solicitud.getSolicitante());//O(n)
        return personasExperimentadas_;//O(1)
    }//O(n)
    public HashSet<String> especiesSolicitadas(){ // G - Devuelve la especie correspondiente a cada animal en solicitud de adopción
        HashSet<String> especies = new HashSet<String>();//O(1)
        for (Solicitud solicitud : solicitudes) if (!especies.contains(solicitud.getAnimal().getEspecie())) especies.add(solicitud.getAnimal().getEspecie());//O(n)
        return especies;//O(1)
    }//O(n)
     public Animal animalMasReciente(){ // H - Devuelve el animal en el sistema más recientemente adoptado
        Animal masReciente = null;
        LocalDate fecha = null;
        for(Solicitud s: solicitudes){
            if (masReciente==null && s.getFechaAdopcion()!=null){
                masReciente=s.getAnimal();
                fecha=s.getFechaAdopcion();
            }else if(s.getEstado()==Estado.APROBADA && s.getFechaAdopcion()!=null && s.getFechaAdopcion().isAfter(fecha)){
                masReciente=s.getAnimal();
                fecha=s.getFechaAdopcion();
            }
        }
        System.out.println("Fecha de adopcion: "+fecha);
        return masReciente;
    }
}
