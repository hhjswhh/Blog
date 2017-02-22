package tech.acodesigner.util;

import java.io.File;

/**
 * Created by 张秦遥 on 2017/2/22/0022.
 */
public class PictureUtil {

    public static String[] getPictures(String basePath, String picture) {
        File pictureDirector = new File(basePath + File.separator + picture);
        String[] pictures = pictureDirector.list();
        return pictures;
    }
}
