package com.emergentes.controlador;

import com.emergentes.dao.RolesDAO;
import com.emergentes.dao.RolesDAOimpl;
import com.emergentes.modelo.Roles;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sammy
 */
@WebServlet(name = "RolesControlador", urlPatterns = {"/RolesControlador"})
public class RolesControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Roles rol = new Roles();
            int id;
            RolesDAO dao = new RolesDAOimpl();
            String action = (request.getParameter("action") != null) ? request.getParameter("action") : "view";

            switch (action) {
                case "add":
                    request.setAttribute("roles", rol);
                    request.getRequestDispatcher("frmroles.jsp").forward(request, response);
                    break;
                case "edit":
                    id = Integer.parseInt(request.getParameter("id"));
                    rol = dao.getById(id);
                    request.setAttribute("roles", rol);
                    request.getRequestDispatcher("frmroles.jsp").forward(request, response);
                    break;
                case "delete":
                    id = Integer.parseInt(request.getParameter("id"));
                    dao.delete(id);
                    response.sendRedirect("RolesControlador");
                    break;
                case "view":
                    // Obtener la lista de registros
                    List<Roles> lista = dao.getAll();
                    request.setAttribute("roless", lista);
                    request.getRequestDispatcher("roles.jsp").forward(request, response);
                    break;
            }

        } catch (Exception ex) {
            System.out.println("Error " + ex.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String descripcion = request.getParameter("descripcion");
       

        Roles rol = new Roles();

        rol.setId(id);
      rol.setDescripcion(descripcion);

        RolesDAO dao = new RolesDAOimpl();
        if (id == 0) {
            try {
                // Nuevo registro
                dao.insert(rol);
            } catch (Exception ex) {
                System.out.println("Error al insertar " + ex.getMessage());
            }
        } else {
            try {
                // Edicion de registro
                dao.update(rol);
            } catch (Exception ex) {
                System.out.println("Error al editar " + ex.getMessage());
            }
        }
        response.sendRedirect("RolesControlador");
    }

}

