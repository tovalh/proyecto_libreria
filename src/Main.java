import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final GestorLibros gestorLibros = new GestorLibros();
    private static final GestorUsuarios gestorUsuarios = new GestorUsuarios();

    public static void main(String[] args) {

        // EJEMPLOS
        Libro libro1 = new Libro("1234567891234", "Programación en Java", "Domingo Díaz", 5, 3, "");
        Libro libro2 = new Libro("9876543219876", "POO Fundamentos", "Rosa Flores", 4, 2, "");
        Libro libro3 = new Libro("5556667778889", "Estructuras de Datos", "Luis Contreras", 6, 6, "");
        gestorLibros.agregarLibro(libro1);
        gestorLibros.agregarLibro(libro2);
        gestorLibros.agregarLibro(libro3);

        Usuario u1 = new Estudiante("Ana Torres", "12345678-9", 'F', "Ingeniería");
        Usuario u2 = new Docente("Carlos Díaz", "98765432-1", 'M', "Ingeniero", "Magíster");
        Usuario u3 = new Estudiante("Pedro López", "11111111-9", 'M', "Diseño Digital");
        gestorUsuarios.agregarUsuario(u1);
        gestorUsuarios.agregarUsuario(u2);
        gestorUsuarios.agregarUsuario(u3);

        //MENU PRINCIPAL
        int opcion;
        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1) Crear usuario");
            System.out.println("2) Crear libro");
            System.out.println("3) Registrar PRÉSTAMO");
            System.out.println("4) Registrar DEVOLUCIÓN");
            System.out.println("5) Salir");
            System.out.print("Elige una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> crearUsuario();
                case 2 -> crearLibro();
                case 3 -> registrarPrestamo();
                case 4 -> registrarDevolucion();
                case 5 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 5);

        sc.close();
        System.out.println("Programa finalizado.");
    }

    // OPCIONES
    private static void crearUsuario() {
        System.out.println("\n> Crear usuario");
        System.out.print("Tipo (E=Estudiante / D=Docente): ");
        String tipo = sc.nextLine().trim();

        System.out.print("Nombre completo: ");
        String nombre = sc.nextLine().trim();

        String rut;
        while (true) {
            System.out.print("RUT (ej: 12.345.678-5): ");
            rut = sc.nextLine().trim();
            if (Usuario.validarRut(rut)) break;
            System.out.println("RUT inválido. Intente nuevamente.");
        }

        char genero;
        while (true) {
            System.out.print("Género (M/F): ");
            String g = sc.nextLine().trim().toUpperCase();
            if (g.length() == 1 && (g.charAt(0) == 'M' || g.charAt(0) == 'F')) {
                genero = g.charAt(0);
                break;
            }
            System.out.println("Valor inválido.");
        }

        Usuario u;

        if (tipo.equalsIgnoreCase("D")) {
            // DOCENTE: pedir profesión y grado
            System.out.print("Profesión: ");
            String profesion = sc.nextLine().trim();
            System.out.print("Grado académico (Magíster/Doctor): ");
            String grado = sc.nextLine().trim();
            u = new Docente(nombre, rut, genero, profesion, grado);

        } else if (tipo.equalsIgnoreCase("E")) {
            // ESTUDIANTE: pedir carrera
            System.out.print("Carrera: ");
            String carrera = sc.nextLine().trim();
            u = new Estudiante(nombre, rut, genero, carrera);

        } else {
            System.out.println("Tipo inválido. Solo E o D.");
            return;
        }

        boolean ok = gestorUsuarios.agregarUsuario(u);
        System.out.println(ok ? "Usuario agregado." : "No se pudo agregar.");
    }

    private static void crearLibro() {
        System.out.println("\n> Crear libro");
        System.out.print("ISBN: ");
        String isbn = sc.nextLine().trim();
        System.out.print("Título: ");
        String titulo = sc.nextLine().trim();
        System.out.print("Autor: ");
        String autor = sc.nextLine().trim();
        System.out.print("Cantidad en biblioteca: ");
        int total = leerEntero();
        System.out.print("Cantidad disponible: ");
        int disp = leerEntero();

        Libro l = new Libro(isbn, titulo, autor, total, disp, "");
        boolean ok = gestorLibros.agregarLibro(l);
        System.out.println(ok ? "Libro agregado." : "No se pudo agregar.");
    }

    private static void registrarPrestamo() {
        System.out.println("\n> Registrar PRÉSTAMO");
        Prestamo p = new Prestamo();
        p.ingresarIsbnDesdeConsola(sc);
        if (!p.validarLibroExiste(gestorLibros, sc)) return;
        if (!p.validarEjemplaresDisponibles(gestorLibros, sc)) return;
        if (!p.ingresarYValidarUsuario(gestorUsuarios, sc)) return;
        if (!p.validarUsuarioHabilitado(gestorUsuarios, sc)) return;
        if (!p.registrarPrestamoEnUsuario()) return;
        if (!p.descontarEjemplarDisponible()) return;
        p.registrarFechaPrestamo();
        p.ingresarDiasPrestamo(sc);
        p.calcularFechaDevolucion();
        p.imprimirTarjetaPrestamo();
        System.out.println("Préstamo completado.");
    }

    private static void registrarDevolucion() {
        System.out.println("\n> Registrar DEVOLUCIÓN");
        Prestamo p = new Prestamo();
        p.procesarDevolucion(gestorLibros, gestorUsuarios, sc);
    }

    private static int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }
}
