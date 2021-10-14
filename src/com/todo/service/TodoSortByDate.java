package com.todo.service;
import java.util.Comparator;

import com.todo.dao.TodoItem;
// TodoList를 date에 따라 오름차순으로 정렬해주는 메소드 
public class TodoSortByDate implements Comparator<TodoItem> {
    @Override
    public int compare(TodoItem o1, TodoItem o2) {
        return o1.getCurrent_date().compareTo(o2.getCurrent_date());

    }
}

/*class TodoSortByDateDesc implements Comparator<TodoItem> {

	@Override
	public int compare(TodoItem o1, TodoItem o2) {
		// TODO Auto-generated method stub
		return o2.getCurrent_date().compareTo(o1.getCurrent_date());
	}
	
}

왜 위와 같이 했을땐 TodoList에서 객체생성이 안될까..? 
*/
