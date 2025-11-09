public class Usuario {
    private String nombre_completo;
    private String rut;
    private char genero;
    private String carrera;
    private String prestamo;

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

    public String validar_genero(char genero){
        if(genero=='M'){
            return "Masculino";
        } else if(genero=='F'){
            return "Femenino";
        } else {
            return "Invalido";
        }
    }
}

