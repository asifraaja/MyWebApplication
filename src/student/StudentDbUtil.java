package student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import dbUtil.DatabaseUtil;

public class StudentDbUtil {
	private static Connection con = null;
	private static Statement stmt = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private DataSource datasrc;
	private String result;
	
	public StudentDbUtil(DataSource datasrc) throws SQLException {
		this.datasrc = datasrc;
		con = datasrc.getConnection();
		System.out.println("Database connected....");
	}
	
	public  List<Student> searchStudents(String keyword,String selectby){
		List<Student> students = new ArrayList<Student>();
		selectby = selectby.toLowerCase();
		String sql;
		try {
			if(keyword.equalsIgnoreCase("all"))
				sql = "select * from student order by "+selectby;
			else
				sql = "select * from student where "+selectby+" like '"+keyword+"%' order by "+selectby;
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Student student = new Student();
				student.setRollnum(rs.getInt("rollnum"));
				student.setFname(rs.getString("firstname"));
				student.setLname(rs.getString("lastname"));
				student.setGender(rs.getString("gender"));
				student.setDept(rs.getString("department"));
				student.setMobileNum(rs.getString("mobilenumber"));
				students.add(student);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return students;
	}
	
	public String addStudent(Student student, String type) {
		String sql;
		try {
			int rollnum = student.getRollnum();
			
			sql = "select count(*) as stcount from student where rollnum=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, rollnum);
			ResultSet rs = ps.executeQuery();
			int studentCount = 0;
			if(rs.next())
				studentCount = rs.getInt("stcount");
			if(studentCount != 0) {
				student.setRollnum();
				System.out.println("Trying with other rollnumber...");
				addStudent(student,type);
			}else {
				result = "adding student";
				sql = "insert into student"+
				" values (?,?,?,?,?,?)";
				ps = con.prepareStatement(sql);
				ps.setInt(1,rollnum); ps.setString(2, student.getFname());
				ps.setString(3, student.getLname()); ps.setString(4, student.getGender());
				ps.setString(5, student.getDept()); ps.setString(6, student.getMobileNum());
				int success = ps.executeUpdate();
				if(success == 1) {
					System.out.println("Student is added...");
					Address address = student.getAddress(type);
					sql = "insert into address (rollnum,type,doorno,street,area,city,pincode) "
							+ "values(?,?,?,?,?,?,?)";
					ps = con.prepareStatement(sql);
					ps.setInt(1, rollnum); ps.setString(2, type);
					ps.setString(3, address.getDoorno()); ps.setString(4, address.getStreet());
					ps.setString(5, address.getArea()); ps.setString(6, address.getCity());
					ps.setInt(7, address.getPincode());
					success = ps.executeUpdate();
					if(success == 1)
						result = "Address Added";
					else {
						System.out.println("Address could not be added....");
						System.out.println("Removing student from....");
						removeStudent(rollnum);
						result = "address not added";
					}	
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(result.equalsIgnoreCase("adding student") || result.equalsIgnoreCase("address not added")) {
				removeStudent(student.getRollnum());
				result = "Student not added";
			}else {
				result = "Student added";
			}
		}
		return result;
	}
	
	public static String removeStudent(int rollnum) {
		try {
			String sql = "delete from address where rollnum=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, rollnum);
			int success = ps.executeUpdate();
				sql = "delete from student where rollnum=?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, rollnum);
				success = ps.executeUpdate();
				if(success == 1) {
					return "Student deleted";
				}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "Student not deleted";
	}
	
	public static String updateAddress(int rollnum, Address address, String type) {
		try {
			String sql = "update address set doorno="+address.getDoorno()
			+",street="+address.getStreet()+",area="+address.getArea()+
			",city="+address.getCity()+",pincode="+address.getPincode()+
			" where rollnum="+rollnum+" and type="+type;
			ps = con.prepareStatement(sql);
			int success = ps.executeUpdate();
			if(success == 1) {
				return "Address updated";
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "Address not updated";
	}
	
	public Student getStudent(int rollnum) {
		String sql;
		Student student = new Student();
		try {
			sql = "select * from student where rollnum="+rollnum;
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			System.out.println("Getting detailss of "+rollnum);
			while(rs.next()) {
				sql = "select * from address where rollnum="+rollnum;
				stmt = con.createStatement();
				ResultSet rs1 = stmt.executeQuery(sql);
				while(rs1.next()) {
					System.out.println("Getting address of "+rs1.getString("type"));
					Address address = new Address(
							rs1.getString("doorno"),rs1.getString("street"),
							rs1.getString("area"),rs1.getString("city"),rs1.getInt("pincode")
							);
					student.setAddress(rs1.getString("type"), address);
				}
				student.setRollnum(rollnum);
				student.setFname(rs.getString("firstname"));
				student.setLname(rs.getString("lastname"));
				student.setGender(rs.getString("gender"));
				student.setDept(rs.getString("department"));
				student.setMobileNum(rs.getString("mobilenumber"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Got all details");
		return student;
	}

	public void close() throws SQLException {
		con.close();
		
	}
}
