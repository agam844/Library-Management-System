import java.sql.*;
import java.util.Scanner;
import java.util.regex.Pattern;


public class trial {
	
	static String user = "Agam";
	static String pass = "toor";
	static String url ="jdbc:mysql://localhost:3306/library";
	static Scanner sc = new Scanner(System.in);
	
	
	
	public static void main(String ars[]) throws Exception
	{
		System.out.println("Welcome to the Digital Library");
		Choice();			
	}
	
	private static void Choice() throws Exception {
		System.out.println("Enter the choice:");
		System.out.println("1. Register New User \n2. Login to an existing user \n3. Admin login \n4. Exit");
		int ch = sc.nextInt();
		switch(ch)
		{
		case 1: Registartion();
		break;
		case 2: Login();
		break;
		case 3: Alogin();
		break;
		case 4: Exit();
		default:System.out.println("Enter a valid choice");
				Choice();
		}
		
	}


	private static void Alogin() throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, user, pass);//Establishing JDBC connection
		Statement st = con.createStatement();//creating statement
		String query = "Select * from admin";
		ResultSet rs = st.executeQuery(query);
		System.out.println("Enter the username");
		String user = sc.next();
		System.out.println("Enter the password");
		String pass = sc.next();
		int flag = 0;
		while(rs.next())
		{
			if(user.equals(rs.getString(1)))
			{
				if(pass.equals(rs.getString(2)))
				{
					flag++;
					System.out.println("Welcome Admin");
					System.out.println("What would you like to do today?");
					System.out.println("1.Add a book \n2.Return a book \n3.Delete a book \n4.Logout");
					int ch = sc.nextInt();
					switch(ch)
					{
					case 1: addb();
					break;
					case 2: Abreturn();
					break;
					case 3: Choice();
					}
				}
				else
				{
					System.out.println("Invalid password");
					Alogin();
				}
			}
			if(flag == 0)
			{
				System.out.println("Invalid username");
			}
		}
		
		}


	private static void Abreturn() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, user, pass);
		Statement st = con.createStatement();
		Statement st1 = con.createStatement();
		Statement st2 = con.createStatement();
		Statement st3 = con.createStatement();
		System.out.println("Enter the username of the student");
		String uname = sc.next();
		String query = "Select * from students where username = '" + uname + "';";
		ResultSet rs = st.executeQuery(query);
		if(rs.next())
		{
			System.out.println("Does the user " + uname + "want to return the book " + rs.getString(4) + "Dated " + rs.getString(5));
			System.out.println("Yes/No");
			String ans = sc.next();
			if(ans.toUpperCase().equals("YES"))
			{
				String query1 = "Update students set bookiss = '' where username = '" + uname + "';";
				String query2 = "Update books set issued = 'no' where bname = '" + rs.getString(4)  + "';";
				String query3 = "Update students set date = CURDATE() where username = '" + uname + "';";
				st1.executeUpdate(query2);
				st2.executeUpdate(query1);
				st3.executeUpdate(query3);
				System.out.println("Book has been returned!");
				System.out.println("We hope to see you soon!");
			}
			else if(ans.toUpperCase().equals("NO"))
			{
				System.out.println("See you soon!");
				Choice();
			}
			else
			{
				System.out.println("Enter a valid response");
				Abreturn();
			}
		}
		else
		{
			System.out.println("Select a valid book");
			Abreturn();
		}
	}

	private static void addb() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, user, pass);
		Statement st = con.createStatement();
		String query = "Select * from books";
		ResultSet rs = st.executeQuery(query);
		System.out.println("Enter the following information:");
		System.out.println("name of the book:");
		sc.nextLine();
		String bname = sc.nextLine();
		System.out.println("The serial number of the book:");
		int sno = sc.nextInt();
		System.out.println("Enter author of the book");
		sc.nextLine();
		String author = sc.nextLine();
		System.out.println("Enter the genre of the book");
		String genre = sc.nextLine();
		while(rs.next())
		{
			rs.next();
		}
		String query2 =  "insert into books values ("+sno +",'"+ bname +"','"+ author +"' , '"+ genre + "' , 'no')";
		int result = st.executeUpdate(query2);
		if(result>0)
		{
		System.out.println("Succcessfully Added");
		Choice();
		}
	}


	private static void Exit() {
		System.out.println("We hope to see you soon!");
	}


	private static void Registartion() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, user, pass);//Establishing JDBC connection
		Statement st = con.createStatement();//creating statement
		String query = "Select * from students";
		ResultSet rs = st.executeQuery(query);
		System.out.println("Enter the username:");
		String user = sc.next();
		System.out.println("Enter the password:");
		String pass = sc.next();
		System.out.println("Enter your email Id");
		String email = sc.next();
		if(user.length()<=50)
		{
			if(pass.length()<=50)
			{
				if(isValid(email))
				{
					New_user(user, pass, email);
				}
				else
				{
					System.out.println("Email is not in correct format");
				}
			}
			else
			{
				System.out.println("Password longer than 50 characters");
			}
		}
		else
		{
			System.out.println("Username long than 50 characters");
		}
		
	}


	private static void New_user(String user1, String pass1, String email) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, user, pass);//Establishing JDBC connection
		Statement st = con.createStatement();//creating statement
		String query = "Select * from students";
		ResultSet rs = st.executeQuery(query);
		
		
		while(rs.next())
		{
			if(user1.equals(rs.getString(1)))
			{
				System.out.println("Username already taken");
			}
			else
			{
				rs.next();
			}
		}
		String query2 = "insert into students values ('"+user1 +"','"+ pass1 +"','"+ email +"' , '' , CURDATE())";
		//System.out.println(query2);
		//int result = st.executeUpdate( 
	    //         "insert into students values('Ak','Ak1234','Ak123@gmail.com',' ', CURDATE())");
		int result = st.executeUpdate(query2);
		if(result>0)
		{
		System.out.println("Succcessfully registered");
		Main_menu(user1);
		}
	}


	private static void Login() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, user, pass);//Establishing JDBC connection
		Statement st = con.createStatement();//creating statement
		String query = "Select * from students";
		ResultSet rs = st.executeQuery(query);
		System.out.println("Enter the username");
		String user = sc.next();
		System.out.println("Enter the Password");
		String pass = sc.next();
		int flag = 0;
		while(rs.next())
		{
			//System.out.println("Entered value :" + user);
			//System.out.println("Rs value:" + rs.getString(1) +"\n");
			if(user.equals(rs.getString(1)))
			{
				if(pass.equals(rs.getString(2)))
				{
					Main_menu(user);
					flag = 1;
				}
				else
				{
					System.out.println("Wrong password \n");
					Login();
				}
			}
			else
			{
				rs.next();
			}
		}
		//System.out.println(flag);
		if(flag == 0)
		{
		System.out.println("Wrong username \n");
		Login();
		}
	}


	private static void Main_menu(String user1) throws Exception {
		System.out.println("\nWelcome to the Digital Library "+ user1);
		System.out.println("Select the option:");
		System.out.println("1. Issue a book \n2. Return a book\n3. Logout");
		int ch = sc.nextInt();
		switch(ch)
		{
		case 1: Viewb(user1);
		break;
		case 2: breturn();
		break;
		case 3:System.out.println("We hope to see you soon"); 
			Choice();
		break;
		default: System.out.println("Enter a valid response");
		}
		
	}
	
	 private static void breturn() throws Exception {
		
		System.out.println("Call the Librarian to return the book");
		Alogin();
	}


	private static void Viewb(String name) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, user, pass);//Establishing JDBC connection
		Statement st = con.createStatement();//creating statement
		Statement st1 = con.createStatement();
		Statement st2 = con.createStatement();
		Statement st3 = con.createStatement();
		Statement st4 = con.createStatement();
		Statement st6 = con.createStatement();
		String query = "Select * from books";
		ResultSet rs = st.executeQuery(query);
		System.out.println("Please select the books you want to see:");
		System.out.println("1.All \n2.By Genre \n3.By author");
		int ch = sc.nextInt();
		switch(ch)
		{
		case 1 :
			while(rs.next())
			{
				if(rs.getString(5).equals("no"))
				{
				System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
				}
			}
			issue(name);
		break;
		case 2 :
			System.out.println("Enter the genre you want");
			String query1 = "Select DISTINCT genre from books";
			ResultSet rs1 = st1.executeQuery(query1);
			while(rs1.next())
			{
				System.out.println(rs1.getString(1));
			}
			System.out.println("Select the genre of books you want to see");
			String gen = sc.next();
			String query2 = "select * from books where genre ='"+ gen +"'";
			ResultSet rs2 = st2.executeQuery(query2);
			while(rs2.next())
			{
				if(rs2.getString(5).equals("no"))
				{
				System.out.println(rs2.getString(1) + " " + rs2.getString(2) + " " + rs2.getString(3) + " " + rs2.getString(4));
				}
			}
			issue(name);
		break;
		case 3 : System.out.println("Enter the author you want");
		String query3 = "Select DISTINCT author from books";
		ResultSet rs3= st3.executeQuery(query3);
		while(rs3.next())
		{
			
			System.out.println(rs3.getString(1));

		}
		String aut = sc.next();
		String query4 = "select * from books where author = '"+aut+"'";
		ResultSet rs4 = st4.executeQuery(query4);
		while(rs4.next())
		{
			if(rs4.getString(5).equals("no"))
			{
			System.out.println(rs4.getString(1) + " " + rs4.getString(2) + " " + rs4.getString(3) + " " + rs4.getString(4));
			}
		}
		issue(name);
		break;
		default: System.out.println("Select a valid option");
		}
	}


	private static void issue(String name) throws Exception {
		System.out.println("Enter the Serial number of the book");
		int x = sc.nextInt();
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, user, pass);
		Statement st = con.createStatement();
		Statement st1 = con.createStatement();
		Statement st2 = con.createStatement();
		Statement st3 = con.createStatement();
		String query = "select * from books where sno ='" + x + "'";
		ResultSet rs = st.executeQuery(query);
		if(rs.next())
		{
			System.out.println("The Book youre trying to issue is " + rs.getString(2));
			System.out.println("Are you sure you want to issue this book?(Yes/No)");
			String ans = sc.next();
			if(ans.toUpperCase().equals("YES"))
			{
				String query1 = "Update students set bookiss = '" + rs.getString(2) + "' where username = '" + name + "';";
				String query2 = "Update books set issued = 'yes' where sno = " + x + ";";
				String query3 = "Update students set date = CURDATE() where username = '" + name + "';";
				st1.executeUpdate(query1);
				st2.executeUpdate(query2);
				st3.executeUpdate(query3);
				System.out.println("Issued the book!");
				System.out.println("Thankyou for using our library!");
				Main_menu(name);
			}
			else if(ans.toUpperCase().equals("NO"))
			{
				System.out.println("Select a new book");
				issue(name);
			}
			else
			{
				System.out.println("Enter a valid response");
				issue(name);
			}
		}
		else
		{
			System.out.println("Select a valid book");
			issue(name);
		}
	}

	public static boolean isValid(String email) 
	    { 
	        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
	                            "[a-zA-Z0-9_+&*-]+)*@" + 
	                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
	                            "A-Z]{2,7}$"; 
	                              
	        Pattern pat = Pattern.compile(emailRegex); 
	        if (email == null) 
	            return false; 
	        return pat.matcher(email).matches(); 
	    }
}
