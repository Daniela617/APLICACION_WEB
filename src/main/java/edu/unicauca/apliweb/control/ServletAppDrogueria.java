package edu.unicauca.apliweb.control;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import edu.unicauca.apliweb.persistence.entities.Producto;
import edu.unicauca.apliweb.persistence.jpa.ProductoJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Usuario
 */
@WebServlet("/")
public class ServletAppDrogueria extends HttpServlet {
    private ProductoJpaController prdJPA;
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
            throws ServletException, IOException {
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
        processRequest(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
                //esta parte es solamente para realizar la prueba:
                //listamos todos los clientes de la base de datos y los imprimimos en consola
                List<Producto> listaProductos = prdJPA.findProductoEntities();
                //imprimimos los clientes en consola
                for(Producto prd: listaProductos){
                System.out.println("Nombre "+prd.getNombreProducto());
                }
        }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        RequestDispatcher dispatcher = request.getRequestDispatcher("producto-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertProducto(HttpServletRequest request, HttpServletResponse response) 
    throws SQLException, ServletException, IOException
    {
       int cod;
       //traerse categoria
       String nombrePrd;
       int precioPub;
       int precioCompra;
       //fecha
       int cantidad;
       String laboratorio;
       
       
   }

    private void deleteProducto(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

    private void updateProducto(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void listProducto(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException, ServletException
    {
        List<Producto> lsPrd = prdJPA.findProductoEntities();
        request.setAttribute("listProducto", lsPrd);
        RequestDispatcher dispatcher = request.getRequestDispatcher("list-productos.jsp");
        dispatcher.forward(request, response);
    }

}
/*
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
        //esta parte es solamente para realizar la prueba:
        //listamos todos los clientes de la base de datos y los imprimimos en consola
        List<Producto> listaProductos = prdJPA.findProductoEntities();
        //imprimimos los clientes en consola
        for(Producto prd: listaProductos){
        System.out.println("Nombre "+prd.getNombreProducto());
        }
}
*/