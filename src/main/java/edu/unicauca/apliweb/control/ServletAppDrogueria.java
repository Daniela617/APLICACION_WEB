package edu.unicauca.apliweb.control;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import edu.unicauca.apliweb.persistence.entities.Compra;
import edu.unicauca.apliweb.persistence.entities.Producto;
import edu.unicauca.apliweb.persistence.entities.Proveedor;

import edu.unicauca.apliweb.persistence.entities.Trabajador;
import edu.unicauca.apliweb.persistence.jpa.CompraJpaController;
import edu.unicauca.apliweb.persistence.jpa.ProductoJpaController;
import edu.unicauca.apliweb.persistence.jpa.TrabajadorJpaController;
import edu.unicauca.apliweb.persistence.jpa.exceptions.IllegalOrphanException;
import edu.unicauca.apliweb.persistence.jpa.exceptions.NonexistentEntityException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Usuario
 */
@WebServlet("/")
public class ServletAppDrogueria extends HttpServlet {
    private ProductoJpaController prdJPA;
    private CompraJpaController comJPA ;
    private TrabajadorJpaController trJPA;
    private final static String PU="edu.unicauca.apliweb_HolaApliWeb_war_1.0-SNAPSHOTPU";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
  
            String action = request.getServletPath();
            try{
                switch (action) {
                case "/new": //Muestra el formulario para crear un nuevo cliente
                    showNewForm(request, response);
                    break;
                case "/insert": //ejecuta la creación de un nuevo cliente en la
                    
                    insertProducto(request, response);
                    break;
                case "/delete": //Ejecuta la eliminación de un cliente de la BD
                    deleteProducto(request, response);
                    break;
                case "/edit": //Muestra el formulario para editar un cliente
                    showEditForm(request, response);
                    break;
                case "/update": //Ejecuta la edición de un cliente de la BD
                    updateProducto(request, response);
                    break;
                default:
                    listProducto(request, response);
                    break;
                    
                    
            }
            } catch (SQLException ex) {
                throw new ServletException(ex);
            }
       
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ServletAppDrogueria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("ingresar".equals(action)) {
            System.out.println("Entra");
            login(request, response);
        } else {
            // Manejar otros casos o mostrar un error
        }
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ServletAppDrogueria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
     @Override
        public void init() throws ServletException {
        super.init();
                //creamos una instancia de la clase EntityManagerFactory
                //esta clase se encarga de gestionar la construcción de entidades y
                //permite a los controladores JPA ejecutar las operaciones CRUD
                EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
                //creamos una instancia del controldor JPA para la clase clients y le
                //pasamos el gestor de entidades
                prdJPA = new ProductoJpaController(emf);
                comJPA = new CompraJpaController(emf);
                trJPA = new TrabajadorJpaController(emf);
                //esta parte es solamente para realizar la prueba:
                //listamos todos los clientes de la base de datos y los imprimimos en consola
                //List<Producto> listaProductos = prdJPA.findProductoEntities();
                //imprimimos los clientes en consola
                //for(Producto prd: listaProductos){
                //System.out.println("Nombre "+prd.getNombreProducto());
               // }
               
        }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        RequestDispatcher dispatcher = request.getRequestDispatcher("producto-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertProducto(HttpServletRequest request, HttpServletResponse response) 
    throws SQLException, ServletException, IOException, ParseException, Exception {
    String dateFormatPattern = "yyyy-MM-dd"; // Ajusta el formato según la cadena proporcionada
    SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);

    String nombrePrd = request.getParameter("nombrePrd");
    String precioPubParam = request.getParameter("precioPub");
    String precioCompraParam = request.getParameter("precioCompra");
    String fecha = request.getParameter("fecha");
    String cantidadParam = request.getParameter("cantidad");
    String laboratorio = request.getParameter("laboratorio");

    // Validaciones
    if (nombrePrd == null || nombrePrd.trim().isEmpty() ||
        precioPubParam == null || precioPubParam.trim().isEmpty() ||
        precioCompraParam == null || precioCompraParam.trim().isEmpty() ||
        fecha == null || fecha.trim().isEmpty() ||
        cantidadParam == null || cantidadParam.trim().isEmpty() ||
        laboratorio == null || laboratorio.trim().isEmpty()) {

        String mensajeError = "¡Todos los campos son obligatorios!";
        request.setAttribute("mensaje", mensajeError);

        // Redirige a mensaje.jsp o a la página que maneja los errores
        RequestDispatcher dispatcher = request.getRequestDispatcher("mensaje.jsp");
        dispatcher.forward(request, response);
        return; // Detiene la ejecución del método si hay campos vacíos
    }

    // Ahora puedes continuar con el procesamiento normal
    try {
        int precioPub = Integer.parseInt(precioPubParam);
        int precioCompra = Integer.parseInt(precioCompraParam);
        int cantidad = Integer.parseInt(cantidadParam);

        Producto prd = new Producto();
        prd.setNombreProducto(nombrePrd);
        prd.setPrecioCompraPrd(precioCompra);
        prd.setPrecioPublicoPrd(precioPub);
        prd.setFechaVencimientoPrd(dateFormat.parse(fecha));
        prd.setProductoCantidad((short) cantidad);
        prd.setLaboratorio(laboratorio);

        // Edita el producto en la BD
        prdJPA.create(prd);

        response.sendRedirect("list");
    } catch (NumberFormatException ex) {
        // Manejar error de conversión de número (precioPub, precioCompra, cantidad)
        String mensajeError = "¡Formato numérico inválido!";
        request.setAttribute("mensaje", mensajeError);

        // Redirige a mensaje.jsp o a la página que maneja los errores
        RequestDispatcher dispatcher = request.getRequestDispatcher("mensaje.jsp");
        dispatcher.forward(request, response);
    } catch (ParseException ex) {
        // Manejar error de conversión de fecha
        String mensajeError = "¡Formato de fecha inválido!";
        request.setAttribute("mensaje", mensajeError);

        // Redirige a mensaje.jsp o a la página que maneja los errores
        RequestDispatcher dispatcher = request.getRequestDispatcher("mensaje.jsp");
        dispatcher.forward(request, response);
    } catch (Exception ex) {
        // Manejar otros errores
        String mensajeError = "¡Hubo un error!";
        request.setAttribute("mensaje", mensajeError);

        // Redirige a mensaje.jsp o a la página que maneja los errores
        RequestDispatcher dispatcher = request.getRequestDispatcher("mensaje.jsp");
        dispatcher.forward(request, response);
    }
}
    private void deleteProducto(HttpServletRequest request, HttpServletResponse response) throws IllegalOrphanException, IOException {
      int id = Integer.parseInt(request.getParameter("id"));

      try {
         List<Compra> lsC=comJPA.findCompraEntities();
         for(Compra compr: lsC){
             if(compr.getCodProducto().getCodProducto()==102){
                 int idC=compr.getIdCompra();
                 comJPA.destroy(idC);
                  System.out.println("Nombre ");
             }
               
         }
          // Elimina el producto con el id indicado
          prdJPA.destroy(id);
      } catch (NonexistentEntityException ex) {
          Logger.getLogger(ServletAppDrogueria.class.getName()).log(Level.SEVERE, null, ex);
      }
      response.sendRedirect("list");
  }


    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
    throws SQLException, ServletException, IOException
    {
        int id = Integer.parseInt(request.getParameter("id"));
        
        Producto existingPrd =prdJPA.findProducto(id);
        RequestDispatcher dispatcher = null;
        if (existingPrd != null) {
        //si lo encuentra lo envía al formulario
        dispatcher = request.getRequestDispatcher("producto-form.jsp");
        request.setAttribute("producto", existingPrd);
        } else {
            dispatcher = request.getRequestDispatcher("list-productos.jsp");
        }
        dispatcher.forward(request, response);
    }

   private void updateProducto(HttpServletRequest request, HttpServletResponse response) throws ParseException, Exception {
             //String dateFormatPattern = "yyyy-mm-dd"; // Ajusta el formato según la cadena proporcionada
        //SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);

       int cod = Integer.parseInt(request.getParameter("id"));
      // int categoria = Integer.parseInt(request.getParameter("categoria"));
       String nombrePrd = request.getParameter("nombrePrd");
       int precioPub = Integer.parseInt(request.getParameter("precioPub"));
       int precioCompra = Integer.parseInt(request.getParameter("precioCompra"));
       String fechaStr = request.getParameter("fecha");
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
       Date fecha = dateFormat.parse(fechaStr);
       int cantidad = Integer.parseInt(request.getParameter("cantidad")); 
       String laboratorio = request.getParameter("laboratorio");
       Producto prdOld = prdJPA.findProducto(cod);


       Producto prd = new Producto();
       prd.setCodProducto(cod);
       prd.setNombreProducto(nombrePrd);
       prd.setPrecioCompraPrd(precioCompra);
       prd.setPrecioPublicoPrd(precioPub);
       prd.setFechaVencimientoPrd( dateFormat.parse(fechaStr));
       prd.setProductoCantidad((short) cantidad);
       prd.setLaboratorio(laboratorio);
       if(prdOld.getCompraList()==null ||prdOld.getProveedorList()==null){
           List<Compra> ls = new ArrayList<>();
           List<Proveedor> lsP = new ArrayList<>();
            prd.setCompraList( ls);
            prd.setProveedorList(lsP);
       }else{
           prd.setCompraList( prdOld.getCompraList());
            prd.setProveedorList(prdOld.getProveedorList());
       }
       
       //falta lab y proveedor

       try {
        //Edita el cliente en la BD
             prdJPA.edit(prd);
        } catch (Exception ex) {
            Logger.getLogger(ServletAppDrogueria.class.getName()).log(Level.SEVERE, null, ex);

        }
        response.sendRedirect("list");

    }

    private void listProducto(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException, ServletException
    {
        List<Producto> lsPrd = prdJPA.findProductoEntities();
        request.setAttribute("listProducto", lsPrd);
        RequestDispatcher dispatcher = request.getRequestDispatcher("list-productos.jsp");
        dispatcher.forward(request, response);
    }
    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String usuario = request.getParameter("user");
        String contraseña = request.getParameter("password");
        List<Trabajador> lsT = trJPA.findTrabajadorEntities();
        for(Trabajador tr: lsT){
             if(tr.getLoginUsuario().equals(usuario) && tr.getContraseniaUsuario().equals(contraseña)){
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                Cookie cookieUsuario = new Cookie("usuarioI", usuario);
                response.addCookie(cookieUsuario);
             }
         }
    }

}
