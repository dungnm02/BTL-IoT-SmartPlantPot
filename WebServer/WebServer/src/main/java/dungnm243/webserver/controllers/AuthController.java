package dungnm243.webserver.controllers;

import dungnm243.webserver.models.User;
import dungnm243.webserver.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/login")
    public String submitLogin(@ModelAttribute("user") User user, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        User userFound = userService.checkLogin(user);
        if (userFound != null) {
            session.setAttribute("user", userFound);
            return "redirect:/pots";
        } else {
            redirectAttributes.addFlashAttribute("message", "Sai tên đăng nhập hoặc mật khẩu");
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        User user = new User();
        String message = "";
        if (model.getAttribute("message") != null) {
            message = (String) model.getAttribute("message");
            user = (User) model.getAttribute("user");
        }
        model.addAttribute("user", user);
        model.addAttribute("message", message);
        return "register";
    }

    @PostMapping("/register")
    public String submitRegister(Model model, @ModelAttribute("user") User user) {
        String message = userService.createUser(user);
        if (message.equals("")) {
            return "redirect:/login";
        } else {
            model.addAttribute("message", message);
            return "register";
        }
    }

    @PostMapping("/logout")
    public String submitLogout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/login";
    }
}
