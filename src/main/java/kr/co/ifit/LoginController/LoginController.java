package kr.co.ifit.LoginController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LoginController {
    @GetMapping("/login")
    public String loginForm() {
        return "loginForm";
    }


    @PostMapping("/login")
    public String login(String id, String pwd, String toURL, boolean rememberid,
                        HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        // 1. id와 pw를 확인
        // 2-1 일치하지 않으면
        if(loginCheck(id, pwd)) {
            String msg = URLEncoder.encode("id 또는 pwd가 일치하지 않습니다.", "utf-8");
            return "redirect:/login/login?msg="+msg;
        }

        if(rememberid) {
            Cookie cookie = new Cookie("id", id);
            response.addCookie(cookie);
        } else {
            Cookie cookie = new Cookie("id", id);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

        HttpSession session = request.getSession();
        session.setAttribute("id", id);


        toURL = toURL==null || toURL.equals("") ? "/":toURL;

        return "redirect:" + toURL;
    }


    private boolean loginCheck(String id, String pwd) {

        return "kosta".equals(id)&& "1234".equals(pwd);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }

}