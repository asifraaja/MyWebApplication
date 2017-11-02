package student;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Resource(name="jdbc/studentDb")
	private DataSource datasrc;
	
	private StudentDbUtil studentDbUtil;
	
	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("Entered here...");
		try {
			studentDbUtil = new StudentDbUtil(datasrc);
		}catch(Exception e) {
			throw new ServletException(e);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Get started");
		String action = req.getParameter("action");
		String success = null;
		HttpSession session = req.getSession();
		RequestDispatcher RequetsDispatcherObj = null;
		switch(action) {
			case "add":
				Student student = null;
				success = addStudent(req,resp,student);
				System.out.println("Success:"+success);
				session.setAttribute("success", success);
				session.setAttribute("student", student);
				RequetsDispatcherObj = req.getRequestDispatcher("/addStudent.jsp");
				break;
			case "search":
				List<Student> students = searchStudent(req,resp);
				for(Student st : students) {
					System.out.println(st.getDept() + st.getFname() + st.getGender() + st.getLname());
				}
				session.setAttribute("students", students);
				RequetsDispatcherObj = req.getRequestDispatcher("/search.jsp");
				break;
		}
		RequetsDispatcherObj.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	private String addStudent(HttpServletRequest request, HttpServletResponse resp,Student student) {
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String gender = request.getParameter("gender");
		String dept = request.getParameter("dept");
		String mobileNum = request.getParameter("mobile");
		
		String doorno = request.getParameter("doorno");
		String street = request.getParameter("street");
		String area = request.getParameter("area");
		String city = request.getParameter("city");
		int pincode = Integer.parseInt(request.getParameter("pincode"));
		String type = request.getParameter("type");
		
		Address address = new Address();
		address.setDoorno(doorno); address.setStreet(street);
		address.setArea(area); address.setCity(city);
		address.setPincode(pincode);
		
		student = new Student();
		student.setRollnum();
		student.setFname(fname); student.setLname(lname);
		student.setGender(gender); student.setDept(dept);
		student.setAddress(type, address); student.setMobileNum(mobileNum);
		
		return studentDbUtil.addStudent(student, type);
	}
	
	private List<Student> searchStudent(HttpServletRequest req, HttpServletResponse resp) {
		String keyword = req.getParameter("search");
		if(keyword.equals(null) || keyword.equalsIgnoreCase("null") || keyword.isEmpty())
			keyword = "all";
		return studentDbUtil.searchStudents(keyword);
	}

}
