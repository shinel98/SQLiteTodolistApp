package com.todo.menu;
public class Menu {
	// 선택지를 출력해주는 static 메소드 
    public static void displaymenu()
    {
        System.out.println();
        System.out.println("1. 새로운 할일 추가 ( add )");
        System.out.println("2. 할일 삭제 ( del )");
        System.out.println("3. 할일 수정 ( edit )");
        System.out.println("4. 할일 목록 보기 ( ls )");
        System.out.println("5. 이름에 따라 오름차순 정렬 ( ls_name_asc )");
        System.out.println("6. 이름에 따라 내림차순 정렬 ( ls_name_desc )");
        System.out.println("7. 날짜에 따라 오름차순 정렬 ( ls_date_asc )");
        System.out.println("8. 날짜에 따라 내림차순 정렬 ( ls_date_desc )");
        System.out.println("9. 키워드를 포함한 항목 찾기 ( find <키워드> )");
        System.out.println("10. 키워드를 포함한 카테고리 찾기 ( find_cate <키워드> )");
        System.out.println("11. 등록되어 있는 카테고리 출력 ( ls_cate )");
        System.out.println("12. 항목 완료 ( comp <번호> )");
        System.out.println("13. 완료된 항목 출력 ( ls_comp )");
        System.out.println("14. 종료 ( exit )");
        //System.out.println("Enter your choice >");
        
    }
    public static void prompt()
    {	
    	System.out.println();
    	System.out.println("옵션을 선택하세요. (커맨드 보기 : help)");
    	System.out.print(">");
    }
}
