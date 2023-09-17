<%--
  Created by IntelliJ IDEA.
  User: nguyenvikhang88
  Date: 9/11/2023
  Time: 5:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <!-- lib -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://unpkg.com/tippy.js@6/animations/scale.css"/>
    <!-- css -->
    <link rel="stylesheet" href="./css/style.css"/>
    <link rel="stylesheet" href="./css/header.css"/>
    <title>Title</title>
</head>
<body>
<div class="container">
    <header>
        <div class="wrapper">
            <div class="logo">
                <span>shop</span>
            </div>
            <div class="search_wrapper">
                <input type="text" class="search" id="search-input"
                       placeholder="Tìm kiếm sản phẩm..."/>
                <button class="search_btn">
                    <i class="fa-solid fa-magnifying-glass"></i>
                </button>
            </div>
            <div class="action">
                <button class="cart_btn" id="cart_btn">
                    <a href="LoadProductInCart">
                        <i class="bx bx-cart"></i>
                        <c:if test="${sessionScope.cart.size() >= 1}">
                            <div class="quantity">
                                <span>${sessionScope.quantityInCart}</span>
                            </div>
                        </c:if>
                    </a>
                </button>
                <div class="popper_wrapper">
                    <h3 class="popper_title">
                        Sản phẩm mới thêm
                    </h3>
                    <div class="popper_content">
                        <c:forEach items="${sessionScope.cart}" var="p">
                            <div class="product">
                                <div class="product_image">
                                    <img src="${p.image}"
                                         alt="">
                                </div>
                                <div class="product_title"><a href="">${p.title}</a></div>
                                <div class="product_price">
                                    <span>${p.price - p.discount}₫</span>
                                </div>
                            </div>
                        </c:forEach>

                    </div>
                    <div class="popper_footer">
                        <div class="message">
                            <span>${sessionScope.cart.size()} sản phẩm trong giỏ hàng</span>
                        </div>
                        <a href="LoadProductInCart" class="more_product">Xem giỏ hàng</a>
                    </div>
                </div>
            </div>
        </div>
    </header>
</div>
</body>
</html>
