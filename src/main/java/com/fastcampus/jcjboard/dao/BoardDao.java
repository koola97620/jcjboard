package com.fastcampus.jcjboard.dao;

import com.fastcampus.jcjboard.servlet.BoardDO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BoardDao {
    private String dbUrl="jdbc:mysql://localhost:3306/jcjboard?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
    private String dbId = "root";
    private String dbPassword = "1234";

    public List<BoardDO> getBoardList() {
        List<BoardDO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = DbUtil.connect(dbUrl,dbId,dbPassword);
            String sql ="select boardid,nickname,title,content from board";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()) {
                BoardDO board = new BoardDO();
                board.setId(rs.getInt(1));
                board.setNickname(rs.getString(2));
                board.setTitle(rs.getString(3));
                board.setContent(rs.getString(4));
                list.add(board);
                System.out.println(board.getId() + board.getContent());
            }


        }catch(Exception ex) {
            ex.printStackTrace();
        }finally {
            DbUtil.close(conn,ps,rs);
        }

        return list;
    }






}
