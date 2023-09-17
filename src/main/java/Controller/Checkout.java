package Controller;

import DAO.productDAO;
import Entity.product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "Checkout", value = "/Checkout")
public class Checkout extends HttpServlet {

    private List<product> getProductChecked(List<product> productInCart, String[] ids) {
        List<product> listResult = new ArrayList<>();
        for (product pic : productInCart) {
            if (ids != null) {
                for (String id : ids) {
                    int i = Integer.parseInt(id);
                    if (pic.getId() == i) {
                        listResult.add(pic);
                    }
                }
            }
        }

        return listResult;
    }

    private int totalPrice(List<product> list) {
        int result = 0;
        for (product p : list) {
            result += (p.getPrice() - p.getDiscount()) * p.getQuantityInCart();
        }
        return result;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        product p = new productDAO().getOneProductWithId(Integer.parseInt(pid));
        p.setQuantityInCart(1);
        request.setAttribute("productBuyNow", p);

        request.getRequestDispatcher("pay.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        List<product> cart = (List<product>) session.getAttribute("cart");
        String[] checkItems = request.getParameterValues("checkbox");
        List<product> listProductChecked = new ArrayList<>();
        try {
            if (checkItems != null) {
                for (product item : cart) {
                    for (String checkedId : checkItems) {
                        int id = Integer.parseInt(checkedId);
                        if (item.getId() == id) {
                            listProductChecked.add(item);
                            break;
                        }
                    }
                }
                request.setAttribute("total", totalPrice(listProductChecked));
                session.setAttribute("productChecked", listProductChecked);
                request.getRequestDispatcher("pay.jsp").forward(request, response);
            } else {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Người dùng chưa chọn sản phẩm nào !!!');");
                out.println("location='LoadProductInCart';");
                out.println("</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}