package Controller;

import Entity.product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ReduceQuantity", value = "/ReduceQuantity")
public class ReduceQuantity extends HttpServlet {

    private String productsHtml(List<product> list) {
        String result = "";
        for (product item : list) {
            result +=
                    "<div class=\"cart_product\">\n" +
                            "                                <div class=\"cart_chosse\">\n" +
                            "<input type=\"checkbox\" name=\"checkbox\" id=\"\" class=\"checkItem\" value="+item.getId()+">" +
                            "                                </div>\n" +
                            "                                <div class=\"cart_product_info\">\n" +
                            "                                    <div class=\"product_image\">\n" +
                            "                                        <img src=\"" + item.getImage() + "\"\n" +
                            "                                             alt=\"\">\n" +
                            "                                    </div>\n" +
                            "                                    <div class=\"cart_product_title\">\n" +
                            "                                    <span>" + item.getTitle() + "</span>\n" +
                            "                                    </div>\n" +
                            "                                </div>\n" +
                            "\n" +
                            "                                <div class=\"cart_product_price\">\n" +
                            "                                    <span class=\"old_price\">" + item.getPrice() + "₫</span>\n" +
                            "                                    <span class=\"new_price\">" + (item.getPrice() - item.getDiscount()) + "₫</span>\n" +
                            "                                </div>\n" +
                            "                                <div class=\"cart_product_quantity cart_quantity\">\n" +
                            "                                    <button class=\"down\" type=\"button\" onclick=\"reduce(" + item.getId() + ")\"><span>-</span></button>\n" +
                            "                                    <input type=\"text\" value=\"" + item.getQuantityInCart() + "\" class=\"quantity_cart\">\n" +
                            "                                    <button class=\"up\" type=\"button\" onclick=\"increasing(" + item.getId() + ")\"><span>+</span></button>\n" +
                            "                                </div>\n" +
                            "                                <div class=\"cart_product_total\">\n" +
                            "                                    <span>" + (item.getPrice() - item.getDiscount()) + "₫</span>\n" +
                            "                                </div>\n" +
                            "                                <div class=\"cart_product_action\">\n" +
                            "                                    <button type=\"button\" class=\"cart_delete_btn\" onclick=\"deleteProductInCart("+item.getId()+")\">Xoá</button>" +
                            "                                </div>\n" +
                            "                            </div>\n";
        }
        return result;
    }

    private int totalPrice(List<product> list) {
        int total = 0;
        for (product item : list) {
            total += (item.getPrice() - item.getDiscount()) * item.getQuantityInCart();

        }
        return total;
    }

    private void reduce(List<product> list, int pid) {
        for (product item : list) {
            if (item.getId() == pid) {
                item.setQuantityInCart(item.getQuantityInCart() - 1);
                if (item.getQuantityInCart() < 1) {
                    item.setQuantityInCart(1);
                }
                System.out.println("down down down");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        int pid = Integer.parseInt(request.getParameter("pid"));
        List<product> listP = (List<product>) session.getAttribute("cart");

        reduce(listP, pid);

        response.getWriter().println(
                "<div class=\"cart_content\">" +
                        productsHtml(listP) +
                        "                    </div>" +
                        "<div class=\"cart_total_price\">\n" +
                        "                        <div class=\"total_price\">\n" +
                        "                            <span>Tổng tiền: " + totalPrice(listP) + "₫</span>\n" +
                        "                        </div>\n" +
                        " <button type=\"submit\" class=\"pay\">Thanh toán</button>" +
                        "                    </div>"
        );
    }
}