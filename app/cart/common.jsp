<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

 <div class="menu">
<ul>
<li style="width:0px;"><a></a></li>
<li class="active"><a href="<%=request.getContextPath() %>/goods/listGoods.action" class="wishlist">物品列表 (${empty goodList?0:goodList.totalSize})</a></li>
<li><a href="<%=request.getContextPath() %>/carts/queryCart.action" class="cart">购物车(${empty cartList?0:cartList.totalSize})</a></li>
<li style="float:right;"><a href="login.jsp" >登录账号:jiaozi</a></li>
<li style="float:right;"><a href="login.jsp" >注销</a></li>
</ul>
</div>

