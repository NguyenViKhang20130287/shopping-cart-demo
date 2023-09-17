package Controller;

import Entity.product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LoadProductInCart", value = "/LoadProductInCart")
public class LoadPrductInCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<product> listP = (List<product>) session.getAttribute("cart");

        int sum = 0;
        int quantity = 0;
        try {
            if (listP != null) {
                for (product p : listP) {
                    sum += (p.getPrice() - p.getDiscount()) * p.getQuantityInCart();
                    quantity += p.getQuantityInCart();
                }
                request.setAttribute("totalPriceInCart", sum);
                request.getRequestDispatcher("cart.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("cart.jsp").forward(request, response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}