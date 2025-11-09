import java.util.ArrayList;

public class GestorUsuarios {
    private ArrayList<Usuario> usuarios;

    public GestorUsuarios() {
        this.usuarios = new ArrayList<>();
    }

    public boolean agregarUsuario(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getRut().equals(usuario.getRut())) {
                System.out.println("Error: RUT ya existe");
                return false;
            }
        }
        usuarios.add(usuario);
        return true;
    }

    public boolean editarUsuario(String rut, String nuevoNombre, char nuevoGenero, String nuevaCarrera) {
        Usuario usuario = buscarUsuario(rut);
        if (usuario == null) {
            System.out.println("Error: Usuario no existe");
            return false;
        }

        usuario.setNombre_completo(nuevoNombre);
        usuario.setGenero(nuevoGenero);
        usuario.setCarrera(nuevaCarrera);

        return true;
    }

    public boolean eliminarUsuario(String rut) {
        Usuario usuario = buscarUsuario(rut);
        if (usuario == null) {
            System.out.println("Error: Usuario no existe");
            return false;
        }
        return usuarios.remove(usuario);
    }

    public Usuario buscarUsuario(String rut) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getRut().equals(rut)) {
                return usuarios.get(i);
            }
        }
        return null;
    }
}