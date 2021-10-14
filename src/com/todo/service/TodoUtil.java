package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	// 새로운 TodoList 를 파라미터로 받아서, title과 desc를 입력해서 title이 중복되지 않으면, 리스트에 추가해주는 static 메소드 
	public static void createItem(TodoList list) {
		
		String title, desc;
		String category, due_date;
		Scanner sc = new Scanner(System.in);
		
		
		System.out.println("\n" + "========== 새로운 할일 작성\n");
		
		System.out.println("카테고리를 입력해주세요.");
		category = sc.next().trim();
		sc.nextLine();
	
		
		System.out.println("항목을 입력해주세요.");
		
		//title = sc.next();
		title = sc.nextLine();
		
		if (list.isDuplicate(title)) {
			System.out.printf("항목이 중복됩니다!");
			return;
		}
		
		System.out.println("내용을 입력해주세요.");
		//desc = sc.next();
		desc = sc.nextLine();
		
		System.out.println("마감일을 입력해주세요.");
		due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		if(list.addItem(t)>0)
			System.out.println("추가되었습니다.");
	}
	// list 를 파라미터로 받고 user가 title 을 입력하면, list에 있는 title과 일치하면 리스트에서 삭제해주는 메소드 
	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.println("\n"
				+ "========== 할일 삭제\n"
				+ "삭제할 항목의 번호를 입력해주세요.");
		/*String title = sc.next();
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				break;
			}
		}*/
		int num = sc.nextInt();
		TodoItem it = l.getList().get(num-1);
		System.out.printf("%d. " + it + "\n", num);
		System.out.println("위 항목을 삭제하시겠습니까? (y/n) > ");
		String choice = sc.next();
		/*
		 * sc.nextLine()으로 했을때는 왜 제대로 작동이 안하는 걸까??
		 */
		if(choice.equals("y")) {
			if((l.deleteItem(it.getIndex())>0))
				System.out.println("삭제되었습니다!");
		}
	}

	/* list를 파라미터로 받아서, 바꾸고싶은 title을 user에게 입력받고, title이 리스트에 존재는지 않는지 체크 , 새로운 title을 입력받아, 리스트에 중복되는지 체크
	중복이 모두 안되면 리스트에 추가  */ 
	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== 할일 수정\n"
				+ "수정하고 싶은 항목의 번호를 입력해주세요.");
		/*String title = sc.nextLine().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("그런 항목은 존재하지 않습니다.");
			return;
		}*/
		int id = sc.nextInt();
		TodoItem it = l.getList().get(id-1);
		System.out.printf("%d. " + it + "\n", id);
		
		sc.nextLine();
		
		System.out.println("새로운 항목을 입력해주세요.");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("이미 동일한 항목이 존재합니다.");
			return;
		}
		System.out.println("새로운 카테고리를 입력해주세요.");
		String new_category = sc.nextLine().trim();
		
		System.out.println("새로운 내용을 입력해주세요. ");
		String new_description = sc.nextLine().trim();
		
		System.out.println("새로운 마감일을 입력해주세요. ");
		String new_due_date = sc.nextLine().trim();
		
		/*for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("할일이 수정되었습니다.");
			}
		}*/
		//l.deleteItem(it.getIndex());
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date);
		t.setIndex(id);
		if(l.updateItem(t) > 0)
			System.out.println("수정되었습니다.");
	}
	// 리스트에서 TodoItem의 title과 desc를 하나씩 출력 
	public static void listAll(TodoList l) {
		/*if(l.isEmpty()) {
			System.out.println("저장된 내용이 없습니다.");
		}*/
		System.out.printf("[전체 목록, 총 %d개]\n", l.getList().size());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			Writer fw = new FileWriter(filename);
			for(TodoItem i : l.getList()) {
				fw.write(i.toSaveString());
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			String line;
			int count = 0;
			BufferedReader br = new BufferedReader(new FileReader(filename));
			while((line = br.readLine()) != null) {
				TodoItem it = new TodoItem(line);
				it.setIndex(count+1);
				l.addItem(it);
				count++;
			}
			br.close();
			System.out.println(count +"개의 항목을 읽었습니다.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("파일이 존재하지 않습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void findKeyTitle(TodoList l, String strtofind) {
		int count = 0;
		for(TodoItem item : l.getList()) {
			if(item.getTitle().contains(strtofind)) {
				System.out.printf(item.getIndex() +". [" + item.getCategory() + "]" + item.getTitle() + " - " + item.getDesc() +" - " + item.getDue_date() +  " - " + item.getCurrent_date() + "\n");
				count++;
			}
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
	}
	
	public static void findCateList(TodoList l, String cate) {
		int count = 0;
		for(TodoItem item : l.getListCategory(cate)) {
				System.out.println(item.toString());
				count++;
			}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
	}
	
	
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		for(String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);
	}
		
	
	
	public static void findList(TodoList l, String keyword) {
		int count = 0;
		for(TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		/*if(l.isEmpty()) {
			System.out.println("저장된 내용이 없습니다.");
		}*/
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	public static void completeItem(TodoList l, String number) {
		int num = Integer.valueOf(number);
		l.completeItem(num);
		System.out.println("완료 체크 하였습니다.");
	}
	public static void listAll(TodoList l, int num) {
		int count = 0;
		for(TodoItem item : l.getList(1)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목이 완료되었습니다.\n", count);
	}	
}
