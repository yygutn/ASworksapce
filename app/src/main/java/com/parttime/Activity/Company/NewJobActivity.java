package com.parttime.Activity.Company;

import android.content.res.AssetManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import cn.bmob.v3.listener.SaveListener;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.parttime.Adapter.XmlParserHandler;
import com.parttime.BaseLibs.BaseActivity;
import com.parttime.Modules.City.CityModel;
import com.parttime.Modules.City.DistrictModel;
import com.parttime.Modules.City.ProvinceBean;
import com.parttime.Modules.City.ProvinceModel;
import com.parttime.Modules.Node;
import com.parttime.Modules.User;
import com.parttime.R;
import com.parttime.UI.Interface.TopBarStatus;
import com.parttime.UI.Interface.TopBarStatusRight;
import com.parttime.UI.TopBar;
import com.parttime.Utils.StringUtil;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@EActivity(R.layout.activity_new_job)
public class NewJobActivity extends BaseActivity {

    @ViewById(R.id.new_job_name)
    EditText mJobName;
    @ViewById(R.id.new_job_pay)
    EditText mJobPay;
    @ViewById(R.id.new_job_number)
    EditText mJobNumber;
    @ViewById(R.id.new_job_work_remark)
    EditText mJobRemark;

    @ViewById(R.id.new_job_sex_man)
    CheckBox mMan;
    @ViewById(R.id.new_job_sex_woman)
    CheckBox mWoman;
    @ViewById(R.id.new_job_sex_no)
    CheckBox mSexNo;

    @ViewById(R.id.new_job_work_location)
    TextView mWorkLoc;
    @ViewById(R.id.new_job_gathering_location)
    TextView mGatheringLoc;
    @ViewById(R.id.new_job_work_time_range)
    TextView mTimeRange;
    @ViewById(R.id.new_job_work_time_start)
    TextView mTimeStart;
    @ViewById(R.id.new_job_work_time_end)
    TextView mTimeEnd;

    @ViewById(R.id.new_job_topBar)
    TopBar mTopBar;

    private String sex = "性别不限";
    private String jobName = "";
    private String jobPay = "";
    private String jobNumber = "";
    private String jobRemark = "";
    private String gatheringLocation = "";
    private String workLocation = "";
    private String timeRange = "";
    private String timeStart = "";
    private String timeEnd = "";


    List<ProvinceModel> provinceList = null;
    TimePickerView mTimer;
    OptionsPickerView mOptions;

    private ArrayList<ProvinceBean> options1Items = new ArrayList<ProvinceBean>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();


    @AfterViews
    void start() {
        mTopBar.setBackIconVisible();
        mTopBar.setTitle("发布兼职");
        mTopBar.setRightTitle("确认发布");
        mTopBar.setTopBarStatusRightListener(new TopBarStatusRight() {
            @Override
            public void onRightClickDelegate() {
                doNewJob();
            }
        });
        mTopBar.setTopBarStatusListener(new TopBarStatus() {
            @Override
            public void onTopBarBackClickDelegate() {
                backToPreActivity();
            }
        });
        initProvinceData();
    }

    /**
     * upload to a new part-time job message
     */
    private void doNewJob() {
        jobName = mJobName.getText().toString();
        if (StringUtil.isNullOrEmpty(jobName)) {
            showToast("兼职名称不能为空");
            return;
        }
        jobPay = mJobPay.getText().toString();
        if (StringUtil.isNullOrEmpty(jobPay) || Integer.valueOf(jobPay) <= 0) {
            showToast("日薪不能为空和负数");
            return;
        }
        workLocation = mWorkLoc.getText().toString();
        if (StringUtil.isNullOrEmpty(workLocation)) {
            showToast("工作地点不能为空");
            return;
        }
        gatheringLocation = mGatheringLoc.getText().toString();
        if (StringUtil.isNullOrEmpty(gatheringLocation)) {
            showToast("集合地点不能为空");
            return;
        }
        timeRange = mTimeRange.getText().toString();
        if (StringUtil.isNullOrEmpty(timeRange)) {
            showToast("工作日期不能为空");
            return;
        }
        timeStart = mTimeStart.getText().toString();
        if (StringUtil.isNullOrEmpty(timeStart)) {
            showToast("工作开始时间不能为空");
            return;
        }
        timeEnd = mTimeEnd.getText().toString();
        if (StringUtil.isNullOrEmpty(timeEnd)) {
            showToast("工作结束不能为空");
            return;
        }
        jobNumber = mJobNumber.getText().toString();
        if (StringUtil.isNullOrEmpty(jobNumber)) {
            showToast("需求人数不能为空");
            return;
        }
        jobRemark = mJobRemark.getText().toString();
        Node node = new Node();
        User user = User.getCurUser(this);
        node.setCompany(user.getCompanyName() == null ? "" : user.getCompanyName());
        node.setCompanyId(user.getId());
        node.setNumExpected(Integer.valueOf(jobNumber));
        node.setPay(jobPay + "/天");
        node.setJobname(jobName);
        node.setSexExpected(sex);
        node.setWorkLocation(workLocation);
        node.setGathering_location(gatheringLocation);
        node.setWorktimerange(timeRange);
        node.setWork_time_start(timeStart);
        node.setWork_time_end(timeEnd);
        node.setWorkType("其他");
        node.setNumHave(0);
        node.setGathering_time(new Random().nextInt(1000000000));
        node.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                showToast("发布兼职成功");
                setResult(11);
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                showToast("网络异常，发布兼职失败");
            }
        });
    }

    /**
     * click listener
     *
     * @param view listener have sth to do
     */
    @Click({R.id.new_job_sex_man, R.id.new_job_sex_woman, R.id.new_job_sex_no,
            R.id.new_job_gathering_location, R.id.new_job_work_location, R.id.new_job_work_time_range,
            R.id.new_job_work_time_start, R.id.new_job_work_time_end})
    void click(View view) {
        switch (view.getId()) {
            case R.id.new_job_sex_man: {
                reSetCheckBox();
                mMan.setChecked(true);
                sex = "男";
                break;
            }
            case R.id.new_job_sex_woman: {
                reSetCheckBox();
                mWoman.setChecked(true);
                sex = "女";
                break;
            }
            case R.id.new_job_sex_no: {
                reSetCheckBox();
                mSexNo.setChecked(true);
                sex = "性别不限";
                break;
            }
            case R.id.new_job_work_location: {
                doSelectCity(0);
                break;
            }
            case R.id.new_job_gathering_location: {
                doSelectCity(1);
                break;
            }
            case R.id.new_job_work_time_range: {
                doSelectTime(2);
                break;
            }
            case R.id.new_job_work_time_start: {
                doSelectTime(3);
                break;
            }
            case R.id.new_job_work_time_end: {
                doSelectTime(4);
                break;
            }

        }
    }

    /**
     * timePiker
     *
     * @param index position
     */
    private void doSelectTime(final int index) {
        if (index == 2) {
            mTimer = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        }
        else if (index == 3 || index == 4) {
            mTimer = new TimePickerView(this, TimePickerView.Type.HOURS_MINS);
        }
        mTimer.setTime(new Date());
        mTimer.setCyclic(false);
        mTimer.setCancelable(true);
        mTimer.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format;
                if (index == 2){
                    format =  new SimpleDateFormat("yyyy-MM-dd");
                } else {
                    format= new SimpleDateFormat("HH:mm");
                }
                setText(index,format.format(date));
            }
        });
        mTimer.show();
    }

    /**
     * select the city u want
     *
     * @param index selection
     */
    private void doSelectCity(final int index) {
        mOptions = new OptionsPickerView(this);
        //add data to pikerView
        ArrayList<String> options2Items_i;
        int len1 = provinceList.size();
        //选项1
        for (int i = 0; i < len1; i++) {
            options1Items.add(new ProvinceBean(i, provinceList.get(i).getName(), "", ""));
        }
        //选项2
        for (int i = 0; i < len1; i++) {
            options2Items_i = new ArrayList<String>();
            List<CityModel> cityList = provinceList.get(i).getCityList();
            for (int j = 0; j < cityList.size(); j++) {
                options2Items_i.add(cityList.get(j).getName());
            }
            options2Items.add(options2Items_i);
        }
        //选项3
        for (int i = 0; i < len1; i++) {
            List<CityModel> cityList = provinceList.get(i).getCityList();
            ArrayList<ArrayList<String>> options3Items_j = new ArrayList<ArrayList<String>>();
            ArrayList<String> options3Items_i_j = new ArrayList<>();
            for (int j = 0; j < cityList.size(); j++) {
                options3Items_j = new ArrayList<ArrayList<String>>();
                List<DistrictModel> districtModelArrayList = cityList.get(j).getDistrictList();
                options3Items_i_j = new ArrayList<String>();
                for (int k = 0; k < districtModelArrayList.size(); k++) {
                    options3Items_i_j.add(districtModelArrayList.get(k).getName());
                }
                options3Items_j.add(options3Items_i_j);
            }
            options3Items.add(options3Items_j);
        }
        //end
        mOptions.setPicker(options1Items, options2Items, options3Items, true);
        mOptions.setTitle("选择地点");
        mOptions.setCyclic(false, true, true);
        mOptions.setSelectOptions(0, 0, 0);
        mOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String t = options2Items.get(options1).get(option2).
                        equals(options1Items.get(options1).getPickerViewText()) ? "" : options2Items.get(options1).get(option2);
                String tx = options1Items.get(options1).getPickerViewText()
                        + t + options3Items.get(options1).get(option2).get(options3);
                setText(index, tx);
            }
        });
        mOptions.show();
    }

    /**
     * set the callback text
     * @param index positon
     * @param tx text
     */
    private void setText(int index, String tx) {
        switch (index) {
            case 0: {
                mWorkLoc.setText(tx);
                break;
            }
            case 1: {
                mGatheringLoc.setText(tx);
                break;
            }
            case 2: {
                mTimeRange.setText(tx);
                break;
            }
            case 3: {
                mTimeStart.setText(tx);
                break;
            }
            case 4: {
                mTimeEnd.setText(tx);
                break;
            }
            default:
                break;
        }
    }

    private void reSetCheckBox() {
        mMan.setChecked(false);
        mWoman.setChecked(false);
        mSexNo.setChecked(false);
    }

    /**
     * city select init from assets file
     */
    private void initProvinceData() {
        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();

        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }


}
