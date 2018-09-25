package common.utils;

import com.jfinal.plugin.activerecord.ICallback;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
public class BorrowService implements ICallback{
	public String oid = null;  
    public String reader_id=null;  
    int result;  
    public String reason="数据库意外,请重试";  
    public void run(Connection conn) throws SQLException {      
    CallableStatement proc = null;  
           try {             
   
            proc = (CallableStatement) conn.prepareCall("{ call borrow(?,?,?,?) }"); // borrow为mysql的存储过程名，其中有两个参数，两个返回值  
            proc.setString(1, oid);//设置参数值  
            proc.setString(2, reader_id);  
            proc.registerOutParameter(3, java.sql.Types.INTEGER);//设置返回值类型  
            proc.registerOutParameter(4, java.sql.Types.VARCHAR);  
            proc.execute();  
   
            result =  proc.getInt(3);//得到返回值  
            reason=proc.getString(4);  
      }catch(Exception e){  
              e.printStackTrace();  
       } finally {  
            proc.close();
            conn.close();
          }  
    }
	@Override
	public Object call(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}  
	//内部类结束  
	//调用内部类方法  
/*	  public void trackresult(String reader_id){//可以加参数  
	           BorrowDbPro  borrowDbPro =new BorrowDbPro();  
	             borrowDbPro.reader_id=reader_id;  
	            Db.execute(borrowDbPro);  
	            String reason=borrowDbPro.reason;    
	  }  */
}  
