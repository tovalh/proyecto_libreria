public class Docente extends Usuario {
    private String profesion;
    private String grado;

    public Docente(String nombre_completo, String rut, char genero,
                   String profesion, String grado) {
        super(nombre_completo, rut, genero);
        this.profesion = profesion;
        this.grado = grado;
    }

    public String getProfesion() { return profesion; }
    public void setProfesion(String profesion) { this.profesion = profesion; }
    public String getGrado() { return grado; }
    public void setGrado(String grado) { this.grado = grado; }
}
