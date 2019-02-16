/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author P-ratt
 */
public class MainServlet extends HttpServlet {

	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String animal = req.getParameter("animals");
		if (animal != null) {
			PrintWriter writer = res.getWriter();
			writer.println(""
					+ "<!DOCTYPE html>"
					+ "<html>"
					+ "<body>"
					+ "<form action=\"back\">"
					+ "<div>"
					+ "<img src=\"/images/" + animal + ".jpg\" alt=\"" + animal + "\"/>"
					+ "</div>"
					+ "<div>"
					+ "<button type=\"submit\" value=\"Back\">Back</button>"
					+ "</div>"
					+ "</form>"
					+ "</body>"
					+ "</html>");
		} else {
			res.sendRedirect(req.getContextPath() + "/index.html");
		}
	}
}
