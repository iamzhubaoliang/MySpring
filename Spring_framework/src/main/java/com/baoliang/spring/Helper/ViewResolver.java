package com.baoliang.spring.Helper;

import com.alibaba.fastjson.JSON;
import com.baoliang.spring.Data.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 年: 2021 月: 09日: 12小时: 19分钟: 15
 * 用户名: liangliang
 **/
public class ViewResolver {
    public static  void  handleViewResult(View view, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = view.getPath();
        if(view.getModel().size()>0)
        {
            handleDataResult(view, response);
        }else {

            if (path != null) {
                if (path.startsWith("/")) { //重定向
                    response.sendRedirect(request.getContextPath() + path);
                } else { //请求转发
                    Map<String, Object> model = view.getModel();
                    for (Map.Entry<String, Object> entry : model.entrySet()) {
                        request.setAttribute(entry.getKey(), entry.getValue());
                    }
                    ResourceBundle bundle = ResourceBundle.getBundle("config");
                    String configPath = bundle.getString("webPath");
                    if (configPath == null)
                        request.getRequestDispatcher(configPath + path).forward(request, response);
                    else
                        request.getRequestDispatcher("/WEB-INF/view/" + path).forward(request, response);
                }
            }
        }
    }
    public static void handleDataResult(View data, HttpServletResponse response) throws IOException {
        Object model = data.getModel();
        if (model != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String json = JSON.toJSON(model).toString();
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }
    public static void handleDataResult(String data, HttpServletResponse response) throws IOException {
        if (data != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(data);
            writer.flush();
            writer.close();
        }
    }
}
