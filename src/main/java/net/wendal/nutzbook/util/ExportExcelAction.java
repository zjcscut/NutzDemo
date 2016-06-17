package net.wendal.nutzbook.util;

import net.wendal.nutzbook.bean.ProUser;
import org.nutz.mvc.Mvcs;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author zhangjinci
 * @version 2016/6/17 10:11
 */
public abstract class ExportExcelAction {


    public void daExportExcel(List<ProUser> users,HttpServletResponse response) {
//        HttpServletResponse response = Mvcs.getResp();
        try{
            String fileName=new String(("用户信息表").getBytes("UTF-8"), "iso8859-1")+ ".xlsx";
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setCharacterEncoding("utf-8");
            setNoCache(response);
//            JspWriter jspWriter =
//            response.flushBuffer();
            String[] titles = {"用户id","用户姓名","手机号码","Email","是否有效","创建日期"};
            ServletOutputStream outputStream = response.getOutputStream();
            ExportUtil.ExportExcel(titles, users, outputStream);
//            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setNoCache(HttpServletResponse response) {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
    }
}
