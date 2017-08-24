<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="css/css.css" />
    <title>商品列表页面</title>
    
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
            <td class="image" >产品展示</td>
            <td class="name"  >产品名称</td>
            <td class="model">类别</td>
            <td class="stock">仓库</td>
            <td class="price">单价</td>
            <td class="cart">加入购物车</td>
          </tr>
        </thead>
        <tbody>
          
          
            <c:forEach items="${goodList.list}" var="god">
                    <tr>
            
            <td class="image"><a href="#"><img src="<%=basePath%>${god.imagePath }" alt="iMac" title="iMac" /></a>
              </td>
            <td class="name"><a href="#"><font size=3>${god.name }</font></a>
            <br/><br/><div style='width:90%'>${god.descp }</div></div>
            </td>
            <td class="model">${god.model }</td>
            <td class="stock">${god.stock }</td>
            <td class="price"><div><font color=red>¥${god.price }</font></div>
              </td>
            <td ><a class="button" href="<%=request.getContextPath() %>/carts/addCart.action?goodId=${god.id }"><span>加入购物车</span></a></td>
          </tr>
          
          </c:forEach>
                    
          <tr>
          			<td colspan="6" style="text-align:right">
          			<a style="text-decoration none" href="<%=basePath%>/goods/listGoods.action?curPage=1">首页</a> 
          			<a style="text-decoration none" href="<%=basePath%>/goods/listGoods.action?curPage=${goodList.prePage}">上一页 </a>
          			&nbsp;<input name='curPage' style='width:20px;height:20px' value='${goodList.curPage}'/>
          			<a style="text-decoration none" href="<%=basePath%>/goods/listGoods.action?curPage=${goodList.nextPage}">下一页</a>
          			<a style="text-decoration none" href="<%=basePath%>/goods/listGoods.action?curPage=${goodList.totalPage}"> 尾页</a>&nbsp;<span style='margin-top:1px'>共${goodList.totalPage}页</span></td>
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
