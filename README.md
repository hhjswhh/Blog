# Blog

这是我的个人博客，[网址](http://www.acodesigner.tech)

前端使用Materialize，后端使用JSP+Servlet，使用Maven作为项目管理工具，版本控制使用Git，开发工具使用Intellij IDEA，数据库使用MySQL,文本编辑模块使用CKEditor插件。
#### 后端设计

```
graph LR
Blog-->po
Blog-->dto
Blog-->util
Blog-->servlet
Blog-->dao
Blog-->filter
po-->ArticlePo
po-->CategoryPo
po-->MessagePo
po-->UserPo
dto-->AboutDto
dto-->ArticleDto
dto-->ArticleLiteDto
dto-->CategoryDto
dto-->MessageDto
dto-->MessageReplyDto
dto-->UserDto
util-->DBUtil
util-->DateUtil
util-->MD5Util
util-->PictureUtil
util-->PropertiesUtil
servlet-->AboutServlet
servlet-->AboutManageServlet
servlet-->ArchiveServlet
servlet-->CategoryServlet
servlet-->CategoryManageServlet
servlet-->HomeServlet
servlet-->LoginServlet
servlet-->ManageServlet
servlet-->MessageServlet
servlet-->MessageManageServlet
servlet-->PictureManageServlet
dao-->ArticleDao
dao-->CategoryDao
dao-->MessageDao
dao-->UserDao
dao-->LinkDao
filter-->loginFilter
```
#### 前端设计

```
graph LR
index.jsp-->about.jsp
index.jsp-->archive.jsp
index.jsp-->article.jsp
index.jsp-->articleDetail.jsp
index.jsp-->category.jsp
index.jsp-->message.jsp
index.jsp-->messageForm.jsp
index.jsp-->messageList.jsp
manage.jsp-->aboutManage.jsp
manage.jsp-->aboutSaveManage.jsp
manage.jsp-->articleManage.jsp
manage.jsp-->articleSaveManage.jsp
manage.jsp-->categoryManage.jsp
manage.jsp-->categorySaveManage.jsp
manage.jsp-->linkManage.jsp
manage.jsp-->linkSaveManage.jsp
manage.jsp-->manageDetail.jsp
manage.jsp-->messageManage.jsp
manage.jsp-->pictureManage.jsp
login.jsp-->loginCard.jsp
login.jsp-->registerCard.jsp
```
#### 数据库设计
1. 数据库创建好后会添加about分类、“关于”文章、默认管理员（账号admin，密码123）
2. blog_article表中第一篇是关于文章，categoryId为1
3. blog_message中，messageType 0 指代消息类型的评论类型 1 留言 2 评论回复 3 留言回复 pid为消息的父ID,其中留言的pid设定为0
4. blog_user中，userType 0代表普通用户，1代表管理员
5. 具体可查看blog.sql文件