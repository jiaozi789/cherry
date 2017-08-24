<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
    <title>购物车界面</title>
    <link rel="stylesheet" type="text/css" href="css/css.css" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <jsp:include page="common.jsp"></jsp:include>

<div class="header">
</div>


<div style="width:1000px;margin: 0 auto;margin-top:10px;">
<div class="linktree">

<br>
 
  </div>
<div id="content">  
    <form action="#" method="post" id="wishlist">
    <div class="wishlist-product">
      <table>
          <thead>
            <tr>
              <td class="remove">Remove</td>
              <td class="image">Image</td>
              <td class="name">Product Name</td>
              <td class="model">Model</td>
              <td class="quantity">Quantity</td>
              <td class="price">Unit Price</td>
              <td class="total">Total</td>
            </tr>
          </thead>
          <tbody>
          	  <c:forEach  items="${cartList.list}" var="cart"> 
	              <tr>
		              <td class="remove"><input type="checkbox" name="remove[]" value="41" /></td>
		              <td class="image"><a href="#"><img src="<%=request.getContextPath() %>${cart.goodImagePath}" alt="iMac" title="iMac" /></a>
		                </td>
		              <td class="name"><a href="#">${cart.goodName }</a>
		              <div>
		              </div>
		                </td>
		              <td class="model">Product 14</td>
		              <td class="quantity"><input type="text" name="quantity[41]" value="${cart.pCount}" size="3" /></td>
		              <td class="price">$${cart.goodPrice }</td>
		              <td class="total">$${cart.goodPrice*cart.pCount}</td>
	            </tr>
            </c:forEach>
           <tr>
          			<td colspan="6" style="text-align:right">
          			<a style="text-decoration none" href="<%=basePath%>/servlet/cartServlet?curPage=1">首页</a> 
          			<a style="text-decoration none" href="<%=basePath%>//servlet/cartServlet?curPage=${cartList.prePage}">上一页 </a>
          			&nbsp;<input name='curPage' style='width:20px;height:20px' value='${cartList.curPage}'/>
          			<a style="text-decoration none" href="<%=basePath%>//servlet/cartServlet?curPage=${cartList.nextPage}">下一页</a>
          			<a style="text-decoration none" href="<%=basePath%>//servlet/cartServlet?curPage=${cartList.totalPage}"> 尾页</a>&nbsp;<span style='margin-top:1px'>共${goodList.totalPage}页</span></td>
           </tr>
            </tbody>
        </table>
    </div>
  </form>
  <div class="buttons">
    <div class="left"><a href="#" class="button"><span>Back</span></a></div>
    <div class="right"><a class="button"><span>Update</span></a></div>
  </div>
    </div>
</div>
</div>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<div class="icart-footer">
<div class="icart-footer-top">
	<div style="width:1000px; margin:0 auto;">	
	</div>
</div>

		

</div></div>
  </body>
</html>
