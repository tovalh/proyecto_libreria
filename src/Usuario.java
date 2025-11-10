public class Usuario {
    private String nombre_completo;
    private String rut;
    private char genero;
    private String prestamo;

    public Usuario(String nombre_completo, String rut, char genero) {
        this.nombre_completo = nombre_completo;
        this.rut = rut;
        this.genero = genero;
        this.prestamo = "0";
    }

    public Usuario() {
        this.prestamo = "0";
    }

    public String getNombre_completo() { return nombre_completo; }
    public void setNombre_completo(String nombre_completo) { this.nombre_completo = nombre_completo; }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public char getGenero() { return genero; }
    public void setGenero(char genero) { this.genero = genero; }

    public String getPrestamo() { return prestamo; }
    public void setPrestamo(String prestamo) { this.prestamo = prestamo; }

    public boolean validar_genero(char genero) {
        return genero == 'M' || genero == 'F';
    }

    // Validar RUT
    public static boolean validarRut(String rut) {
        if (rut == null || rut.isEmpty()) return false;

        String clean = rut.replace(".", "").trim().toUpperCase();
        return clean.matches("\\d{7,8}-[0-9K]");
    }
}
