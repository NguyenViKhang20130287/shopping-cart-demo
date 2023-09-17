package Controller;

import DAO.productDAO;
import Entity.product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Pay", value = "/Pay")
public class Pay extends HttpServlet {

    private int totalPrice(List<product> list) {
        int total = 0;
        for (product item : list) {
            total += (item.getPrice() - item.getDiscount()) * item.getQuantityInCart();

        }
        return total;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        String fullName = request.getParameter("fullName");
        String streetName = request.getParameter("streetName");
        String subDistrict = request.getParameter("sub-district");
        String district = request.getParameter("district");
        String city = request.getParameter("city");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        String status = request.getParameter("status");
        String pid = request.getParameter("pid");

        if (status.equals("buyCart")) {
            List<product> cart = (List<product>) session.getAttribute("cart");
            List<product> products = (List<product>) session.getAttribute("productChecked");

            new productDAO().insertOrder(fullName, streetName, subDistrict, district, city, email, phone, totalPrice(products));
            for (product item : products) {
                new productDAO().insertOrderDetails(item.getId(), item.getQuantityInCart());
                new productDAO().DeleteProductInCart(cart, item.getId());

            }
            session.setAttribute("quantityInCart", new productDAO().quantityInCart(cart));
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Đặt hàng thành công, Tổng đơn hàng là: " + totalPrice(products) + "₫');");
            out.println("location='Home';");
            out.println("</script>");

        } else if (status.equals("buyNow")) {
            product p = new productDAO().getOneProductWithId(Integer.parseInt(pid));
            p.setQuantityInCart(1);
            new productDAO().insertOrder(fullName, streetName, subDistrict, district, city, email, phone, (p.getPrice() - p.getDiscount()));
            new productDAO().insertOrderDetails(p.getId(), p.getQuantityInCart());
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Đặt hàng thành công, Tổng đơn hàng là: " + (p.getPrice() - p.getDiscount()) + "₫');");
            out.println("location='Home';");
            out.println("</script>");
        }

    }
}