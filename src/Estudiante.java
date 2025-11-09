public class Estudiante extends Usuario{
    private String carrera;

    public Estudiante(String carrera) {
        this.carrera = carrera;
    }

    @Override
    public String getCarrera() {
        return carrera;
    }

    @Override
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
}

