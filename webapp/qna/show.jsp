<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<!DOCTYPE html>
<html lang="kr">
<head>
	<%@ include file="/header.jspf" %>
</head>
<body>

<%@ include file="/navigation.jspf" %>

<div class="container" id="main">
    <div class="col-md-12 col-sm-12 col-lg-12">
        <div class="panel panel-default">
          <header class="qna-header">
              <h2 class="qna-title">${qna.title}</h2>
          </header>
          <div class="content-main">
              <article class="article">
                  <div class="article-header">
                      <div class="article-header-thumb">
                          <img src="https://graph.facebook.com/v2.3/100000059371774/picture" class="article-author-thumb" alt="">
                      </div>
                      <div class="article-header-text">
                          <a href="/users/92/kimmunsu" class="article-author-name">${qna.writer}</a>
                          <a href="/questions/413" class="article-header-time" title="퍼머링크">
                              ${qna.createdDate}<i class="icon-link"></i>
                          </a>
                      </div>
                  </div>
                  <div class="article-doc">
                      <p>${qna.contents}</p>
                  </div>
                  <div class="article-util">
                      <ul class="article-util-list">
                          <li>
                              <a class="link-modify-article" href="/questions/423/form">수정</a>
                          </li>
                          <li>
                              <form class="form-delete" action="/questions/${qna.questionId}" method="POST">
                                  <input type="hidden" name="_method" value="DELETE">
                                  <button class="link-delete-article" type="submit">삭제</button>
                              </form>
                          </li>
                          <li>
                              <a class="link-modify-article" href="/index.html">목록</a>
                          </li>
                      </ul>
                  </div>
              </article>

              <div class="qna-comment">
                  <div class="qna-comment-slipp">
                      <p class="qna-comment-count"><strong>${fn:length(answers)}</strong>개의 의견</p>
                      <div class="qna-comment-slipp-articles">
						<c:forEach items="${answers}" var="answer">
                          <article class="article" id="answer-1405">
                              <div class="article-header">
                                  <div class="article-header-thumb">
                                      <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                                  </div>
                                  <div class="article-header-text">
                                      <a href="#" class="article-author-name">${answer.writer}</a>
                                      <a href="#answer-1434" class="article-header-time" title="퍼머링크">${answer.createdDate}</a>
                                  </div>
                              </div>
                              <div class="article-doc comment-doc">
                                  <p>${answer.contents}</p>
                              </div>
                              <div class="article-util">
                                  <ul class="article-util-list">
                                      <li>
                                          <a class="link-modify-article" href="#">수정</a>
                                      </li>
                                      <li>
                                          <button type="submit" class="link-delete-article anw-del-btn" value="${answer.answerId}">삭제</button>
                                      </li>
                                  </ul>
                              </div>
                          </article>
      					</c:forEach>
      					<div class="answerWrite">
                          <form class="submit-write" id="anw-form">
                          	  <input type="hidden" name="questionId" value="${qna.questionId}">
							  <div class="form-group col-lg-4" style="padding-top:10px;">
							    <input class="form-control" id="writer" name="writer" placeholder="이름">
							  </div>
							  <div class="form-group col-lg-12">
								<textarea name="contents" id="contents" class="form-control" placeholder=""></textarea>
							  </div>
                              <button class="btn btn-success pull-right" id="anw-reg-btn" type="button">Post</button>
                              <div class="clearfix" /></div>
                          </form>
                        </div>
                      </div>
                  </div>
              </div>
          </div>
        </div>
    </div>
</div>
<script type="text/template" id="answerTemplate">
	<article class="article">
		<div class="article-header">
			<div class="article-header-thumb">
				<img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
			</div>
			<div class="article-header-text">
				{0}
				<div class="article-header-time">{1}</div>
			</div>
		</div>
		<div class="article-doc comment-doc">
			{2}
		</div>
		<div class="article-util">
			<ul class="article-util-list">
				<li>
					<a class="link-modify-article" href="/api/qna/updateAnswer/{3}">수정</a>
				</li>
				<li>
					<button type="submit" class="link-delete-article anw-del-btn" value="{4}">삭제</button>
				</li>
			</ul>
		</div>
	</article>
</script>

<%@ include file="/footer.jspf" %>

<script>
	$("#anw-reg-btn").on("click", function() {
		$.ajax({
			type : "post",
			url : "/answer/register",
			data : $("#anw-form").serialize(),
			dataType : "json",
			success : function(result) {
				var answer = result.answer;
				var answerTemplate = $("#answerTemplate").html();
				var template = answerTemplate.format(answer.writer, new Date(answer.createdDate), answer.contents, answer.answerId);
				
				$(".qna-comment-slipp-articles").prepend(template);
			},
			error : function() {
				alert("댓글 삽입에 실패했어요.");
			}
		});
	});
	
	$(".anw-del-btn").on("click", function() {
		var questionId = "${qna.questionId}";
		var answerId = {
			"answerId" : this.value
		};
		
		$.ajax({
			type : "post",
			url : "/answer/delete",
			data : answerId,
			dataType : "json",
			success : function(isDeleted) {
				if(isDeleted.result === "SUCCESS") {
					alert("댓글 삭제 성공");

					location.href = "/qna/show?questionId=" + questionId;
					return;
				}
				
				alert("댓글 삭제 실패");
			},
			error : function() {
				alert("댓글 삭제에 문제가있어요.");
			}
		});
	})
</script>

</body>
</html>