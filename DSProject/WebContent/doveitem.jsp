<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<meta charset="UTF-8">
<meta charset="UTF-8">
<title>GoogleSearch</title>
<style type="text/css">
#padding{
	padding: 0px 0px 15px 15px;
}
a {
	color: #0B173B;
	font-size: 30px;
	text-decoration: none;
}
a:hover{
text-decoration:underline;
}
.border-style {
	border-radius: 90px/90px;
}
</style>
</head>
<body>
<body style='background-color: #d5d2c1'>
<form action='${requestUri}' method='get'>

	<div style='position: absolute;margin-top:190px;margin-left:50px'>
		<%
		String[][] orderList = (String[][]) request.getAttribute("query");
		for (int i = 0; i < orderList.length; i++) {
			String s=orderList[i][1];
			s=s.substring(7);
		%>
		
		<a href='<%=s%>'><%=orderList[i][0]%> </a> <br>連結<br>
		<br>
		<%
}
%>
	</div>
	<div>
		<img src="./image/chocoholic.jpg" style='position: absolute; width: 150px; height: 100px; left: 50%; top: 50%; margin-top: -280px; margin-left: -590px'>
	</div>
		<div>
		<input type='text' class="border-style" id="padding" name='keyword'
			style='font-size: 120%; position: absolute; left: 50%; top: 48%; margin-top: -250px; margin-left: -400px; width: 800px; height: 25px'
			placeholder = 'search...' value='<%=request.getParameter("keyword")%>'/>
	</div>

</form>
</body>
</html>
