package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.DbConnect;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		//l.importData("todolist.txt"); // 파일에 있는 내용을 데이터베이스로 옮기는 메소드  
		//TodoUtil.checkDays(l);
		boolean isList = false;
		boolean quit = false;
		
		//TodoUtil.loadList(l, "todolist.txt"); 파일에 저장 하는 메소드 
		
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.nextLine();
			//StringTokenizer st= new StringTokenizer(choice);
			String[] choices = choice.split(" ");
			switch (choices[0]) {

			case "help":
				Menu.displaymenu();
				break;
				
			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;
			case "find":
				String keyword = choices[1].trim();
				TodoUtil.findList(l, keyword);
				break;
				
			case "find_cate":
				String cate = choices[1].trim();
				TodoUtil.findCateList(l, cate);
				break;
				
			case "ls_name_asc":
				System.out.println("제목순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("제목순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date_asc":
				System.out.println("날짜순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "due_date", 1);
				break;

			case "ls_date_desc":
				System.out.println("날짜역순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "due_date", 0);
				break;
				
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
			
			case "comp":
				TodoUtil.completeItem(l, choices[1]);
				break;
				 
			case "ls_comp":
				TodoUtil.listAll(l, 1);
				break;
			
			case "ls_w":
				TodoUtil.checkDays(l);
				break;
				
			case "find_wd":
				TodoUtil.listWeekdays(l,choices[1]);
				break;
		
			case "del_pst":
				TodoUtil.overDelete(l);
				break;
				
			case "exit":
				quit = true;
				break;

			default:
				System.out.println();
				System.out.println("명령어를 잘못 입력하셨습니다.");
				System.out.println("위의 명령어중 하나를 입력해주세요.");
				System.out.println();
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
		
		
		TodoUtil.saveList(l, "todolist.txt");
		//System.out.println(l.getList().size() + "개의 항목을 파일에 저장했습니다.");
		DbConnect.closeConnection();
	}
}
