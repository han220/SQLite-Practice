import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SQLPractice {
	Connection con = null;
	Scanner s = new Scanner(System.in);
	final String dbFile = "myfirst.db";
	final String tableName = "g_artists";

	public static void main(String[] args) {
		new SQLPractice().practice();
	}

	void practice() {
		try {
			// SQLite JDBC 체크
			Class.forName("org.sqlite.JDBC");

			// SQLite 데이터베이스 파일에 연결

			con = DriverManager.getConnection("jdbc:sqlite:" + dbFile);

			// 데이터 추가 (C)
			createData();

			// 데이터 조회 (R)
			readAllData();

			// 데이터 업데이트 (U)
			updateData();
			
			// 데이터 삭제 (D)
			deleteData();
			
			readAllData();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
	}

	void readAllData() throws SQLException {
		System.out.println("\n*** 데이터 조회 ***");
		Statement stat1 = con.createStatement();
		String sql1 = "SELECT * FROM " + tableName;
		ResultSet rs1 = stat1.executeQuery(sql1);

		// 데이터 출력
		while (rs1.next()) {
			String id = rs1.getString("ArtistId");
			String name = rs1.getString("Name");
			System.out.println(id + " " + name);
		}
		stat1.close();
	}

	void createData() throws SQLException {
		System.out.println("\n*** 데이터 추가 ***");

		System.out.print("추가할 가수 입력: ");
		String artist = s.nextLine().trim();

		Statement stat1 = con.createStatement();
		String sql1 = "INSERT INTO " + tableName + "(Name) VALUES (\"" + artist + "\")";
		int cnt = stat1.executeUpdate(sql1);

		// 데이터 출력
		if (cnt > 0)
			System.out.println("데이터 추가 완료.");
		else
			System.out.print("[오류] 데이터 추가 오류!");
		stat1.close();
	}
	
	void updateData() throws SQLException {
		System.out.println("\n*** 데이터 수정 ***");

		System.out.print("업데이트할 번호: ");
		int no = s.nextInt();
		s.nextLine();
		
		System.out.print("추가할 가수 입력: ");
		String artist = s.nextLine().trim();

		Statement stat1 = con.createStatement();
		String sql1 = "UPDATE " + tableName + " SET NAME=\"" + artist + "\" WHERE ArtistID=" + no;
		int cnt = stat1.executeUpdate(sql1);

		// 데이터 출력
		if (cnt > 0)
			System.out.println("데이터 수정 완료.");
		else
			System.out.print("[오류] 데이터 수정 오류!");
		stat1.close();
	}
	
	void deleteData() throws SQLException {
		System.out.println("\n*** 데이터 삭제 ***");

		System.out.print("삭제할 번호: ");
		int no = s.nextInt();
		s.nextLine();

		Statement stat1 = con.createStatement();
		String sql1 = "DELETE from " + tableName + " WHERE ArtistID="+no;
		int cnt = stat1.executeUpdate(sql1);

		// 데이터 출력
		if (cnt > 0)
			System.out.println("데이터 삭제 완료.");
		else
			System.out.print("[오류] 데이터 삭제 오류!");
		stat1.close();
	}
}
