public class Usuario {
    private String nombre_completo;
    private String rut;
    private char genero;
    private String carrera;
    private String prestamo;

    public Usuario(String nombre_completo, String rut, char genero, String carrera) {
        this.nombre_completo = nombre_completo;
        this.rut = rut;
        this.genero = genero;
        this.carrera = carrera;
        this.prestamo = "0";
    }

    public Usuario() {
        this.prestamo = "0";
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(String prestamo) {
        this.prestamo = prestamo;
    }

    public boolean validar_genero(char genero){
        if(genero=='M'){
            return true;
        } else if(genero=='F'){
            return true;
        } else {
            return false;
        }
    }
}

