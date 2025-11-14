package com.jhssong.univletter.domain.article.service;

import com.jhssong.univletter.domain.article.dto.ArticleReqDTO;
import com.jhssong.univletter.domain.article.exception.ArticleExceptionUtils;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleCrawler {

    private final ArticleService articleService;

    public void crawl(int timeWindow) {
        log.info("크롤링 작업 시작");
        crawlKNUCSE(timeWindow);
        log.info("크롤링 작업 종료");
    }

    private void crawlKNUCSE(int timeWindow) {
        String boardName = "경북대학교 컴퓨터학부";
        String baseURL = "https://cse.knu.ac.kr/bbs/board.php?bo_table=sub5_1&page=";

        boolean active = true;
        int pageCnt = 1;

        while (active) {
            String url = baseURL + pageCnt;
            pageCnt++;

            try {
                Document doc = Jsoup.connect(url).get();
                Elements rows = doc.select("tbody tr");

                if (rows.isEmpty()) {
                    break;
                }

                for (Element row : rows) {
                    // 게시판
                    String subName = Optional.ofNullable(row.selectFirst("td.td_subject a.bo_cate_link"))
                            .map(Element::text).orElse(null);

                    // 제목 및 링크
                    Element titleLink = row.selectFirst("div.bo_tit a");
                    String title = titleLink != null ? titleLink.text() : null;
                    String link = titleLink != null ? titleLink.attr("href") : null;

                    // 작성자
                    String author = Optional.ofNullable(row.selectFirst("td.td_name span.sv_member"))
                            .map(Element::text).orElse(null);

                    // 조회수
                    Integer views = Optional.ofNullable(row.selectFirst("td.td_num"))
                            .map(e -> Integer.parseInt(e.text().trim()))
                            .orElse(0);

                    // 작성일
                    String dateString = Optional.ofNullable(row.selectFirst("td.td_datetime"))
                            .map(Element::text).orElse(null);

                    LocalDate writtenAt = null;
                    if (dateString != null) {
                        writtenAt = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }

                    // 하루 전보다 오래된 글이면 종료
                    LocalDate oneDayAgo = LocalDate.now().minusDays(timeWindow);
                    if (writtenAt != null && writtenAt.isBefore(oneDayAgo)) {
                        active = false;
                        break;
                    }

                    // 최신 글이면 ArticleReqDTO 객체 생성
                    ArticleReqDTO articleReqDTO = ArticleReqDTO.builder()
                            .title(title)
                            .link(link)
                            .views(views)
                            .author(author)
                            .writtenAt(writtenAt)
                            .boardName(boardName)
                            .boardSubName(subName)
                            .build();

                    log.info("분야: {}, 제목: {}, 작성일: {}", subName, title, writtenAt);

                    articleService.addNotice(articleReqDTO);
                }

            } catch (IOException e) {
                throw ArticleExceptionUtils.CrawlingError(url, e.getMessage());
            }
        }

    }
}
