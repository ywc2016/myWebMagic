package util;

import db.BadgeDao;
import db.MedalDao;
import po.Badge;
import po.Medal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ywcrm on 2017/6/7.
 */
public class Utils {

    // 把list转换为string，用,分割
    public String listToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    /**
     * @param medalImageList
     * @return
     */
    public List<String> convertMedalImageListToIdlist(List<String> medalImageList) {
        MedalDao medalDao = new MedalDao();
        List<String> ids = new ArrayList<String>();
        for (String medalImage : medalImageList) {
            List<Medal> list = medalDao.findByPropertyEqual("imageUrl", medalImage, "String");
            if (list.size() == 0) {
                ids.add("-1");
            } else {
                ids.add(list.get(0).getId() + "");
            }
        }
        return ids;
    }

    public List<String> convertBadgeImageListToIdList(List<String> badgeImageList) {
        BadgeDao badgeDao = new BadgeDao();
        List<String> ids = new ArrayList<String>();
        for (String badgeImage : badgeImageList) {
            List<Badge> list = badgeDao.findByPropertyEqual("imageUrl", badgeImage, "String");
            if (list.size() == 0) {
                ids.add("-1");
            } else {
                ids.add(list.get(0).getId() + "");
            }
        }
        return ids;
    }

}
