package spring.mvc.test.space;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaceController {
	
	@RequestMapping("space")
	public String space(HttpServletRequest req, Model model) {
		System.out.println("space()");
		return "space/space";
	}
}
