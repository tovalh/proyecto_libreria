import java.util.Scanner;
import java.time.LocalDate;

public class Prestamo {
    private Usuario usuario;
    private Libro libro;
    private String fechaPrestamo;
    private int diasPrestamo;
    private String fechaDevolucion;

    //3.1.1: campo para almacenar el ISBN capturado por consola ===
    private String isbnLibro;

    public Prestamo(Usuario usuario, Libro libro, String fechaPrestamo, int diasPrestamo, String fechaDevolucion) {
        this.usuario = usuario;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
        this.diasPrestamo = diasPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    public Prestamo() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public int getDiasPrestamo() {
        return diasPrestamo;
    }

    public void setDiasPrestamo(int diasPrestamo) {
        this.diasPrestamo = diasPrestamo;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    //3.1.1: getter del ISBN capturado
    public String getIsbnLibro() {
        return isbnLibro;
    }

    //3.1.1: captura por consola del ISBN (solo formato, sin validar existencia) ===
    public void ingresarIsbnDesdeConsola(Scanner sc) {
        System.out.print("Ingrese ISBN del libro a prestar: ");
        String input = sc.nextLine().trim();

        // Validación básica de formato (ISBN-10 o ISBN-13, con o sin guiones)
        while (input.isEmpty() || !formatoIsbnBasicoValido(input)) {
            System.out.println("ISBN vacío o con formato incorrecto. Intente nuevamente.");
            System.out.println("Ejemplos: 978-956-123456-7 | 9789561234567 | 956123456X");
            System.out.print("Ingrese ISBN del libro a prestar: ");
            input = sc.nextLine().trim();
        }

        this.isbnLibro = input;
    }

    // Metodo auxiliar: valida estructura del ISBN
    private boolean formatoIsbnBasicoValido(String s) {
        String clean = s.replaceAll("-", "").toUpperCase();
        // ISBN-13: 13 dígitos; ISBN-10: 9 dígitos + (dígito o 'X')
        return clean.matches("\\d{13}") || clean.matches("\\d{9}[\\dX]");
    }

    // Normaliza ISBN para comparar
    private String normalizarIsbn(String s) {
        if (s == null) return "";
        return s.replaceAll("-", "").toUpperCase().trim();
    }

    // 3.1.2: validar libro existente
    public boolean validarLibroExiste(GestorLibros gestorLibros, Scanner sc) {
        while (true) {
            if (this.getIsbnLibro() == null || this.getIsbnLibro().isBlank()) {
                ingresarIsbnDesdeConsola(sc); // reutiliza 3.1.1
            }

            Libro encontrado = gestorLibros.buscarPorIsbn(this.getIsbnLibro());
            if (encontrado != null) {
                this.libro = encontrado;
                System.out.println("Libro encontrado: " + encontrado.getTitulo());
                return true;
            }

            System.out.println("No se encontró un libro con ese ISBN.");
            System.out.print("¿Desea ingresar otro ISBN? (S/N): ");
            String opt = sc.nextLine().trim();
            if (!opt.equalsIgnoreCase("S")) return false;

            this.setLibro(null);
            this.ingresarIsbnDesdeConsola(sc);
        }
    }

    // 3.1.3. El libro debe tener al menos un ejemplar disponible.
    public boolean validarEjemplaresDisponibles(GestorLibros gestorLibros, Scanner sc) {
        while (true) {
            // Si aún no hay libro seleccionado
            if (this.libro == null) {
                ingresarIsbnDesdeConsola(sc);
                boolean existe = validarLibroExiste(gestorLibros, sc);
                if (!existe) return false;
            }

            Integer disponibles = this.libro.getCantidad_disponible();
            if (disponibles != null && disponibles > 0) {
                System.out.println("Ejemplares disponibles: " + disponibles);
                return true;
            }

            // No hay stock disponible para el libro actual
            System.out.println("No hay ejemplares disponibles para este libro.");
            System.out.print("¿Desea ingresar otro ISBN? (S/N): ");
            String opt = sc.nextLine().trim();
            if (!opt.equalsIgnoreCase("S")) {
                return false; // usuario cancela el flujo
            }

            // Reintento con otro ISBN
            this.libro = null;     // limpia selección actual
            this.isbnLibro = null; // fuerza nueva captura
        }
    }

    //Solicita por consola el RUT del usuario que solicita el préstamo.
    public String ingresarRunDesdeConsola(Scanner sc) {
        System.out.print("Ingrese RUT del usuario (ej: 12.345.678-5 o 12345678-5): ");
        String run = sc.nextLine().trim();
        while (run.isEmpty() || !Usuario.validarRut(run)) {
            if (run.isEmpty()) {
                System.out.println("RUT vacío. Intente nuevamente.");
            } else {
                System.out.println("RUT inválido (formato o dígito verificador incorrecto). Intente nuevamente.");
            }
            System.out.print("Ingrese RUT del usuario: ");
            run = sc.nextLine().trim();
        }
        return run;
    }


    // 3.1.4 + 3.1.5 Pide el RUN y valida que el usuario exista en el sistema.
    public boolean ingresarYValidarUsuario(GestorUsuarios gestorUsuarios, Scanner sc) {
        while (true) {
            String runIngresado = ingresarRunDesdeConsola(sc);

            // Búsqueda directa según la implementación actual del GestorUsuarios
            Usuario u = gestorUsuarios.buscarUsuario(runIngresado);

            if (u != null) {
                this.usuario = u;
                System.out.println("Usuario encontrado: " + u.getNombre_completo() + " (RUT " + u.getRut() + ")");
                return true;
            }

            System.out.println("No se encontró un usuario con ese RUT.");
            System.out.print("¿Desea ingresar otro RUT? (S/N): ");
            String opt = sc.nextLine().trim();
            if (!opt.equalsIgnoreCase("S")) {
                return false; // el usuario decidió no continuar
            }
        }
    }

    // 3.1.6. El usuario debe estar habilitado para préstamo.
    public boolean validarUsuarioHabilitado(GestorUsuarios gestorUsuarios, Scanner sc) {
        while (true) {
            // Asegura que ya exista un usuario seleccionado (desde 3.1.4/3.1.5)
            if (this.usuario == null) {
                boolean ok = ingresarYValidarUsuario(gestorUsuarios, sc);
                if (!ok) return false;
            }

            String estado = this.usuario.getPrestamo();
            if ("0".equals(estado)) {
                System.out.println("Usuario habilitado para préstamo.");
                return true;
            }

            // Usuario no habilitado (ya tiene un préstamo activo)
            System.out.println("El usuario ya tiene un préstamo activo (ISBN: " + estado + ").");
            System.out.print("¿Desea ingresar otro RUT? (S/N): ");
            String opt = sc.nextLine().trim();
            if (!opt.equalsIgnoreCase("S")) {
                return false;
            }

            // Reintento con otro RUT
            this.usuario = null;
        }
    }

    // 3.1.7 Marca al usuario como no disponible para nuevo préstamo
    public boolean registrarPrestamoEnUsuario() {
        if (this.usuario == null || this.libro == null) {
            System.out.println("No hay usuario o libro asignado para registrar el préstamo.");
            return false;
        }
        this.usuario.setPrestamo(this.libro.getIsbn());
        System.out.println("Usuario marcado con préstamo activo (ISBN: " + this.libro.getIsbn() + ").");
        return true;
    }

    // 3.1.8 Resta uno a la cantidad de ejemplares disponibles del libro.
    public boolean descontarEjemplarDisponible() {
        if (this.libro == null) {
            System.out.println("No hay libro asignado para descontar ejemplares.");
            return false;
        }
        Integer d = this.libro.getCantidad_disponible();
        if (d == null || d <= 0) {
            System.out.println("No hay ejemplares disponibles para descontar.");
            return false;
        }
        this.libro.setCantidad_disponible(d - 1);
        System.out.println("Ejemplar descontado. Disponibles ahora: " + this.libro.getCantidad_disponible());
        return true;
    }

    // 3.1.9 Asigna la fecha actual del sistema como fecha de préstamo.
    public void registrarFechaPrestamo() {
        LocalDate hoy = LocalDate.now();
        this.fechaPrestamo = hoy.toString();
        System.out.println("Fecha de préstamo registrada automáticamente: " + this.fechaPrestamo);
    }

    //3.1.10 Solicita al usuario ingresar la cantidad de días de préstamo.
    public void ingresarDiasPrestamo(Scanner sc) {
        if (this.usuario == null) {
            System.out.println("No hay usuario asignado para determinar el límite de días.");
            return;
        }

        int limiteMaximo;
        // Determinar el límite según el tipo de usuario
        if (this.usuario instanceof Docente) {
            limiteMaximo = 20;
            System.out.println("Usuario tipo Docente. Período máximo: 20 días.");
        } else if (this.usuario instanceof Estudiante) {
            limiteMaximo = 10;
            System.out.println("Usuario tipo Estudiante. Período máximo: 10 días.");
        } else {
            limiteMaximo = 10;
            System.out.println("Tipo de usuario no reconocido, se usará máximo de 10 días.");
        }

        int dias = 0;
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print("Ingrese la cantidad de días del préstamo: ");
                dias = Integer.parseInt(sc.nextLine().trim());
                if (dias <= 0) {
                    System.out.println("La cantidad de días debe ser mayor a 0.");
                } else if (dias > limiteMaximo) {
                    System.out.println("No puede exceder el máximo de " + limiteMaximo + " días. Intente nuevamente.");
                } else {
                    valido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }

        this.diasPrestamo = dias;
        System.out.println("Días de préstamo asignados: " + dias);
    }

    // 3.1.11 Calcula y registra automáticamente la fecha de devolución
    public void calcularFechaDevolucion() {
        if (this.fechaPrestamo == null || this.fechaPrestamo.isEmpty()) {
            System.out.println("Error: no hay fecha de préstamo registrada.");
            return;
        }

        if (this.diasPrestamo <= 0) {
            System.out.println("Error: los días de préstamo no son válidos.");
            return;
        }

        // Convertir la fecha de préstamo (String) a LocalDate
        LocalDate fechaInicio = LocalDate.parse(this.fechaPrestamo);

        // Sumar los días para obtener la fecha de devolución
        LocalDate fechaDev = fechaInicio.plusDays(this.diasPrestamo);

        // Guardar en formato String (AAAA-MM-DD)
        this.fechaDevolucion = fechaDev.toString();

        System.out.println("Fecha de devolución calculada automáticamente: " + this.fechaDevolucion);
    }

    // 3.1.12 Genera y muestra en consola una tarjeta de préstamo con los datos principales.
    public void imprimirTarjetaPrestamo() {
        // Validaciones mínimas de datos necesarios
        if (this.usuario == null || this.libro == null) {
            System.out.println("No hay usuario y/o libro asignado para imprimir la tarjeta.");
            return;
        }
        if (this.fechaPrestamo == null || this.fechaPrestamo.isEmpty()) {
            System.out.println("No hay fecha de préstamo registrada.");
            return;
        }
        if (this.fechaDevolucion == null || this.fechaDevolucion.isEmpty()) {
            System.out.println("No hay fecha de devolución registrada.");
            return;
        }

        String tipoUsuario = (this.usuario instanceof Docente) ? "Docente"
                : (this.usuario instanceof Estudiante) ? "Estudiante"
                : "Usuario";

        // TARJETA
        String separador = "+--------------------------------------------+";
        System.out.println(separador);
        System.out.println("|           TARJETA DE PRÉSTAMO             |");
        System.out.println(separador);
        System.out.printf("| Usuario : %-34s |\n", this.usuario.getNombre_completo());
        System.out.printf("| RUN     : %-34s |\n", this.usuario.getRut());
        System.out.printf("| Tipo    : %-34s |\n", tipoUsuario);
        System.out.println(separador);
        System.out.printf("| Título  : %-34s |\n", this.libro.getTitulo());
        System.out.printf("| Autor   : %-34s |\n", (this.libro.getAutor() != null ? this.libro.getAutor() : ""));
        System.out.printf("| ISBN    : %-34s |\n", this.libro.getIsbn());
        System.out.println(separador);
        System.out.printf("| Fecha préstamo  : %-23s |\n", this.fechaPrestamo);
        System.out.printf("| Días de préstamo: %-23d |\n", this.diasPrestamo);
        System.out.printf("| Fecha devolución: %-23s |\n", this.fechaDevolucion);
        System.out.println(separador);
    }


    // 3.2: DEVOLUCIÓN
    // 3.2.5 Valida que el ISBN del libro coincida con el préstamo del usuario
    public boolean validarCoincidenciaPrestamoConIsbn() {
        if (this.usuario == null || this.libro == null) {
            System.out.println("Error: No hay usuario o libro asignado.");
            return false;
        }

        String prestamoUsuario = this.usuario.getPrestamo();
        if (prestamoUsuario == null || prestamoUsuario.equals("0")) {
            System.out.println("El usuario no tiene préstamos activos.");
            return false;
        }

        String a = normalizarIsbn(prestamoUsuario);
        String b = normalizarIsbn(this.libro.getIsbn());

        if (a.equals(b)) {
            System.out.println("Validación correcta: el libro coincide con el préstamo del usuario.");
            return true;
        } else {
            System.out.println("Error: el libro no coincide con el préstamo del usuario.");
            System.out.println("El usuario tiene prestado el ISBN: " + prestamoUsuario);
            return false;
        }
    }


    // 3.2.6 Habilita al usuario para nuevos préstamos
    public boolean habilitarUsuarioParaNuevoPrestamo() {
        if (this.usuario == null) {
            System.out.println("No hay usuario cargado.");
            return false;
        }
        this.usuario.setPrestamo("0");
        System.out.println("Usuario habilitado para un nuevo préstamo.");
        return true;
    }

    // 3.2.7 Suma un ejemplar disponible del libro devuelto
    public boolean sumarEjemplarDisponible() {
        if (this.libro == null) {
            System.out.println("No hay libro cargado para devolver.");
            return false;
        }

        int disponibles = this.libro.getCantidad_disponible();
        int enBiblioteca = this.libro.getCantidad_biblioteca();

        if (disponibles >= enBiblioteca) {
            System.out.println("La cantidad disponible ya es igual al total en biblioteca.");
            return false;
        }

        this.libro.setCantidad_disponible(disponibles + 1);
        System.out.println("Ejemplar devuelto correctamente. Disponibles ahora: " + this.libro.getCantidad_disponible());
        return true;
    }

    // 3.2.8 Calcula multa por retraso
    public void calcularMultaPorRetraso() {
        if (this.fechaDevolucion == null || this.fechaDevolucion.isEmpty()) {
            System.out.println("No se tiene registrada la fecha de devolución esperada. No se puede calcular multa.");
            return;
        }

        LocalDate fechaComprometida = LocalDate.parse(this.fechaDevolucion);
        LocalDate hoy = LocalDate.now();

        if (hoy.isAfter(fechaComprometida)) {
            long diasRetraso = java.time.temporal.ChronoUnit.DAYS.between(fechaComprometida, hoy);
            long multa = diasRetraso * 1000;
            System.out.println("El libro se devolvió con " + diasRetraso + " día(s) de retraso.");
            System.out.println("Multa total: $" + multa);
        } else {
            System.out.println("El libro se devolvió a tiempo. No hay multa.");
        }
    }

    // Metodo para la devolución (3.2.1 al 3.2.8)
    public void procesarDevolucion(GestorLibros gestorLibros, GestorUsuarios gestorUsuarios, Scanner sc) {
        System.out.println("\n===== PROCESO DE DEVOLUCIÓN =====");

        // 3.2.1 y 3.2.2: Ingresar y validar libro
        ingresarIsbnDesdeConsola(sc);
        boolean existeLibro = validarLibroExiste(gestorLibros, sc);
        if (!existeLibro) return;

        // 3.2.3 y 3.2.4: Ingresar y validar usuario
        boolean usuarioValido = ingresarYValidarUsuario(gestorUsuarios, sc);
        if (!usuarioValido) return;

        // 3.2.5: Verificar coincidencia entre libro y préstamo activo
        boolean coincide = validarCoincidenciaPrestamoConIsbn();
        if (!coincide) return;

        // 3.2.8: Calcular multa antes de liberar el préstamo
        calcularMultaPorRetraso();

        // 3.2.6: Habilitar usuario para nuevo préstamo
        boolean habilitado = habilitarUsuarioParaNuevoPrestamo();
        if (!habilitado) return;

        // 3.2.7: Sumar ejemplar devuelto
        boolean sumado = sumarEjemplarDisponible();
        if (!sumado) return;

        System.out.println("Devolución completada correctamente.\n");
    }
}
