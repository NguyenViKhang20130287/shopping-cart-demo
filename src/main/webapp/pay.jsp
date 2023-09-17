<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%----%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%----%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- lib -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://unpkg.com/tippy.js@6/animations/scale.css"/>
    <!-- css -->
    <link rel="stylesheet" href="./css/header.css"/>
    <link rel="stylesheet" href="./css/pay.css">
    <title>Shop - Thanh toán</title>
</head>

<body>
<div class="container">

    <%@include file="header.jsp"%>

    <div class="main">
        <form method="post" action="Pay" class="form-wrapper">
            <div class="main_wrapper">
                <div class="form-main">
                    <div class="form-group">
                        <label for="">Họ và tên</label>
                        <input type="text" name="fullName">
                    </div>
                    <div class="form-group">
                        <label for="">Tên đường</label>
                        <input type="text" name="streetName">
                    </div>
                    <div class="form-group">
                        <label for="">Phường/xã</label>
                        <input type="text" name="sub-district">
                    </div>
                    <div class="form-group">
                        <label for="">Quận/huyện</label>
                        <input type="text" name="district">
                    </div>
                    <div class="form-group">
                        <label for="">Tỉnh/thành phố</label>
                        <input type="text" name="city">
                    </div>
                    <div class="form-email-phone">
                        <div class="email">
                            <label for="">Địa chỉ email</label>
                            <input type="text" name="email">
                        </div>
                        <div class="phone">
                            <label for="">Số điện thoại</label>
                            <input type="text" name="phone">
                        </div>
                    </div>
                </div>
                <div class="order">
                    <div class="title">
                        <h3>Đơn hàng của bạn</h3>
                    </div>
                    <div class="product_header">
                        <div class="product_info">
                            <span>Sản phẩm</span>
                        </div>
                        <div class="product_quantity">
                            <span>Số lượng</span>
                        </div>
                        <div class="product_total">
                            Gía
                        </div>
                    </div>
                    <div class="products">

                        <c:choose>
                            <c:when test="${sessionScope.productChecked == null && productBuyNow != null}">
                                <input value="buyNow" name="status" style="display: none">
                                <input type="text" name="pid" value="${productBuyNow.id}" style="display: none">
                                <div class="product">
                                    <div class="product_info">
                                        <div class="product_image">
                                            <img src="${productBuyNow.image}"
                                                 alt="">
                                        </div>
                                        <div class="product_title">
                                            <span>${productBuyNow.title}</span>
                                        </div>
                                    </div>
                                    <div class="product_quantity">
                                        <span>${productBuyNow.quantityInCart}</span>
                                    </div>
                                    <div class="product_total">
                                        <span>${(productBuyNow.price  - productBuyNow.discount) * productBuyNow.quantityInCart}₫</span>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${sessionScope.productChecked != null && productBuyNow != null}">
                                <input value="buyNow" name="status" style="display: none">
                                <input type="text" name="pid" value="${productBuyNow.id}" style="display: none">
                                <div class="product">
                                    <div class="product_info">
                                        <input type="text" name="pid" value="${productBuyNow.id}" style="display: none">
                                        <div class="product_image">
                                            <img src="${productBuyNow.image}"
                                                 alt="">
                                        </div>
                                        <div class="product_title">
                                            <span>${productBuyNow.title}</span>
                                        </div>
                                    </div>
                                    <div class="product_quantity">
                                        <span>${productBuyNow.quantityInCart}</span>
                                    </div>
                                    <div class="product_total">
                                        <span>${(productBuyNow.price  - productBuyNow.discount) * productBuyNow.quantityInCart}₫</span>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${sessionScope.productChecked != null && productBuyNow == null}">
                                <c:forEach items="${sessionScope.productChecked}" var="p">
                                    <input value="buyCart" name="status" style="display: none">
                                    <div class="product">
                                        <div class="product_info">
                                            <input type="text" name="pid" value="${p.id}" style="display: none">
                                            <div class="product_image">
                                                <img src="${p.image}"
                                                     alt="">
                                            </div>
                                            <div class="product_title">
                                                <span>${p.title}</span>
                                            </div>
                                        </div>
                                        <div class="product_quantity">
                                            <span>${p.quantityInCart}</span>
                                        </div>
                                        <div class="product_total">
                                            <span>${(p.price  - p.discount) * p.quantityInCart}₫</span>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                        </c:choose>


                        <!--  -->

                    </div>
                    <div class="footer">
                        <c:choose>
                            <c:when test="${sessionScope.productChecked == null && productBuyNow != null}">
                                <div class="total_price_order">
                                    <span>Tổng đơn hàng ${(productBuyNow.price  - productBuyNow.discount) * productBuyNow.quantityInCart}₫</span>
                                </div>
                            </c:when>
                            <c:when test="${sessionScope.productChecked != null && productBuyNow != null}">
                                <div class="total_price_order">
                                    <span>Tổng đơn hàng ${(productBuyNow.price  - productBuyNow.discount) * productBuyNow.quantityInCart}₫</span>
                                </div>
                            </c:when>
                            <c:when test="${productChecked != null}">
                                <div class="total_price_order">
                                    <span>Tổng đơn hàng ${total}₫</span>
                                </div>
                            </c:when>
                        </c:choose>

                        <button type="submit" class="pay_btn">Đặt hàng</button>
                    </div>
                </div>
            </div>
        </form>

    </div>


</div>
</body>
<script src="https://unpkg.com/@popperjs/core@2"></script>
<script src="https://unpkg.com/tippy.js@6"></script>
<script>
    var popper = document.querySelector('.popper_wrapper')
    tippy('#cart_btn', {
        interactive: true,
        content: popper.innerHTML,
        allowHTML: true,
        delay: [300, 200],
    });
</script>
</html>