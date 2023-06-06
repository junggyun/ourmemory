package myproject.ourmemory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeContorller {

    @GetMapping("/")
    public String home() {
        return "메인 페이지입니다.";
    }
}
