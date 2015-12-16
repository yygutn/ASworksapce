package com.parttime.Modules;

import android.content.Context;
import cn.bmob.v3.BmobUser;
import com.parttime.R;

/**
 * Created by Jumy on 15/11/29 下午8:16.
 * deadline is the first productivity
 */
public class User extends BmobUser{
    private Integer id;
    private String CompanyName;//公司名字
    private String Boss;//负责人
    private String head;//头像
    private String TEL;//g固话
    private String Location;//公司地址
    private String nickname;//昵称
    private String remark;//简介
    private Boolean type;// 0 custom 1 company

    public static void saveUserInfo(User user){
        Config.getInstance().setBoolean("type",user.getType()==null?false:user.getType());
        Config.getInstance().setInt("id",user.getId()>0?0:user.getId());
        Config.getInstance().set("head",user.getHead()==null?"":user.getHead());
        Config.getInstance().set("nickname",user.getNickname()==null?"":user.getNickname());
        Config.getInstance().set("remark",user.getRemark()==null?"":user.getRemark());
        Config.getInstance().set("username",user.getUsername()==null?"":user.getUsername());
        Config.getInstance().set("mobilePhoneNumber",user.getMobilePhoneNumber()==null?"":user.getMobilePhoneNumber());
        Config.getInstance().set("email",user.getEmail()==null?"":user.getEmail());

        Config.getInstance().set("CompanyName",user.getCompanyName()==null?"":user.getCompanyName());
        Config.getInstance().set("Boss",user.getBoss()==null?"":user.getBoss());
        Config.getInstance().set("TEL",user.getTEL()==null?"":user.getTEL());
        Config.getInstance().set("Location",user.getLocation()==null?"":user.getLocation());

    }

    public static User getUserInfo(Context context){
        User user = new User();
        user.setType(Config.getInstance().getBoolean("type",false));
        user.setId(Config.getInstance().getInt("id",0));
        user.setHead(Config.getInstance().get("head",context.getResources().getString(R.string.defaulthead)));
        user.setNickname(Config.getInstance().get("nickname",""));
        user.setRemark(Config.getInstance().get("remark",""));
        user.setUsername(Config.getInstance().get("username",""));
        user.setMobilePhoneNumber(Config.getInstance().get("mobilePhoneNumber",""));
        user.setEmail(Config.getInstance().get("email",""));

        user.setCompanyName(Config.getInstance().get("CompanyName",""));
        user.setBoss(Config.getInstance().get("Boss",""));
        user.setTEL(Config.getInstance().get("TEL",""));
        user.setLocation(Config.getInstance().get("Location",""));
        return user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getBoss() {
        return Boss;
    }

    public void setBoss(String boss) {
        Boss = boss;
    }

    public String getTEL() {
        return TEL;
    }

    public void setTEL(String TEL) {
        this.TEL = TEL;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
