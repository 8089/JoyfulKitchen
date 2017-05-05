package com.joyful.joyfulkitchen.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */

public class SearchMeauList implements Serializable{


    /**
     * id : 5136
     * title : 酱肉包子
     * tags : 早餐;面食;包子;夏季饮食;上班族
     * imtro : 有的人认为夏天的饮食要尽可能清淡，最好是一点油星都别沾，这样才能清凉不燥，其实这种观点片面了，确实，夏天人体燥热，吃过多油腻的东西会导致上火，但是天气越热，人体消耗越大，加上出汗过多，如果吃得太过清淡，能量补充太少，尤其是主食吃得太少，人体很容易虚脱，，今天的酱肉包子就是专门为夏天补充能量准备的，饿的时候吃一个保证你浑身来劲
     * ingredients : 面粉,800g;温水,400ml;肥瘦肉,400g;大葱,200g
     * burden : 花生油,2大勺;甜面酱,2小勺;盐,1/2小勺;酱油,1/2小勺;料酒,1/2小勺;香油,适量;胡椒粉,适量;姜,适量
     * albums : ["http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/t/6/5136_467931.jpg"]
     * steps : [{"img":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/52/5136_bef39484e3e5b204.jpg","step":"1.准备材料"},{"img":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/52/5136_25406b4e50d208b5.jpg","step":"2.温水溶解酵母，将酵母水分多次倒入面粉中，边加水边揉面，最后揉成光滑的面团，室温醒发1小时"},{"img":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/52/5136_b27fcdc1f2663088.jpg","step":"3.猪肉切成1厘米见方的肉丁，大葱切成同样大小的小片，姜切末"},{"img":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/52/5136_ca4b61e3486a99ff.jpg","step":"4.肉丁中加入面酱、盐、胡椒粉、料酒、花生油和香油"},{"img":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/52/5136_5a390d309bf38133.jpg","step":"5.搅拌均匀后腌制半个小时"},{"img":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/52/5136_23dcbff06118624e.jpg","step":"6.腌制好的肉馅拌入大葱"},{"img":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/52/5136_451a5cc1552eca32.jpg","step":"7.搅拌均匀即成包子馅"},{"img":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/52/5136_65e26a75c470090c.jpg","step":"8.发好的面团拿出揉匀，揪成小剂子"},{"img":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/52/5136_f0947681f895374d.jpg","step":"9.将面剂子按扁擀成圆皮"},{"img":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/52/5136_ef42e9eef07450db.jpg","step":"10.取一张面皮，放入肉丁馅"},{"img":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/52/5136_e04880db52c252ba.jpg","step":"11.捏褶收口成包子"},{"img":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/52/5136_fbbd889d12e4ee03.jpg","step":"12.做好的包子盖湿布发酵20分钟"},{"img":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/52/5136_5f027244524dacaa.jpg","step":"13.凉水上屉旺火蒸约18分钟，关火焖5分钟"}]
     */

    private String id;
    private String title;
    private String tags;
    private String imtro;
    private String ingredients;
    private String burden;

    //private List<String> foodMatail;
    private List<Matail> foodMatail;

    private List<String> albums;
    private List<StepsBean> steps;

    public SearchMeauList() {
        super();
    }

    public SearchMeauList(String id, String title, String tags, String imtro, String ingredients, String burden,List<Matail> foodMatail,List<String> albums, List<StepsBean> steps) {
        this.id = id;
        this.title = title;
        this.tags = tags;
        this.imtro = imtro;
        this.ingredients = ingredients;
        this.burden = burden;
        this.foodMatail= foodMatail;
        this.albums = albums;
        this.steps = steps;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImtro() {
        return imtro;
    }

    public void setImtro(String imtro) {
        this.imtro = imtro;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getBurden() {
        return burden;
    }

    public void setBurden(String burden) {
        this.burden = burden;
    }

    public List<String> getAlbums() {
        return albums;
    }

    public void setAlbums(List<String> albums) {
        this.albums = albums;
    }

    public List<StepsBean> getSteps() {
        return steps;
    }

    public void setSteps(List<StepsBean> steps) {
        this.steps = steps;
    }


    public List<Matail> getFoodMatail() {
        return foodMatail;
    }

    public void setFoodMatail(List<Matail> foodMatail) {
        this.foodMatail = foodMatail;
    }

    public static class StepsBean implements Serializable{
        /**
         * img : http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/52/5136_bef39484e3e5b204.jpg
         * step : 1.准备材料
         */

        private String img;
        private String step;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getStep() {
            return step;
        }

        public void setStep(String step) {
            this.step = step;
        }

        public StepsBean(String img, String step) {
            this.img = img;
            this.step = step;
        }

        @Override
        public String toString() {
            return "StepsBean{" +
                    "img='" + img + '\'' +
                    ", step='" + step + '\'' +
                    '}';
        }
    }

    public static class Matail implements Serializable{
        private String name ;
        private String count;
        private double weight = 0;  // 实际称量重量
        private boolean isComplete = false;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public boolean isComplete() {
            return isComplete;
        }

        public void setComplete(boolean complete) {
            isComplete = complete;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Matail{" +
                    "name='" + name + '\'' +
                    ", count='" + count + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SearchMeauList{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", tags='" + tags + '\'' +
                ", imtro='" + imtro + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", burden='" + burden + '\'' +
                ", foodMatail=" + foodMatail +
                ", albums=" + albums +
                ", steps=" + steps +
                '}';
    }
}
