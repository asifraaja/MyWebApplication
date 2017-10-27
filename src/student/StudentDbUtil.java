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
	
	public  List<Student> searchStudents(String keyword){
		List<Student> students = new ArrayList<Student>();
		String sql;
		try {
			if(keyword.equalsIgnoreCase("all"))
				sql = "select * from student order by firstName";
			else
				sql = "select * from student where firstName like '"+keyword+"%'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				sql = "select * from student where rollnum="+rs.getInt(0);
				Statement stmt2 = con.createStatement();
				ResultSet rs2 = stmt.executeQuery(sql);
				Student student = new Student();
				student.setRollnum(rs.getInt(0)); 
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return students;
	}
	
	public  String addStudent(Student student) {
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
				addStudent(student);
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
					String type = student.getAddrType(true);
					Address address = student.getAddress(type);
					if(address == null) {
						type = student.getAddrType(true);
						address = student.getAddress(type);
					}
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

	public void close() throws SQLException {
		con.close();
		
	}
}
