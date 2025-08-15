package com.jhssong.univletter.global.admin;

import com.jhssong.univletter.domain.article.dto.ArticleResDTO;
import com.jhssong.univletter.domain.article.service.ArticleService;
import com.jhssong.univletter.domain.board.dto.BoardResDTO;
import com.jhssong.univletter.domain.board.service.BoardService;
import com.jhssong.univletter.domain.subscribe.dto.SubscribeResDTO;
import com.jhssong.univletter.domain.subscribe.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final ArticleService articleService;
    private final BoardService boardService;
    private final SubscribeService subscribeService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin/dashboard")
    public String showDashboard(Model model,
                                @RequestParam(defaultValue = "articles") String tab,
                                @RequestParam(defaultValue = "0") int page) {
        // Setup paging
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize);

        // Article Data
        Page<ArticleResDTO> articlesPageData = articleService.getPageableArticles(pageable);
        model.addAttribute("articles", articlesPageData.getContent());
        model.addAttribute("articlesPage", articlesPageData.getNumber());
        model.addAttribute("articlesTotalPages", articlesPageData.getTotalPages());

        // Subscriber Data
        Page<SubscribeResDTO> subscribersPageData = subscribeService.getPageableSubscribers(pageable);
        model.addAttribute("subscribers", subscribersPageData.getContent());
        model.addAttribute("subscribersPage", subscribersPageData.getNumber());
        model.addAttribute("subscribersTotalPages", subscribersPageData.getTotalPages());

        // Board Data
        Page<BoardResDTO> boardsPageData = boardService.getPageableBoards(pageable);
        model.addAttribute("boards", boardsPageData.getContent());
        model.addAttribute("boardsPage", boardsPageData.getNumber());
        model.addAttribute("boardsTotalPages", boardsPageData.getTotalPages());

        model.addAttribute("currentTab", tab);

        return "dashboard";
    }

}

