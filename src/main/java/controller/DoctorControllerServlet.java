/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import jdbc.DatabaseConnection;
import models.Doctor;

/**
 *
 * @author Admin
 */
@WebServlet(name = "DoctorControllerServlet", urlPatterns = {"/DoctorControllerServlet"})
public class DoctorControllerServlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            List<Doctor> doctors = new ArrayList<>();
            
            String from = request.getParameter("from");
            String to = request.getParameter("to");
            
//            // bien check
            int check = 1;
////            // neu la lan dau tien ( searchName = null ) cho bien check = 0
            if (from == null && to == null) {
                check = 0;
            }
//
//            // neu search trong ( search == "" ) va bien check = 1 ( de tranh hien thi bao loi lan dau chua nhap vao search)
            if (from == null || "".equals(from) || to == null || "".equals(to) && check == 1) {
                // cho search = ""
                from = "";
                to = "";
                // tao bien session bao loi
                request.setAttribute("errorMessage", "Không được để trống!");
                // quay ve
                request.getRequestDispatcher("./index.jsp").forward(request, response);
            }
////            // check lan dau - hien het ket qua
            if (check == 0) {
                from = "";
                to = "";
            }
            // cau lenh sql
            myConn = DatabaseConnection.getConnection();

            myStmt = myConn.createStatement();

            String sql = "SELECT * FROM customer WHERE age BETWEEN " + from + " AND " + to + "";

            myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {
                int id = myRs.getInt("id");
                String name = myRs.getString("name");
                int age = myRs.getInt("age");
                String address = myRs.getString("address");
                
                doctors.add(new Doctor(id, name, age, address));
            }
            request.setAttribute("DOCTOR_LIST", doctors);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } finally {

           //close sai bet
            if (myRs != null) try {
                myRs.close();
            } catch (SQLException ignore) {
                //irgone
            }

            if (myStmt != null) try {
                myStmt.close();
            } catch (SQLException ignore) {
                //irgone
            }

            if (myConn != null) try {
                myConn.close();
            } catch (SQLException ignore) {
                //irgone
            }
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
        } catch (SQLException ex) {
            Logger.getLogger(DoctorControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(DoctorControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
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

}