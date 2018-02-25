package gao.servlet;

import java.io.IOException;
import gao.java.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ManagerServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
	//response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");//���ñ��뷽ʽ������������
		request.setCharacterEncoding("utf-8");
		DBHandle dbh=new DBHandle();
		int pageSize=2;
		int pageCount=0;
		int pageNow=1;
		
		System.out.println("I am the value fo pageSize "+ pageSize);
		System.out.println("select  * from Tea_tb  Tea_ID limit "+pageSize*pageNow+","+pageSize);
		String limitSql=" limit "+pageSize*(pageNow-1)+","+pageSize;
		int rowCount=0;//�����ĸ��ֲ�����,���ڷ�ҳ����
		String flag=request.getParameter("flag");
		String pageNowStr=request.getParameter("pageNow");
		if(pageNowStr==null||pageNowStr=="") pageNowStr="1";//Ĭ��pageNow=1������һЩ����Ҫ���쳣����null���գ������쳣
		pageNow=Integer.parseInt(pageNowStr);
		HttpSession sess=request.getSession();
		String user=(String)sess.getAttribute("user");
		if(flag.equals("teacher")){			
			String name=request.getParameter("name");//�õ�ģ����ѯ�����������
		  	ArrayList<Teacher> al=new ArrayList<Teacher>();
			if(name!=null){//�����ж��ǲ��ǵ�һ����ʾ����ͨ������ҳ���ѧ���������Ӷ���
				if(name!=""){//�����ж��ǲ�����Ҫ���������в�ѯ					
						al=dbh.getTeacher("select  * from Tea_tb where Tea_name like '%"+name+"%'  Tea_ID  limit  "+pageSize*(pageNow-1)+","+pageSize);
						rowCount=dbh.getRowCount("select count(*) from Tea_tb where Tea_name like '%"+name+"%'");
				}else{//��������ѯ����ʾ���н�ʦ��Ϣ
					al=dbh.getTeacher("select * from Tea_tb  Tea_ID  limit  "+pageSize*(pageNow-1)+","+pageSize);
				    rowCount=dbh.getRowCount("select count(*) from Tea_tb");
				}	
				request.setAttribute("name", name);
			}else{//��һ����ʾ���н�ʦ��Ϣ��
				System.out.println("select * from Tea_tb  Tea_ID limit "+pageSize*(pageNow-1)+","+pageSize);
				al=dbh.getTeacher("select  * from Tea_tb  Tea_ID limit "+pageSize*(pageNow-1)+","+pageSize);
			    rowCount=dbh.getRowCount("select count(*) from Tea_tb");
			    request.setAttribute("name", "");
			}			
		    if(rowCount%pageSize==0) 
		    	pageCount=rowCount/pageSize;
		    else 
		    	pageCount=rowCount/pageSize+1;
		    //ͨ��rowCount��pageSize�Ĺ�ϵ������pageCount��ֵ	   
		    request.setAttribute("Teacher", al);
			request.setAttribute("pageNow", pageNowStr);
			request.setAttribute("pageCount",pageCount+"");
			request.setAttribute("rowCount", rowCount+"");	
			System.out.print("select * from Tea_tb  Tea_ID limit "+pageSize*(pageNow-1)+","+pageSize);
			System.out.println("select * from Tea_tb  Tea_ID limit "+pageSize*(pageNow-1)+","+pageSize);
         			request.getRequestDispatcher("Manager_Teacher.jsp").forward(request,response );			
		}else if(flag.equals("student")){
			String class_name=request.getParameter("Class_name");
			int class_ID=dbh.getID("select Class_ID from Class_tb where Class_name='"+class_name+"'");
			//�õ����༶��ѯ�İ༶�����ݴ˲�ѯ��ID��
			String name=request.getParameter("name");//�õ�ģ����ѯ�����������
		  	ArrayList<Student> al=new ArrayList<Student>();
			if(class_name!=null||name!=null){//�����ж��ǲ��ǵ�һ����ʾ����ͨ��������ҳ���ѧ���������Ӷ���
				if(class_name!=""||name!=""){//�����ж��ǲ�����Ҫ���������в�ѯ
					if(class_name==""){//�����ֽ���ģ����ѯ
						al=dbh.getStudent("select  * from Stu_tb where Stu_name like '%"+name+"%' and Stu_ID limit "+pageSize*(pageNow-1)+","+pageSize);
						rowCount=dbh.getRowCount("select count(*) from Stu_tb where Stu_name like '%"+name+"%'");
					}else if(name==""){//���༶���в�ѯ
						al=dbh.getStudent("select  * from Stu_tb where Stu_class= '"+class_ID+"' and Stu_ID limit "+pageSize*(pageNow-1)+","+pageSize);
						rowCount=dbh.getRowCount("select count(*) from Stu_tb where Stu_class='"+class_ID+"'"); 
					}else{//���༶������ͬʱ���в�ѯ
						al=dbh.getStudent("select  * from Stu_tb where Stu_class='"+class_ID+"' and Stu_name like '%"+name+"%' and Stu_ID limit "+pageSize*(pageNow-1)+","+pageSize);
						rowCount=dbh.getRowCount("select count(*) from Stu_tb where Stu_class='"+class_ID+"' and Stu_name like '%"+name+"%'");
					}
				}else{//��������ѯ����ʾ����ѧ����Ϣ
					al=dbh.getStudent("select * from Stu_tb where Stu_ID limit  "+pageSize*(pageNow-1)+","+pageSize);
				    rowCount=dbh.getRowCount("select count(*) from Stu_tb");
				}
				request.setAttribute("name", name);
				request.setAttribute("class", class_name );
			}else{//��һ����ʾ����ѧ����Ϣ��
				al=dbh.getStudent("select * from Stu_tb  Stu_ID limit  "+pageSize*(pageNow-1)+","+pageSize);
			    rowCount=dbh.getRowCount("select count(*) from Stu_tb");
			    request.setAttribute("name", "");
				request.setAttribute("class", "");
			}			
		    if(rowCount%pageSize==0) pageCount=rowCount/pageSize;
		    else pageCount=rowCount/pageSize+1;
		    //ͨ��rowCount��pageSize�Ĺ�ϵ������pageCount��ֵ
		    ArrayList<Banji> Banji=dbh.getBanji("select distinct * from class_tb");
		    Iterator<Banji> it=Banji.iterator();
		    while(it.hasNext()){
		    	Banji banji=it.next();
		    	System.out.println(banji.getClass_name()+"  "+banji.getClass_ID());
		    	
		    }
	
		    ArrayList<String>className=dbh.getAttribute("select Class_name from class_tb");
		    //�õ����еİ༶�����ƣ��������Ƿ���һ�������� 		   
		    request.setAttribute("Student", al);
			request.setAttribute("pageNow", pageNowStr);
			request.setAttribute("pageCount",pageCount+"");
			request.setAttribute("rowCount", rowCount+"");
			request.setAttribute("Banji", Banji);	
			request.setAttribute("Class_name", className);
			request.getRequestDispatcher("Manager_Student.jsp").forward(request,response );
		}else if(flag.equals("course")){			
			String name=request.getParameter("name");//�õ�ģ����ѯ�����������
		  	ArrayList<Course> al=new ArrayList<Course>();
			if(name!=null){//�����ж��ǲ��ǵ�һ����ʾ����ͨ��������ҳ���ѧ���������Ӷ���
				if(name!=""){//�����ж��ǲ�����Ҫ���������в�ѯ					
						al=dbh.getCourse("select  * from Cou_tb where Cou_name like '%"+name+"%' and Cou_ID limit "+pageSize*(pageNow-1)+","+pageSize);
						rowCount=dbh.getRowCount("select count(*) from Cou_tb where Cou_name like '%"+name+"%'");
				}else{//��������ѯ����ʾ����ѧ����Ϣ
					al=dbh.getCourse("select  * from Cou_tb where Cou_ID limit "+pageSize*(pageNow-1)+","+pageSize);
				    rowCount=dbh.getRowCount("select count(*) from Cou_tb");
				}
				request.setAttribute("name", name);	
			}else{//��һ����ʾ����ѧ����Ϣ��
				al=dbh.getCourse("select  * from Cou_tb where Cou_ID limit "+pageSize*(pageNow-1)+","+pageSize);
			    rowCount=dbh.getRowCount("select count(*) from Cou_tb");
			    request.setAttribute("name", "");	
			}			
		    if(rowCount%pageSize==0) pageCount=rowCount/pageSize;
		    else pageCount=rowCount/pageSize+1;
		    //ͨ��rowCount��pageSize�Ĺ�ϵ������pageCount��ֵ	
		    request.setAttribute("Course", al);
			request.setAttribute("pageNow", pageNowStr);
			request.setAttribute("pageCount",pageCount+"");
			request.setAttribute("rowCount", rowCount+"");	
			//System.out.print("hah hehe");
			request.getRequestDispatcher("Manager_Course.jsp").forward(request,response );
			
		}else if(flag.equals("class")){			
			String name=request.getParameter("name");//�õ�ģ����ѯ�����������
			ArrayList<Banji> al=new ArrayList<Banji>();
			if(name!=null){//�����ж��ǲ��ǵ�һ����ʾ����ͨ��������ҳ���ѧ���������Ӷ���
				if(name!=""){//�����ж��ǲ�����Ҫ���������в�ѯ					
						al=dbh.getBanji("select  * from Class_tb where Class_name like '%"+name+"%' and Class_ID limit "+pageSize*(pageNow-1)+","+pageSize);
						rowCount=dbh.getRowCount("select count(*) from Class_tb where Class_name like '%"+name+"%'");
				}else{//��������ѯ����ʾ����ѧ����Ϣ
					al=dbh.getBanji("select  * from Class_tb where Class_ID limit "+pageSize*(pageNow-1)+","+pageSize);
				    rowCount=dbh.getRowCount("select count(*) from Class_tb");
				}
				request.setAttribute("name", name);				
			}else{//��һ����ʾ����ѧ����Ϣ��
				al=dbh.getBanji("select  * from Class_tb where Class_ID limit "+pageSize*(pageNow-1)+","+pageSize);
			    rowCount=dbh.getRowCount("select count(*) from Class_tb");
			    request.setAttribute("name", "");
			}			
		    if(rowCount%pageSize==0) pageCount=rowCount/pageSize;
		    else pageCount=rowCount/pageSize+1;
		    //ͨ��rowCount��pageSize�Ĺ�ϵ������pageCount��ֵ	 
		    request.setAttribute("Banji", al);
			request.setAttribute("pageNow", pageNowStr);
			request.setAttribute("pageCount",pageCount+"");
			request.setAttribute("rowCount", rowCount+"");	
			//System.out.print("hah hehe");
			request.getRequestDispatcher("Manager_Class.jsp").forward(request,response );
			
		}else if(flag.equals("query")){
			
			
			String 	class_name=request.getParameter("Class_name");

			System.out.println("175"+class_name);
			String cou_name=request.getParameter("Cou_name");
			String tea_name=request.getParameter("Tea_name");
			String stu_name=request.getParameter("Stu_name");
			response.setCharacterEncoding("UTF-8");//���ñ��뷽ʽ������������
			int class_ID=dbh.getID("select Class_ID from Class_tb where Class_name='"+class_name+"'");
			int cou_ID=dbh.getID("select Cou_ID from Cou_tb where Cou_name='"+cou_name+"'");
			int tea_ID=dbh.getID("select Tea_ID from Tea_tb where Tea_name='"+tea_name+"'");	
			int stu_ID=dbh.getID("select Stu_ID from stu_tb where Stu_name='"+stu_name+"'");
			///��ѯ�ɼ�
			String queryScore="select * from(select stu_tb.stu_ID  "+"���"+ ",stu_tb.Stu_ID,class_tb.Class_ID,cou_tb.Cou_ID,tea_tb.Tea_ID,score_tb.Score_score from  stu_tb "+
					"left join class_tb  on stu_tb.stu_class=class_tb.Class_ID "+
					"LEFT JOIN  tea_cou_class_tb  on tea_cou_class_tb.class_ID=stu_tb.stu_class   "  +
					"LEFT JOIN  score_tb          on stu_tb.Stu_ID=score_tb.stu_ID,"+
					"tea_tb,cou_tb  "+
					"where  tea_tb.tea_ID=tea_cou_class_tb.tea_ID   and cou_tb.Cou_ID=tea_cou_class_tb.Cou_ID ";
			String queryScoreWhere="";
		 limitSql="Stu_ID limit "+pageSize*(pageNow-1)+","+pageSize;
		  	ArrayList<Score> al=new ArrayList<Score>();
			if(class_name!=null||cou_name!=null||tea_name!=null||stu_name!=null){//�����ж��ǲ��ǵ�һ����ʾ����ͨ��������ҳ���ѧ���������Ӷ���
				if(class_name!=""){//�����ж��ǲ�����Ҫ���������в�ѯ
					if(cou_name!=""){//���༶�Ϳγ̲�ѯ
						if(stu_name==null)
							stu_name="";
						if(class_name==null)
							class_name="";
						if(cou_name==null)
							cou_name="";
						if(tea_name==null)
							tea_name="";
					queryScoreWhere="  and stu_tb.stu_name like '%"+stu_name+"%' AND  class_tb.Class_name='"+class_name+"'  and cou_tb.cou_name='"+cou_name+"'  and tea_tb.Tea_name like '%"+tea_name+"%'  )";
					System.out.println(queryScore+queryScoreWhere+limitSql);	
					al=dbh.getScore(queryScore+queryScoreWhere+limitSql);
			
					rowCount=dbh.getRowCount("select count(*)   from  ("+queryScore+"  "+ queryScoreWhere +" tmp )   tmp2 ");
					}
					else {
						if(tea_name!=""){//���༶�ͽ�ʦ��ѯ		
							if(stu_name==null)
								stu_name="";
							if(class_name==null)
								class_name="";
							if(cou_name==null)
								cou_name="";
							if(tea_name==null)
								tea_name="";
							queryScoreWhere="  and stu_tb.stu_name like '%"+stu_name+"%' AND  class_tb.Class_name='"+class_name+"'  and cou_tb.cou_name like '%"+cou_name+"%'  and tea_tb.Tea_name ='"+tea_name+"'  )";

							al=dbh.getScore(queryScore+queryScoreWhere+limitSql);
							rowCount=dbh.getRowCount("select count(*)   from  ("+queryScore+"  "+ queryScoreWhere +" tmp )   tmp2 ");
						}else{//���༶��ѯ
							if(stu_name==null)
								stu_name="";
							if(class_name==null)
								class_name="";
							if(cou_name==null)
								cou_name="";
							if(tea_name==null)
								tea_name="";

						queryScoreWhere="  and stu_tb.stu_name like '%"+stu_name+"%' AND  class_tb.Class_name='"+class_name+"'  and cou_tb.cou_name like '%"+cou_name+"%'  and tea_tb.Tea_name like '%"+tea_name+"%'  )";

						al=dbh.getScore(queryScore+queryScoreWhere+limitSql);
						rowCount=dbh.getRowCount("select count(*)   from  ("+queryScore+"  "+ queryScoreWhere +"  tmp )   tmp2 ");
						}
					}
				}
			 else{
				 if(cou_name!=""){//���γ̺ͽ�ʦ��ѯ
					 if(tea_name!=""){
							if(stu_name==null)
								stu_name="";
							if(class_name==null)
								class_name="";
							if(cou_name==null)
								cou_name="";
							if(tea_name==null)
								tea_name="";
							
						 queryScoreWhere="  and stu_tb.stu_name like '%"+stu_name+"%' AND  class_tb.Class_name  like '%"+class_name+"%'  and cou_tb.cou_name= '"+cou_name+"'  and tea_tb.Tea_name = '"+tea_name+"'  )";

							al=dbh.getScore(queryScore+queryScoreWhere+limitSql);
							rowCount=dbh.getRowCount("select count(*)   from  ("+queryScore+"  "+ queryScoreWhere +" tmp )   tmp2 ");
					 }else{//���γ̲�ѯ
							if(stu_name==null)
								stu_name="";
							if(class_name==null)
								class_name="";
							if(cou_name==null)
								cou_name="";
							if(tea_name==null)
								tea_name="";
						 queryScoreWhere="  and stu_tb.stu_name like '%"+stu_name+"%' AND  class_tb.Class_name  like '%"+class_name+"%'  and cou_tb.cou_name= '"+cou_name+"'  and tea_tb.Tea_name like '%"+tea_name+"%'  )";
							rowCount=dbh.getRowCount("select count(*)   from  ("+queryScore+"  "+ queryScoreWhere +" tmp )   tmp2 ");

							al=dbh.getScore(queryScore+queryScoreWhere+limitSql);
					 }
				 }
				else{//����ʦ��ѯ
					if(tea_name!=""){
						if(stu_name==null)
							stu_name="";
						if(class_name==null)
							class_name="";
						if(cou_name==null)
							cou_name="";
						if(tea_name==null)
							tea_name="";
						dbh.setString(stu_name,class_name,cou_name,tea_name);
						 queryScoreWhere="  and stu_tb.stu_name like '%"+stu_name+"%' AND  class_tb.Class_name  like '%"+class_name+"%'  and cou_tb.cou_name like '%"+cou_name+"%'  and tea_tb.Tea_name = '"+tea_name+"'  )";
						 System.out.println("236");
							al=dbh.getScore(queryScore+queryScoreWhere+limitSql);
							rowCount=dbh.getRowCount("select count(*)   from  ("+queryScore+"  "+ queryScoreWhere +" tmp )  tmp2 ");
					}
					else{
						if(stu_name==null)
							stu_name="";
						if(class_name==null)
							class_name="";
						if(cou_name==null)
							cou_name="";
						if(tea_name==null)
							tea_name="";
						 queryScoreWhere="  and stu_tb.stu_name like '%"+stu_name+"%' AND  class_tb.Class_name  like '%"+class_name+"%'  and cou_tb.cou_name like '%"+cou_name+"%'  and tea_tb.Tea_name like '%"+tea_name+"%'  )";
						 //  System.out.println(queryScore+" )  "+limitSql);
							al=dbh.getScore(queryScore+queryScoreWhere+limitSql);
							System.out.println("245"+queryScore+queryScoreWhere+limitSql);
							rowCount=dbh.getRowCount("select count(*)   from  ("+queryScore+"  "+ queryScoreWhere +" tmp  ) tmp2 ");
						//	System.out.println("246" +"  "+"select count(*)   from  ("+queryScore+"  "+ queryScoreWhere +"  ) tmp ) tmp2 ");
					}
				 }
				 
				}
				request.setAttribute("class_name", class_name);
				request.setAttribute("tea_name",tea_name);
				request.setAttribute("cou_name", cou_name);
			}else{//��һ����ʾ������Ϣ��
				if(stu_name==null)
					stu_name="";
				if(class_name==null)
					class_name="";
				if(cou_name==null)
					cou_name="";
				if(tea_name==null)
					tea_name="";

			queryScoreWhere="  and stu_tb.stu_name like '%"+stu_name+"%' AND  class_tb.Class_name  like '%"+class_name+"%'  and cou_tb.cou_name like '%"+cou_name+"%'  and tea_tb.Tea_name like '%"+tea_name+"%'  )";
                  
					al=dbh.getScore(queryScore+queryScoreWhere+limitSql);		
					System.out.println(queryScore+queryScoreWhere+limitSql);
					rowCount=dbh.getRowCount("select count(*)   from  ("+queryScore+"  ) tmp ) tmp2 ");
			//	System.out.println("select count(*)   from  ("+queryScore+"  ) tmp ) tmp2 ");
				request.setAttribute("class_name", "");
				request.setAttribute("cou_name", "");
				request.setAttribute("tea_name","");
				//System.out.println("��һ����ʾ������Ϣa1.size:"+al.size());
				//System.out.println("264"+queryScore+queryScoreWhere+limitSql);
			}			
		    if(rowCount%pageSize==0) pageCount=rowCount/pageSize;
		    else pageCount=rowCount/pageSize+1;
		    //ͨ��rowCount��pageSize�Ĺ�ϵ������pageCount��ֵ
		    ArrayList<String> Class_name=dbh.getAttribute("select Class_name from Class_tb");
		    ArrayList<String> Tea_name=dbh.getAttribute("select Tea_name from Tea_tb");
		    ArrayList<String> Cou_name=dbh.getAttribute("select Cou_name from Cou_tb");
		    //�õ����еİ༶�����ƣ��������Ƿ���һ�������� 	
		
		    request.setAttribute("Score", al);
			request.setAttribute("pageNow", pageNowStr);
			request.setAttribute("pageCount",pageCount+"");
			request.setAttribute("rowCount", rowCount+"");			
			request.setAttribute("Class_name", Class_name);
			request.setAttribute("Tea_name",Tea_name);
			request.setAttribute("Cou_name", Cou_name);
			response.setCharacterEncoding("UTF-8");//���ñ��뷽ʽ������������ 
			request.getRequestDispatcher("Manager_Query.jsp").forward(request,response );
		}else if(flag.equals("assign")){
			String class_name=request.getParameter("Class_name");
			String cou_name=request.getParameter("Cou_name");
			String tea_name=request.getParameter("Tea_name");
			
			int class_ID=dbh.getID("select Class_ID from Class_tb where Class_name='"+class_name+"'");
			System.out.println("select Class_ID from Class_tb where Class_name='"+class_name+"'");
			int cou_ID=dbh.getID("select Cou_ID from Cou_tb where Cou_name='"+cou_name+"'");
			int tea_ID=dbh.getID("select Tea_ID from Tea_tb where Tea_name='"+tea_name+"'");
			ArrayList<Tea_Cou_Cla> al=new ArrayList<Tea_Cou_Cla>();
			if(class_name!=null||cou_name!=null||tea_name!=null){//�����ж��ǲ�����������ѯ
				if(class_name!=""){//�����ж��ǲ�����Ҫ���������в�ѯ
					if(cou_name!=""){//���༶�Ϳγ̲�ѯ
						class_ID=dbh.getID("select Class_ID from Class_tb where Class_name='"+class_name+"'");
						cou_ID=dbh.getID("select Cou_ID from Cou_tb where Cou_name='"+cou_name+"'");
						tea_ID=dbh.getID("select Tea_ID from Tea_tb where Tea_name like '%"+tea_name+"%'");
						al=dbh.getTea_Cou_Cla("select *  from (select  * from Tea_Cou_Class_tb where Cou_ID='"+cou_ID+"' and Class_ID='"+class_ID+"'  ) Cou_ID   "+limitSql);
						rowCount=dbh.getRowCount("select count(*) from Tea_Cou_Class_tb where Cou_ID='"+cou_ID+"' and Class_ID='"+class_ID+"'");
					}
					else {
						if(tea_name!=""){//���༶�ͽ�ʦ��ѯ			
							class_ID=dbh.getID("select Class_ID from Class_tb where Class_name='"+class_name+"'");
							tea_ID=dbh.getID("select Tea_ID from Tea_tb where Tea_name='"+tea_name+"'");
							cou_ID=dbh.getID("select Cou_ID from Cou_tb where Cou_name  like  '%"+cou_name+"%'");
							al=dbh.getTea_Cou_Cla(" select *  from (select  * from Tea_Cou_Class_tb where Tea_ID='"+tea_ID+"' and Class_ID='"+class_ID+"' )  Tea_ID  "+limitSql);
							rowCount=dbh.getRowCount("select count(*) from Tea_Cou_Class_tb where Class_ID='"+class_ID+"' and Tea_ID='"+tea_ID+"'"); 
						}else{//���༶��ѯ
							class_ID=dbh.getID("select Class_ID from Class_tb where Class_name='"+class_name+"'");
							tea_ID=dbh.getID("select Tea_ID from Tea_tb where Tea_name like '%"+tea_name+"%'");
							cou_ID=dbh.getID("select Cou_ID from Cou_tb where Cou_name  like  '%"+cou_name+"%'");
							al=dbh.getTea_Cou_Cla(" select  *  from  (select  * from Tea_Cou_Class_tb where Class_ID='"+class_ID+"' )  Tea_ID  "+limitSql);
							rowCount=dbh.getRowCount("select count(*) from Tea_Cou_Class_tb where Class_ID='"+class_ID+"'");
						}
					}
				}
			 else{
				 if(cou_name!=""){//���γ̺ͽ�ʦ��ѯ
					 if(tea_name!=""){
							class_ID=dbh.getID("select Class_ID from Class_tb where Class_name like  '%"+class_name+"%'");
							tea_ID=dbh.getID("select Tea_ID from Tea_tb where Tea_name='"+tea_name+"'");
							cou_ID=dbh.getID("select Cou_ID from Cou_tb where Cou_name='"+cou_name+"'");
						 al=dbh.getTea_Cou_Cla(" select * from (select  * from Tea_Cou_Class_tb where Cou_ID='"+cou_ID+"' and Tea_ID='"+tea_ID+"'  )  Tea_ID  "+limitSql);
						 rowCount=dbh.getRowCount("select count(*) from Tea_Cou_Class_tb where Cou_ID='"+cou_ID+"' and Tea_ID='"+tea_ID+"'"); 
					 }else{//���γ̲�ѯ
							class_ID=dbh.getID("select Class_ID from Class_tb where Class_name like  '%"+class_name+"%'");
							tea_ID=dbh.getID("select Tea_ID from Tea_tb where Tea_name  like '%"+tea_name+"%'");
							cou_ID=dbh.getID("select Cou_ID from Cou_tb where Cou_name='"+cou_name+"'");
						 al=dbh.getTea_Cou_Cla(" select *  from (select  * from Tea_Cou_Class_tb where Cou_ID='"+cou_ID+"' )  Tea_ID  "+limitSql);
						 rowCount=dbh.getRowCount("select count(*) from Tea_Cou_Class_tb where Cou_ID='"+cou_ID+"'");
					 }
				 }
				else{//����ʦ��ѯ 
					if(tea_name!=""){
						class_ID=dbh.getID("select Class_ID from Class_tb where Class_name like  '%"+class_name+"%'");
						tea_ID=dbh.getID("select Tea_ID from Tea_tb where Tea_name='"+tea_name+"'");
						cou_ID=dbh.getID("select Cou_ID from Cou_tb where Cou_name  like '%"+cou_name+"%'");
						al=dbh.getTea_Cou_Cla("select * from (select  * from Tea_Cou_Class_tb where Tea_ID='"+tea_ID+"'  ) Tea_ID  "+limitSql);
						System.out.println("select  * from Tea_Cou_Class_tb where Tea_ID='"+tea_ID+"'   Tea_ID  "+limitSql);
						rowCount=dbh.getRowCount("select count(*) from Tea_Cou_Class_tb where Tea_ID='"+tea_ID+"'");
					}
					else{
						class_ID=dbh.getID("select Class_ID from Class_tb where Class_name like  '%"+class_name+"%'");
						tea_ID=dbh.getID("select Tea_ID from Tea_tb where Tea_name  like '%"+tea_name+"%'");
						cou_ID=dbh.getID("select Cou_ID from Cou_tb where Cou_name   like  '%"+cou_name+"%'");
						al=dbh.getTea_Cou_Cla("select * from Tea_Cou_Class_tb  Tea_ID   "+limitSql);
						rowCount=dbh.getRowCount("select count(*) from Tea_Cou_Class_tb ");
					}
				 }
				 
				}
				request.setAttribute("class_name", class_name);
				request.setAttribute("tea_name",tea_name);
				request.setAttribute("cou_name", cou_name);
			}else{//������,��ʾ���н�ʦ-�༶-�γ���Ϣ��
				al=dbh.getTea_Cou_Cla("select * from Tea_Cou_Class_tb   Tea_ID   "+limitSql);
				rowCount=dbh.getRowCount("select count(*) from Tea_Cou_Class_tb ");
				request.setAttribute("class_name", "");
				request.setAttribute("cou_name", "");
				request.setAttribute("tea_name","");
			}			
		    if(rowCount%pageSize==0) pageCount=rowCount/pageSize;
		    else pageCount=rowCount/pageSize+1;
		    //ͨ��rowCount��pageSize�Ĺ�ϵ������pageCount��ֵ
		    ArrayList<String> Class_name=dbh.getAttribute("select Class_name from Class_tb");
		    ArrayList<String> Tea_name=dbh.getAttribute("select Tea_name from Tea_tb");
		    ArrayList<String> Cou_name=dbh.getAttribute("select Cou_name from Cou_tb");
		    //�õ����еİ༶�����ƣ��������Ƿ���һ�������� 		   
		    request.setAttribute("Tea_Cou_Cla", al);
			request.setAttribute("pageNow", pageNowStr);
			request.setAttribute("pageCount",pageCount+"");
			request.setAttribute("rowCount", rowCount+"");
			request.setAttribute("Class_name", Class_name);
			request.setAttribute("Tea_name",Tea_name);
			request.setAttribute("Cou_name", Cou_name);
			request.getRequestDispatcher("Manager_Assign.jsp").forward(request,response );
		}
		else if(flag.equals("psdAlter")){
			 String old_psd=request.getParameter("old_psd");
			 String new_psd=request.getParameter("new_psd");			 
			 ArrayList<String> psd=dbh.getPassword("select Man_psd from Man_tb where Man_name='"+user+"'");
			 boolean b=false;
			 for(int i=0;i<psd.size();i++){
				 if(old_psd.equals(psd.get(i))){
					 b=true;break;
				 }
			 }
			 if(b){
				 String sql="update Man_tb set Man_psd='"+new_psd+"' where Man_name='"+user+"' and Man_psd='"+old_psd+"'";
				 if(dbh.Update(sql)){
					 sess.setAttribute("message", "�����޸ĳɹ���");
					 request.getRequestDispatcher("Success.jsp").forward(request, response);
				 }
			 }else{
				 request.getRequestDispatcher("Manager_psdAlter.jsp?flag=error").forward(request, response);
			 }
			 
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	
	}

}
