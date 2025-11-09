public class Docente extends Usuario{
    private String profesion;
    private String grado;

    public Docente(String profesion, String grado) {
        this.profesion = profesion;
        this.grado = grado;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }
}
