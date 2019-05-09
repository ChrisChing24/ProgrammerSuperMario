package com.white.ghost.programmersupermario.bean;

import com.white.ghost.programmersupermario.base.BaseResponse;

import java.io.Serializable;

/**
 * Function:登录接口返回数据
 * Author Name: Chris
 * Date: 2019/5/6 15:34
 */
public class LoginBean extends BaseResponse {

    /**
     * response : {"msg":"登录成功！","token":"OTFkMWx0ZlpGTWNiY2ZCbmhHc2lKSHE3U0RzQ01lRzZLelk4MkhRTEtPbUp6MzNFYlpVQXFkSitFaU9DKzZPTGVxL1NweUN0dFpPdmZvQ3ZqbUZjTHprMFNBODNCak5TM2RRdi8xQ1YrK3IxUEx5RzA2cnpoeTFReGQvRnNiRE1zdXZHUEE=","flag":false,"ic_school_normal":{"school_id":10055,"school_name":"晓信科技","school_img":"http://jiaowu-1252817547.file.myqcloud.com/formalPicture/201805111028069568.png"},"teacher_info":{"teacher_id":21648,"teacher_name":"杜中法","photo":"http://growthimage-1252817547.image.myqcloud.com/201708/49ad7a98-85f6-49af-b8a6-820cb0c1501f","account_number":"13045684793","teacher_type":2},"is_operate":false}
     */

    private ResponseBean response;

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * msg : 登录成功！
         * token : OTFkMWx0ZlpGTWNiY2ZCbmhHc2lKSHE3U0RzQ01lRzZLelk4MkhRTEtPbUp6MzNFYlpVQXFkSitFaU9DKzZPTGVxL1NweUN0dFpPdmZvQ3ZqbUZjTHprMFNBODNCak5TM2RRdi8xQ1YrK3IxUEx5RzA2cnpoeTFReGQvRnNiRE1zdXZHUEE=
         * flag : false
         * ic_school_normal : {"school_id":10055,"school_name":"晓信科技","school_img":"http://jiaowu-1252817547.file.myqcloud.com/formalPicture/201805111028069568.png"}
         * teacher_info : {"teacher_id":21648,"teacher_name":"杜中法","photo":"http://growthimage-1252817547.image.myqcloud.com/201708/49ad7a98-85f6-49af-b8a6-820cb0c1501f","account_number":"13045684793","teacher_type":2}
         * is_operate : false
         */

        private String msg;
        private String token;
        private boolean flag;
        private SchoolBean school;
        private TeacherInfoBean teacher_info;
        private boolean is_operate;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public SchoolBean getSchool() {
            return school;
        }

        public void setSchool(SchoolBean school) {
            this.school = school;
        }

        public TeacherInfoBean getTeacher_info() {
            return teacher_info;
        }

        public void setTeacher_info(TeacherInfoBean teacher_info) {
            this.teacher_info = teacher_info;
        }

        public boolean isIs_operate() {
            return is_operate;
        }

        public void setIs_operate(boolean is_operate) {
            this.is_operate = is_operate;
        }

        public static class SchoolBean implements Serializable {
            /**
             * school_id : 10055
             * school_name : 晓信科技
             * school_img : http://jiaowu-1252817547.file.myqcloud.com/formalPicture/201805111028069568.png
             */

            private int school_id;
            private String school_name;
            private String school_img;

            public int getSchool_id() {
                return school_id;
            }

            public void setSchool_id(int school_id) {
                this.school_id = school_id;
            }

            public String getSchool_name() {
                return school_name;
            }

            public void setSchool_name(String school_name) {
                this.school_name = school_name;
            }

            public String getSchool_img() {
                return school_img;
            }

            public void setSchool_img(String school_img) {
                this.school_img = school_img;
            }
        }

        public static class TeacherInfoBean implements Serializable {
            /**
             * teacher_id : 21648
             * teacher_name : 杜中法
             * photo : http://growthimage-1252817547.image.myqcloud.com/201708/49ad7a98-85f6-49af-b8a6-820cb0c1501f
             * account_number : 13045684793
             * teacher_type : 2
             */

            private int teacher_id;
            private String teacher_name;
            private String photo;
            private String account_number;
            private int teacher_type;

            public int getTeacher_id() {
                return teacher_id;
            }

            public void setTeacher_id(int teacher_id) {
                this.teacher_id = teacher_id;
            }

            public String getTeacher_name() {
                return teacher_name;
            }

            public void setTeacher_name(String teacher_name) {
                this.teacher_name = teacher_name;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getAccount_number() {
                return account_number;
            }

            public void setAccount_number(String account_number) {
                this.account_number = account_number;
            }

            public int getTeacher_type() {
                return teacher_type;
            }

            public void setTeacher_type(int teacher_type) {
                this.teacher_type = teacher_type;
            }
        }
    }
}
