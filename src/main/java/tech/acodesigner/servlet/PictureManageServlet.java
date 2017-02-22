package tech.acodesigner.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import tech.acodesigner.util.PictureUtil;
import tech.acodesigner.util.PropertiesUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 张秦遥 on 2017/2/22/0022.
 */
@WebServlet(name = "PictureManageServlet", urlPatterns = "/pictureManage")
public class PictureManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageBasePath = getServletContext().getRealPath("images");
        // System.out.println(imageBasePath);
        processUpload(request, response, imageBasePath);

        String[] userImages = PictureUtil.getPictures(imageBasePath, PropertiesUtil.getValue("userPicturePath"));
        String[] articleImages = PictureUtil.getPictures(imageBasePath, PropertiesUtil.getValue("articlePicturePath"));
        request.setAttribute("userImages", userImages);
        request.setAttribute("articleImages", articleImages);

        request.setAttribute("mainPage", "pictureManage.jsp");
        request.getRequestDispatcher("manage.jsp").forward(request, response);
    }
    private void processUpload(HttpServletRequest request, HttpServletResponse response, String imageBasePath) {
        System.out.println("process upload");
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            return;
//			e.printStackTrace();
        }
        if (items == null) {
            return;
        }
        Iterator<FileItem> itr = items.iterator();
        while (itr.hasNext()) {
            FileItem item = (FileItem) itr.next();
            if (item.isFormField()) {
                System.out.println("error");
            } else if ("userImage".equals(item.getFieldName())) {
                try {
                    String imageName = item.getName();
                    String filePath = imageBasePath + File.separator
                            + PropertiesUtil.getValue("userPicturePath")
                            + File.separator + imageName;
                    System.out.println(filePath);
                    item.write(new File(filePath));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if ("articleImage".equals(item.getFieldName())) {
                try {
                    String imageName = item.getName();
                    String filePath = imageBasePath + File.separator
                            + PropertiesUtil.getValue("articlePicturePath")
                            + File.separator + imageName;
                    System.out.println(filePath);
                    item.write(new File(filePath));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
