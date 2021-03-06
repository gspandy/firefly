package test.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.firefly.annotation.Controller;
import com.firefly.annotation.RequestMapping;
import com.firefly.mvc.web.HttpMethod;
import com.firefly.mvc.web.View;

@Controller
public class IndexController {

	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("into /index");
		HttpSession session = request.getSession();
		request.setAttribute("hello", session.getAttribute("name"));
		response.addCookie(new Cookie("test", "cookie_value"));
		Cookie cookie = new Cookie("myname", "xiaoqiu");
		cookie.setMaxAge(5 * 60);
		response.addCookie(cookie);
		return "/index.html";
	}
	
	@RequestMapping(value = "/add", method = HttpMethod.POST, view = View.TEXT)
	public String add(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("into /add");
		return request.getParameter("content");
	}
	
	@RequestMapping(value = "/add2", method = HttpMethod.POST, view = View.TEXT)
	public String add2(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("into /add2");
		return "test add 2";
	}

	@RequestMapping(value = "/login")
	public String test(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(15);
		String name = (String)session.getAttribute("name");
		if(name == null) {
			System.out.println("name is null");
			name = "Qiu Pengtao";
			session.setAttribute("name", name);
		}
		request.setAttribute("name", name);
		return "/test.html";
	}
	
	@RequestMapping(value = "/exit")
	public String exit(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		request.setAttribute("name", "exit");
		return "/test.html";
	}

	@RequestMapping(value = "/index2")
	public String index2(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.sendRedirect("index");
		return null;
	}

	@RequestMapping(value = "/index3")
	public String index3(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.sendRedirect(request.getContextPath()
				+ request.getServletPath() + "/index");
		return null;
	}
	
	@RequestMapping(value = "/testc")
	public String testOutContentLength(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String msg = "<html><body>test Content-Length output</body></html>";
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		response.setHeader("Content-Length", String.valueOf(msg.getBytes("UTF-8").length));
		PrintWriter writer = response.getWriter();
		try {
			writer.print(msg);
		} finally {
			writer.close();
		}
		return null;
	}

	@RequestMapping(value = "/index4", view = View.REDIRECT)
	public String index4(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return "/index";
	}

}
