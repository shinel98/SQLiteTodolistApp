package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.DbConnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByDateDesc;
import com.todo.service.TodoSortByName;




public class TodoList {
	Connection conn;
	private List<TodoItem> list;
	
	
	public TodoList() {
		this.list = new ArrayList<TodoItem>();
		this.conn = DbConnect.getConnection(); 
	}

	public int addItem(TodoItem t) {
		list.add(t); 
		String sql = "insert into list (title, memo, category, current_date, due_date, weekdays)" + 
				"values (?,?,?,?,?,?);";
		int count = 0;
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, t.getTitle());
			pstm.setString(2, t.getDesc());
			pstm.setString(3, t.getCategory());
			pstm.setString(4, t.getCurrent_date());
			pstm.setString(5, t.getDue_date());
			pstm.setString(6, t.getWeekdays());
			count = pstm.executeUpdate();
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index) {
		//list.remove(t);
		String sql = "delete from list where id=?;";
		PreparedStatement pstm;
		int count = 0;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, index);
			count = pstm.executeUpdate();
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public int updateItem(TodoItem t) {
		
		PreparedStatement  pstm;
		int count = 0;
		try {
			String sql = "update list set title = ?, memo = ?, category = ?, current_date = ?, due_date = ?, weekdays = ?"
					+ " where id = ?;";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, t.getTitle());
			pstm.setString(2, t.getDesc());
			pstm.setString(3, t.getCategory());
			pstm.setString(4, t.getCurrent_date());
			pstm.setString(5, t.getDue_date());
			pstm.setString(6, t.getWeekdays());
			pstm.setInt(7, t.getIndex());
			count = pstm.executeUpdate();
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
		
	}

	/*
	public ArrayList<TodoItem> getList() {
		return new ArrayList<TodoItem>(list);
	}
*/
	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select * from list;";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String category = rs.getString("category");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				String weekdays = rs.getString("weekdays");
				boolean is_completed = rs.getBoolean("is_completed");
				
				TodoItem item = new TodoItem(title, memo, category, due_date, is_completed);
				item.setIndex(id);
				item.setCurrent_date(current_date);
				item.setWeekdays(weekdays);
				list.add(item);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String keyword){
		ArrayList<TodoItem> list = new ArrayList<>();
		PreparedStatement pstm;
		keyword = "%" + keyword+ "%";
		
		try {
			String sql = "select * from list where title like ? or memo like ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, keyword);
			pstm.setString(2, keyword);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String category = rs.getString("category");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				TodoItem item = new TodoItem(title, memo, category, due_date);
				item.setIndex(id);
				item.setCurrent_date(current_date);
				list.add(item);
			}
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;		
	}
	
	public int getCount() {
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			String sql = "select count(id) from list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<String> getCategories(){
		ArrayList<String> list = new ArrayList<>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select distinct category from list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(rs.getString("category"));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<TodoItem> getListCategory(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<>();
		PreparedStatement pstm;
		String sql = "select * from list where category = ?";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, keyword);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String category = rs.getString("category");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				boolean is_completed = rs.getBoolean("is_completed");
				String weekdays = rs.getString("weekdays");
				TodoItem item = new TodoItem(title, memo, category, due_date);
				item.setIndex(id);
				item.setCurrent_date(current_date);
				item.setIs_completed(is_completed);
				item.setWeekdays(weekdays);
				list.add(item);			
			}
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {
		System.out.println("\n"
				+ "========목록========\n");
		for (TodoItem myitem : list) {
			System.out.println(myitem.getTitle() + " " + myitem.getDesc() + " " + myitem.getCurrent_date());
		}
	}
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}
	
	public void sortByDateDesc() {
		Collections.sort(list, new TodoSortByDateDesc());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	/*public Boolean isDuplicate(String title) {
		PreparedStatement pstm;
		try {
			String sql = "select count(title) from list where title = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, title);
			ResultSet rs = pstm.executeQuery();
			if(rs.next())	
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	*/
	public Boolean isDuplicate(String title) {
		for(TodoItem i : list) {
			if(title.equals(i.getTitle())) 
				return true;
		}
		return false;
	}
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String sql = "insert into list (title, memo, category, current_date, due_date)" + 
					"values (?,?,?,?,?);";
			int records = 0;
			String line;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String memo = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				PreparedStatement pstm = conn.prepareStatement(sql);
				pstm.setString(1, title);
				pstm.setString(2, memo);
				pstm.setString(3, category);
				pstm.setString(4, current_date);
				pstm.setString(5, due_date);
				int count = pstm.executeUpdate();
				if(count > 0) records++;
				pstm.close();
			}
			System.out.println(records + " records read!!");
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select * from list order by " + orderby;
			if(ordering == 0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String category = rs.getString("category");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				TodoItem item = new TodoItem(title, memo, category, due_date);
				item.setIndex(id);
				item.setCurrent_date(current_date);
				list.add(item);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public void completeItem(int number) {
		PreparedStatement pstm;
		String sql = "update list set is_completed = 1 where id = ?";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, number);
			pstm.executeUpdate();
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArrayList<TodoItem> getList(int num){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstm;
		try {
			String sql = "select * from list where is_completed = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, num);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String category = rs.getString("category");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				boolean is_completed = rs.getBoolean("is_completed");
				TodoItem item = new TodoItem(title, memo, category, due_date, is_completed);
				item.setIndex(id);
				item.setCurrent_date(current_date);
				list.add(item);
			}
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public void checkDays(int id) {
		PreparedStatement pstm;
		try {
			String sql = "update list set next_seven_days = 1 where id = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			pstm.executeUpdate();
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArrayList<TodoItem> getWeekList(int num){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstm;
		try {
			String sql = "select * from list where next_seven_days = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, num);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String category = rs.getString("category");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				boolean is_completed = rs.getBoolean("is_completed");
				String weekdays = rs.getString("weekdays");
				TodoItem item = new TodoItem(title, memo, category, due_date, is_completed);
				item.setIndex(id);
				item.setCurrent_date(current_date);
				item.setWeekdays(weekdays);
				list.add(item);
			}
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<TodoItem> getWeekdays(String weekdays){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstm;
		try {
			String sql = "select * from list where weekdays = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, weekdays);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String category = rs.getString("category");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				boolean is_completed = rs.getBoolean("is_completed");
				TodoItem item = new TodoItem(title, memo, category, due_date, is_completed);
				item.setIndex(id);
				item.setCurrent_date(current_date);
				item.setWeekdays(weekdays);
				list.add(item);
			}
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/*
	
	public Boolean isEmpty(){
		if(list.isEmpty()) return true;
		else return false;
	}
	*/
}
