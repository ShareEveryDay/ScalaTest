package cn.tedu.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.mchange.v2.c3p0.ComboPooledDataSource;

@WebServlet("/DataView")
public class DataView extends HttpServlet {
	private ComboPooledDataSource c3p0=new ComboPooledDataSource();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//每次请求向前台的页面发送一组数据 形式[pv,uv,vv,newip,newcount]
		Connection conn=null;
		PreparedStatement ps1=null;
		ResultSet rs=null;
		try{
			//设置当前的日期时间
			SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd");
			String todayTime=sdf.format(System.currentTimeMillis());
			//设置x轴的刻度数字
			SimpleDateFormat sdf2=new SimpleDateFormat("mm-ss");
			String perTime=sdf2.format(System.currentTimeMillis());
			//连接数据库
			conn=c3p0.getConnection();
			ps1=conn.prepareStatement("select * from tongji2 where reprottime=?");
			ps1.setString(1, todayTime);
			rs=ps1.executeQuery();
			
			if(rs.next()){
				//表示当天是有数据的
				
				//返回的形式：[pv,uv,vv,newip,newcust]
				
				int pv=rs.getInt("pv");
				int uv=rs.getInt("uv");
				int vv=rs.getInt("vv");
				int newip=rs.getInt("newip");
				int newcust=rs.getInt("newcust");
				String result="["+pv+","+uv+","+vv+","+newip+","+newcust+","+perTime+"]";
			}else{
				//表示当天是没有数据的 则每个数的默认指标为0
				String result="[0,0,0,0,0"+","+perTime+"]";
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(ps1!=null){
				try {
					ps1.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//测试
		/*String result="[";
		for(int i=0;i<4;i++){
			int num=new Random().nextInt(100);
			result=result+num+",";
		}
		int num=new Random().nextInt(100);
		result=result+num+"]";
		response.getWriter().write(result);*/
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
