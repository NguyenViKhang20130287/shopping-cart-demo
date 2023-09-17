<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%----%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%----%>
<!DOCTYPE html>
<html lang="en">

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
    <title>Shop</title>
</head>

<body>
<div class="container">
    <%--    <header>--%>
    <%--        <div class="wrapper">--%>
    <%--            <div class="logo">--%>
    <%--                <span>shop</span>--%>
    <%--            </div>--%>
    <%--            <div class="search_wrapper">--%>
    <%--                <input type="text" class="search" placeholder="Tìm kiếm sản phẩm..."/>--%>
    <%--                <button class="search_btn">--%>
    <%--                    <i class="fa-solid fa-magnifying-glass"></i>--%>
    <%--                </button>--%>
    <%--            </div>--%>
    <%--            <div class="action">--%>
    <%--                <button class="cart_btn" id="cart_btn">--%>
    <%--                    <a href="LoadProductInCart">--%>
    <%--                        <i class="bx bx-cart"></i>--%>
    <%--                        <c:if test="${sessionScope.cart.size() >= 1}">--%>
    <%--                            <div class="quantity">--%>
    <%--                                <span>${sessionScope.cart.size()}</span>--%>
    <%--                            </div>--%>
    <%--                        </c:if>--%>
    <%--                    </a>--%>
    <%--                </button>--%>
    <%--                <div class="popper_wrapper">--%>
    <%--                    <h3 class="popper_title">--%>
    <%--                        Sản phẩm mới thêm--%>
    <%--                    </h3>--%>
    <%--                    <div class="popper_content">--%>
    <%--                        <c:forEach items="${sessionScope.cart}" var="p">--%>
    <%--                            <div class="product">--%>
    <%--                                <div class="product_image">--%>
    <%--                                    <img src="${p.image}"--%>
    <%--                                         alt="">--%>
    <%--                                </div>--%>
    <%--                                <div class="product_title"><a href="">${p.title}</a></div>--%>
    <%--                                <div class="product_price">--%>
    <%--                                    <span>${p.price - p.discount}₫</span>--%>
    <%--                                </div>--%>
    <%--                            </div>--%>
    <%--                        </c:forEach>--%>

    <%--                    </div>--%>
    <%--                    <div class="popper_footer">--%>
    <%--                        <div class="message">--%>
    <%--                            <span>${sessionScope.cart.size()} sản phẩm trong giỏ hàng</span>--%>
    <%--                        </div>--%>
    <%--                        <a href="LoadProductInCart" class="more_product">Xem giỏ hàng</a>--%>
    <%--                    </div>--%>
    <%--                </div>--%>
    <%--            </div>--%>
    <%--        </div>--%>
    <%--    </header>--%>

    <%@include file="header.jsp" %>

    <div class="main">
        <div class="main_wrapper">
            <div class="main_sidebar">
                <div class="sidebar_title">
                    <h3>
                        Hãng
                    </h3>
                </div>
                <uk class="side_menu">
                    <li class="sidebar_item all active">
                        <button class="cat-btn" onclick="getAllProduct()">Tất cả</button>
                    </li>
                    <li class="sidebar_item akko">
                        <button class="cat-btn" onclick="getProductWithCatName(catName='akko')">Akko</button>
                    </li>
                    <li class="sidebar_item e-dra">
                        <button class="cat-btn" onclick="getProductWithCatName(catName='e-dra')">E-Dra</button>
                    </li>
                    <li class="sidebar_item corsair">
                        <button class="cat-btn" onclick="getProductWithCatName(catName='corsair')">Corsair</button>
                    </li>
                    <li class="sidebar_item razer">
                        <button class="cat-btn" onclick="getProductWithCatName(catName='razer')">Razer</button>
                    </li>

                </uk>
            </div>

            <div class="main_content">
                <c:forEach items="${products}" var="p">
                    <div class="main_content_product">
                        <div class="product_image">
                            <img src="${p.image}" alt="">
                        </div>
                        <div class="product_title">
                            <span>${p.title}</span>
                        </div>
                        <div class="product_price">
                            <span class="new_price">${p.price - p.discount}₫</span>
                            <span class="price">${p.price}₫</span>
                        </div>
                        <div class="product_action">
                            <a href="Checkout?pid=${p.id}" class="buy">Mua</a>
                            <button type="button" class="addToCart" onclick="addToCart(pid=${p.id})">Thêm vào giỏ</button>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>

</div>
</body>
<script src="https://unpkg.com/@popperjs/core@2"></script>
<script src="https://unpkg.com/tippy.js@6"></script>
<script src="https://code.jquery.com/jquery-3.7.0.js"></script>
<script>
    var popper = document.querySelector('.popper_wrapper')
    tippy('#cart_btn', {
        interactive: true,
        content: popper.innerHTML,
        allowHTML: true,
        delay: [300, 200],
    });

    //
    function getProductWithCatName(catName) {

        $('.sidebar_item').each(function () {
            if ($(this).hasClass(catName)) {
                $(this).addClass("active")
            } else {
                $(this).removeClass('active')
            }
        })

        $.ajax({
            url: 'Category',
            type: 'post',
            data: {
                catName: catName
            },
            success: function (data) {
                let wrapper = document.querySelector('.main_content')
                wrapper.innerHTML = data
                // console.log(data)
            },
            error: function (error) {
                console.log(error)
            }
        })
    }

    $(document).ready(function () {
        $('#search-input').on('input', function () {
            let value = $(this).val();
            $.ajax({
                url: 'Search',
                type: 'post',
                data: {
                    searchValue: value,
                },
                success: function (data) {
                    let wrapper = document.querySelector('.main_content')
                    wrapper.innerHTML = data
                    // console.log(data)
                },
                error: function (error) {
                    console.log(error)
                }
            })
        })
    })

    function getAllProduct() {
        $('.all').addClass('active')
        $('.akko').removeClass('active')
        $('.e-dra').removeClass('active')
        $('.corsair').removeClass('active')
        $('.razer').removeClass('active')
        $.ajax({
            url: 'Home',
            type: 'post',
            data: {},
            success: function (data) {
                let wrapper = document.querySelector('.main_content')
                wrapper.innerHTML = data
                // console.log(data)
            },
            error: function (error) {
                console.log(error)
            }
        })
    }

    function addToCart(pid) {
        $.ajax({
            url: 'addToCart',
            type: 'post',
            data: {
                pid: pid
            },
            success: function (data) {
                let action_wrapper = document.querySelector(".action")
                action_wrapper.innerHTML = data
                // console.log(data)
            },
            error: function (error) {
                console.log(error)
            }
        })
    }
</script>

</html>