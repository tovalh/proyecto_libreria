import java.util.ArrayList;

public class GestorUsuarios {

    private ArrayList<Usuario> usuarios;

    public GestorUsuarios() {
        this.usuarios = new ArrayList<>();
    }

    //Agrega un usuario
    public boolean agregarUsuario(Usuario usuario) {
        if (usuario == null) {
            System.out.println("Error: usuario nulo");
            return false;
        }

        // Validación de RUT
        if (!Usuario.validarRut(usuario.getRut())) {
            System.out.println("Error: RUN inválido.");
            return false;
        }

        // Validación de género (M / F)
        if (!usuario.validar_genero(usuario.getGenero())) {
            System.out.println("Error: Género inválido (debe ser 'M' o 'F').");
            return false;
        }

        String rutNuevoNorm = normalizarRut(usuario.getRut());
        for (Usuario u : usuarios) {
            if (normalizarRut(u.getRut()).equals(rutNuevoNorm)) {
                System.out.println("Error: RUT ya existe");
                return false;
            }
        }

        usuarios.add(usuario);
        return true;
    }

    //Edita nombre, género y carrera de un usuario existente identificado por RUT.
    public boolean editarUsuarioBasico(String rut, String nuevoNombre, char nuevoGenero) {
        Usuario u = buscarUsuario(rut);
        if (u == null) { System.out.println("Error: Usuario no existe"); return false; }
        if (!u.validar_genero(nuevoGenero)) { System.out.println("Error: Género inválido"); return false; }
        u.setNombre_completo(nuevoNombre);
        u.setGenero(nuevoGenero);
        return true;
    }

    public boolean editarCarreraEstudiante(String rut, String nuevaCarrera) {
        Usuario u = buscarUsuario(rut);
        if (!(u instanceof Estudiante)) { System.out.println("Error: No es estudiante"); return false; }
        ((Estudiante) u).setCarrera(nuevaCarrera);
        return true;
    }

    public boolean editarDatosDocente(String rut, String nuevaProfesion, String nuevoGrado) {
        Usuario u = buscarUsuario(rut);
        if (!(u instanceof Docente)) { System.out.println("Error: No es docente"); return false; }
        Docente d = (Docente) u;
        d.setProfesion(nuevaProfesion);
        d.setGrado(nuevoGrado);
        return true;
    }


    //Elimina un usuario por RUT si existe.
    public boolean eliminarUsuario(String rut) {
        Usuario usuario = buscarUsuario(rut);
        if (usuario == null) {
            System.out.println("Error: Usuario no existe");
            return false;
        }
        return usuarios.remove(usuario);
    }

    //Busca un usuario por RUT
    public Usuario buscarUsuario(String rut) {
        String objetivo = normalizarRut(rut);
        for (Usuario u : usuarios) {
            if (normalizarRut(u.getRut()).equals(objetivo)) {
                return u;
            }
        }
        return null;
    }

    //Normaliza el RUT para comparación:
    private String normalizarRut(String rut) {
        if (rut == null) return "";
        return rut.replace(".", "").replace("-", "").toUpperCase().trim();
    }
}
