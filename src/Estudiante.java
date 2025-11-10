public class Estudiante extends Usuario {
    private String carrera;

    // usa "rut" para ser consistente
    public Estudiante(String nombre, String rut, char genero, String carrera) {
        super(nombre, rut, genero);
        this.carrera = carrera;
    }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }
}
