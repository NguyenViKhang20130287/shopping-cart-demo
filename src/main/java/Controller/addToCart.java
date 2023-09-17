package Controller;

import DAO.productDAO;
import Entity.product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "addToCart", value = "/addToCart")
public class addToCart extends HttpServlet {

    private List<product> listP = new ArrayList<>();


    private String cartBtnHtml(int quantityInCart) {
        return " <div class=\"quantity\">\n" +
                "<span>" + quantityInCart + "</span>\n" +
                " </div>\n";
    }

    private String cartContentHtml(List<product> list) {
        String result = "";
        if (!list.isEmpty()) {
            for (product p : list) {
                result += "<div class=\"product\">\n" +
                        "<div class=\"product_image\">\n" +
                        "<img src=\"" + p.getImage() + "\"\n" +
                        "alt=\"\">\n" +
                        "</div>\n" +
                        "<div class=\"product_title\"><a href=\"\">" + p.getTitle() + "</a></div>\n" +
                        "<div class=\"product_price\">\n" +
                        "<span>" + (p.getPrice() - p.getDiscount()) + "₫</span>\n" +
                        "</div>\n" +
                        "</div>\n";
            }
        }
        return result;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        int pid = Integer.parseInt(request.getParameter("pid"));
        product p = new productDAO().getOneProductWithId(pid);

        if (listP.isEmpty()) {
            p.setQuantityInCart(1);
            listP.add(p);
        } else {
            boolean checkExist = new productDAO().checkProductExistInCart(listP, p);
            if (checkExist) {
                //
            } else {
                p.setQuantityInCart(1);
                listP.add(p);
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute("cart", listP);
        session.setAttribute("quantityInCart", new productDAO().quantityInCart(listP));

//        response.getWriter().println(session.getAttribute("cart"));
        response.getWriter().println(
                "<button class=\"cart_btn\" id=\"cart_btn\">\n" +
                        "<a href=\"LoadProductInCart\">\n" +
                        "<i class=\"bx bx-cart\"></i>"
                        + cartBtnHtml(new productDAO().quantityInCart(listP))
                        + "</a>\n" +
                        "</button>" +
                        "<div class=\"popper_wrapper\" \">\n" +
                        "<h3 class=\"popper_title\">\n" +
                        "Sản phẩm mới thêm\n" +
                        "</h3>\n" +
                        "<div class=\"popper_content\">"
                        + cartContentHtml(listP)
                        + "</div>\n" +
                        "                    <div class=\"popper_footer\">\n" +
                        "                        <div class=\"message\">\n" +
                        "                            <span>" + listP.size() + " sản phẩm trong giỏ hàng</span>\n" +
                        "                        </div>\n" +
                        "                        <a href=\"LoadProductInCart\" class=\"more_product\">Xem giỏ hàng</a>\n" +
                        "                    </div>\n" +
                        "                </div>"
        );
    }
}