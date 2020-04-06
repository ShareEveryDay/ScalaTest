<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>网站流量项目的实时可视化</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/echarts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.js"></script>
<script type="text/javascript">

	window.onload=function(){
	
	//1.初始化容器
	myChart = echarts.init(document.getElementById('main')); 
	pvArr=[]
	uvArr=[]
	vvArr=[]
	newipArr=[]
	newcustArr=[]
	xtimeArr=[]
	
	//添加一个定时的触发任务
	window.setInterval(function(){
		$.get("http://localhost:8080/flux/DataView",function(datax){
			if(pvArr.lenght>7){
				pvArr.shift()
				uvArr.shift()
				vvArr.shift()
				newipArr.shift()
				newcustArr.shift()
			}
			pvArr.push(eval(datax)[0])
			uvArr.push(eval(datax)[1])
			vvArr.push(eval(datax)[2])
			newipArr.push(eval(datax)[3])
			newcustArr.push(eval(datax)[4])
			xtimeArr.push(eval(datax)[5])
			//2.准备Option
			option = {
				legend:{
					data:['pv','uv','vv','newip','newcount']
				},
			    xAxis: {
			        type: 'category',
			        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
			    },
			    yAxis: {
			        type: 'value'
			    },
			    series: [{
			    	name:'pv',
			        data: pvArr,
			        type: 'line',
			        smooth: true
			    },{
			    	name:'uv',
			        data: uvArr,
			        type: 'line',
			        smooth: true
			    },{
			    	name:'vv',
			        data: vvArr,
			        type: 'line',
			        smooth: true
			    },{
			    	name:'newip',
			        data: newipArr,
			        type: 'line',
			        smooth: true
			    },{
			    	name:'newcount',
			        data: newcustArr,
			        type: 'line',
			        smooth: true
			    }]
			};
		//3.设置Opiton
		myChart.setOption(option);
		})
	},2000)	 
}
</script>
</head>
<body>
	<div id="main" style="width:1000px;height:400px;"></div>
</body>
</html>