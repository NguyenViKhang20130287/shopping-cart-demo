package Controller;

import DAO.productDAO;
import Entity.product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Search", value = "/Search")
public class Search extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String searchValue = request.getParameter("searchValue");
        List<product> list = new productDAO().searchProduct(searchValue);

        for (product p : list){
            response.getWriter().println(
                    "<div class=\"main_content_product\">\n" +
                            "                        <div class=\"product_image\">\n" +
                            "                            <img src=\"" + p.getImage() + "\" alt=\"\">\n" +
                            "                        </div>\n" +
                            "                        <div class=\"product_title\">\n" +
                            "                            <span>" + p.getTitle() + "</span>\n" +
                            "                        </div>\n" +
                            "                        <div class=\"product_price\">\n" +
                            "                            <span class=\"new_price\">" + (p.getPrice() - p.getDiscount()) + "₫</span>\n" +
                            "                            <span class=\"price\">" + p.getPrice() + "₫</span>\n" +
                            "                        </div>\n" +
                            "                        <div class=\"product_action\">\n" +
                            "<a href=\"Checkout?pid="+p.getId()+"\" class=\"buy\">Mua</a>" +
                            "<button class=\"addToCart\" onclick=\"addToCart(pid="+p.getId()+")\">Thêm vào giỏ</button>" +
                            "                        </div>\n" +
                            "                    </div>"
            );
        }
    }
}