package com.parttime.Modules;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;

import java.io.Serializable;

/**
 * Created by jumy on 15/9/5 下午12:38.
 * deadline is the first productivity
 */
public class Node extends BmobObject implements Serializable {
    public static final String TAG = "com.parttime.Modules.Node";
    private Integer id;// 信息编号
    private String jobname;//工作名称
    private String pay;//工资
    private String SignUpDeadLine;//报名截止
    private String workLocation;//工作地点
    private BmobGeoPoint location;//地点
    private String company;//所属企业
    private Integer companyId;//所属企业ID
    private String sexExpected;//性别需求
    private Integer gathering_time;//集合时间
    private String gathering_location;//集合地点
    private String workType;//工作类型
    private String worktimerange;//工作日期
    private Integer time_start;//工作开始时间 工作日期
    private Integer time_end;//工作结束时间
    private String timeLine;//工作时间
    private Integer numExpected;//需要人数
    private Integer numHave;//已招聘人数
    private String remark;//工作简介
    //要求，最多四个
    private String v1;
    private String v2;
    private String v3;
    private String v4;

    public Node() {
        this.setTableName("Node");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getSignUpDeadLine() {
        return SignUpDeadLine;
    }

    public void setSignUpDeadLine(String signUpDeadLine) {
        SignUpDeadLine = signUpDeadLine;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public BmobGeoPoint getLocation() {
        return location;
    }

    public void setLocation(BmobGeoPoint location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getSexExpected() {
        return sexExpected;
    }

    public void setSexExpected(String sexExpected) {
        this.sexExpected = sexExpected;
    }

    public Integer getGathering_time() {
        return gathering_time;
    }

    public void setGathering_time(Integer gathering_time) {
        this.gathering_time = gathering_time;
    }

    public String getGathering_location() {
        return gathering_location;
    }

    public void setGathering_location(String gathering_location) {
        this.gathering_location = gathering_location;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getWorktimerange() {
        return worktimerange;
    }

    public void setWorktimerange(String worktimerange) {
        this.worktimerange = worktimerange;
    }

    public Integer getTime_start() {
        return time_start;
    }

    public void setTime_start(Integer time_start) {
        this.time_start = time_start;
    }

    public Integer getTime_end() {
        return time_end;
    }

    public void setTime_end(Integer time_end) {
        this.time_end = time_end;
    }

    public String getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(String timeLine) {
        this.timeLine = timeLine;
    }

    public Integer getNumExpected() {
        return numExpected;
    }

    public void setNumExpected(Integer numExpected) {
        this.numExpected = numExpected;
    }

    public Integer getNumHave() {
        return numHave;
    }

    public void setNumHave(Integer numHave) {
        this.numHave = numHave;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public String getV2() {
        return v2;
    }

    public void setV2(String v2) {
        this.v2 = v2;
    }

    public String getV3() {
        return v3;
    }

    public void setV3(String v3) {
        this.v3 = v3;
    }

    public String getV4() {
        return v4;
    }

    public void setV4(String v4) {
        this.v4 = v4;
    }
}
