package com.guoxiaohei.markdown.api;

import com.guoxiaohei.markdown.model.common.ResponseResult;
import com.guoxiaohei.markdown.model.projo.Article;
import com.guoxiaohei.markdown.service.ArticleService;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class ArticleApi {

  private ArticleService articleService;

  public ArticleApi(@Autowired ArticleService articleService) {
    this.articleService = articleService;
  }

  @PostMapping(value = "article", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseResult insertArticle(@RequestBody Article article) {
    return ResponseResult.builder().code(HttpStatus.OK.value()).message(
        articleService.insertArticle(article)).build();
  }

  @PostMapping(value = "article/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseResult updateArticle(@PathVariable("id") String id, @RequestBody Article article) {
    if (!StringUtils.equals(id, article.getId())) {
      return ResponseResult.builder().code(HttpStatus.BAD_REQUEST.value()).message("valid params")
          .build();
    }
    return ResponseResult.builder().code(HttpStatus.OK.value())
        .message(articleService.updateArticle(article)).build();
  }

  @GetMapping(value = "article", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseResult page(@RequestParam("categoryId") String categoryId,
      @RequestParam(value = "search") String search,
      @RequestParam("page") int page,
      @RequestParam("pageSize") int pageSize) {
    String decode = URLDecoder.decode(search, StandardCharsets.UTF_8);
    return ResponseResult.builder().code(HttpStatus.OK.value()).message("success")
        .data(articleService.pageArticle(categoryId, decode, page, pageSize)).build();
  }

  @GetMapping(value = "article/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseResult findById(@PathVariable("id") String id) {
    return ResponseResult.builder().code(HttpStatus.OK.value()).message("success")
        .data(articleService.findById(id)).build();
  }

  @GetMapping(value = "articleDel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseResult deleteById(@PathVariable("id") String id) {
    return ResponseResult.builder().code(HttpStatus.OK.value()).message("success")
        .data(articleService.deleteById(id)).build();
  }

}
