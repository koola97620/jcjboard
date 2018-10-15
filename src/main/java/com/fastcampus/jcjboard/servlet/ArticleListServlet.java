package com.fastcampus.jcjboard.servlet;

import com.fastcampus.jcjboard.dao.BoardDao;
import com.fastcampus.jcjboard.paging.Paging;
import com.fastcampus.jcjboard.paging.PerPage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/board/list")
public class ArticleListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //int id = Integer.parseInt(req.getParameter("page"));


        // 그냥 id 값 초기화 해준거.
        int id=1;

        // 최초에 index.html 에서 넘어오면 page 값이 null 이다
        // 이후에 paging 부분의 [2].[3]을 누르면 그 인덱스가 id 로 저장이 되게 만들었어!
        if(req.getParameter("page") != null) {
            id = Integer.parseInt(req.getParameter("page"));
        }

        BoardDao dao = new BoardDao();

        // SQL limit 써서. 한 페이지당 게시글 출력 개수 조정하기!!!!!!!!!!
        PerPage perPage = new PerPage(); // 기본값 1, 10
        // 1 은 어차피 기본값이다.
        if(id > 1) {
            perPage.setPage(id);
        }

        Paging paging = new Paging();
        paging.setPerPage(perPage);
        paging.setTotalCount(dao.getArticleListTotalCount());


        // getBoardListPerPage() 안에 불러와야할 게시글의 수 지정해주기. (시작지점, 갯수)
        // Paging 클래스 안의 PerPage 클래스 이용하면 시작지점, 갯수 다 구할 수 있다.
        List<ArticleVO> articleVOList = dao.getArticleListPerPage(paging);

        // 게시판 총 글 수.
        //int totalCount = list.size();

        /*add by siyoon - 해당글의 댓글 숫자를 출력하기 위해서 추가함*/
        for (ArticleVO bdo : articleVOList) {
            int commentCount = dao.getCommentCount(bdo.getId());
            bdo.setCommentCount(commentCount);
        }

        req.setAttribute("articleVOList" , articleVOList);
        req.setAttribute("boardSize" , paging.getTotalCount());
        req.setAttribute("paging" , paging);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/articleList.jsp");
        dispatcher.forward(req,resp);
    }
}