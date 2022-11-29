import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// Fetch data
/**
 * Servlet implementation class demo3
 */
@WebServlet("/GetBooksByAuthor")
public class GetBooksByAuthor extends HttpServlet {
    private static final long serialVersionUID = 1 ;

    String dns = "ec2-18-234-166-62.compute-1.amazonaws.com";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBooksByAuthor() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String sql;
        Connection connection = null;
        Statement statement = null;
        PreparedStatement statement1 = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        String keyword = request.getParameter("authorName");
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        String title = "Fetch Book Details";
        String docType =
            "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor = \"##40D61A\">\n" + //
            "<h1 align = \"center\">" + title + "</h1>\n");


        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + dns + ":3306/mysql", "matt", "password");
        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            System.out.println("Connection Failed!:\n" + e2.getMessage());
        }
        System.out.println("SUCCESS!!!! You made it, take control     your database now!");
        System.out.println("Creating statement...");

        sql = "SELECT * FROM product WHERE product_name=?";
        try {

            statement1 = connection.prepareStatement(sql);
            String theAuthor = keyword;
            statement1.setString(1, theAuthor);
     
        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        try {

            rs = statement1.executeQuery();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        out.println("<table border=1 width=50% height=10%>");
        out.println("<tr><th>Book Title</th><th>Author</th><th>Genre</th><th>ISBN</th><th>Summary</th><th>Poster Name</th>");
        try {
            while (rs.next()) {
                //Retrieve by column name
                String bookTitle = rs.getString("product_name");
                String authorVal = rs.getString("posting_date");
                String genre = rs.getString("product_desc");
                String isbn = rs.getString("product_image");
                String summary = rs.getString("poster_contact");
                String name = rs.getString("poster_name");
                out.println("<tr><td>" + bookTitle + "</td><td>" + authorVal + "</td><td>" + genre + "</td><td>" + isbn + "</td><td>" 
                + summary + "</td><td>" + name + "</td></tr>");
            }
            out.println("</body></html>");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}