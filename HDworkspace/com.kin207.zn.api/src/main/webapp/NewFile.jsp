<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="employee/picupload" method="post" enctype="multipart/form-data">  
       <table width="60%" border="1" cellspacing="0">  
           <tr>  
               <td width="35%"><strong>File to upload</strong></td>  
               <td width="65%"><input type="file" name="file" /></td>  
           </tr>  
           <!--   
           <tr>  
               <td><strong>Notes</strong></td>  
               <td><input type="text" name="notes" width="60" /></td>  
           </tr>  
            -->  
             
           <tr>  
               <td>&nbsp;</td>  
               <td><input type="submit" name="submit" value="Add"/></td>  
           </tr>  
       </table>  
   </form>  
</body>
</html>