package com.winmax.steps.attendance;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.winmax.dso.ImportFile;
import com.winmax.pages.attendance.AnalysisPage;
import com.winmax.pages.attendance.VacationPage;
import com.winmax.utils.DBHelper;
import com.winmax.utils.DataTableUtils;
import com.winmax.utils.Excel;
import com.winmax.utils.Utils;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class VacationStep {

	private static final Logger logger = LogManager.getLogger(VacationStep.class);
	private VacationPage vp = new VacationPage();
	public AnalysisPage anp = new AnalysisPage();
	private ImportFile file = null;
	
	@Then("^I search leave balance by leave code (.+) and employee id (.+)$")
	public void search_leave_balance(String leaveCode, String employeeID) throws Exception {
	    vp.searchLeaveBalance(Utils.getTestDataProperty(leaveCode), Utils.getTestDataProperty(employeeID));
	}

	@Then("^I update leave banlance for employee id (.+)$")
	public void i_update_leave_banlance(String id, DataTable data) throws Exception {
		HashMap<String, String> hash = DataTableUtils.toHashMap(data);
		String lastYear = hash.get("上年结余");
		String thisYear = hash.get("本年享有");
		String countYear = hash.get("本年调整");
		String nextYear = hash.get("下年享有");
		vp.updateBanlance(id, lastYear, thisYear, countYear, nextYear);	
	}

	@Then("^The leave banlance for (.+) should be$")
	public void verify_leave_banlance(String id, DataTable data) throws Exception {
		HashMap<String, String> hash = DataTableUtils.toHashMap(data);
		String lastYear = hash.get("上年结余");
		String thisYear = hash.get("本年享有");
		String countYear = hash.get("本年调整");
		String nextYear = hash.get("下年享有");
		vp.verifyBanlance(id, lastYear, thisYear, countYear, nextYear);
	}
	
	@Then("^I edit card information for (.+)$")
	public void i_edit_card_information_for(String employeeNo, DataTable data) throws Exception {
		HashMap<String, String> hash = DataTableUtils.toHashMap(data);
		String cardNo = hash.get("卡号");
    	String effectiveDate = hash.get("生效日期");
    	String expiredDate = hash.get("失效日期");  	
    	vp.editCardInformation(cardNo, effectiveDate, expiredDate);
	}

	@Then("^analysis result hint (.+)$")
	public void analysis_result_hint(String hit) throws Exception {
		vp.analysisResultHit(hit);
	}
	
	 @And("^I import leaveFile (.+)$")
	  public void import_file(String fileName) throws Exception {
		file = new ImportFile(fileName);
	    vp.importLeave(fileName);
	 }
	 
	 @Then("^verify vacation details for(.+)$")
	  public void verify_vacation_details_for(String leaveCode, DataTable data) throws Exception {
		 HashMap<String, String> hash = DataTableUtils.toHashMap(data);
		 String startDate = hash.get("开始日期");
		 String endDate = hash.get("结束日期");
		 vp.verifyVacationDetails(leaveCode, startDate, endDate);
	 }
	 
	@When("^I import 休假结余 excel (.+)$")
	public void import_vacation_balance(String fileName) throws Exception {
		vp.importVacationBalance(fileName);
	} 
	 
	 @And("^I add period details for (.+)$")
	 public void i_add_period_details_for(String winmaxSoftCycle, DataTable data) throws Exception {
		 HashMap<String, String> hash = DataTableUtils.toHashMap(data);
		 String cycleName = hash.get("名称");
		 String year = hash.get("年");
		 String month = hash.get("月");
		 String startDate = hash.get("开始时间");
		 String endDate = hash.get("结束时间");
		 vp.addPeriod(winmaxSoftCycle, cycleName, year, month, startDate, endDate);  
	 }

	 @Then("^verify preiod details for (.+)$")
	 public void verify_preiod_details_for(String winmaxSoftCycle, DataTable data) throws Exception {
		 HashMap<String, String> hash = DataTableUtils.toHashMap(data);
		 String startDate = Utils.getDate(hash.get("开始时间"));
		 String endDate = Utils.getDate(hash.get("结束时间"));
		 String cycleName = hash.get("名称");
		 vp.verifyPreiodDetails(winmaxSoftCycle, cycleName, startDate, endDate);
	 }
	 
	@Then("^I verify imported 休假结余 data from (.+) should display correctly$")
	public void verify_imported_card_data(String fileName) throws Exception {	     
		List<LinkedHashMap<String, String>> excelData = Excel.readWorkBook(Utils.getImportExcelFilePath(fileName));
		vp.verifyVacationBalanceUIWithExcel(excelData);
	}	
	
	@And("^I delete 休假结余 from DB$")
	public void delete_leave_balance_from_DB(DataTable data) throws Exception {
		HashMap<String, String> hash = DataTableUtils.toHashMap(data);
		String leaveCode = Utils.getDate(hash.get("休假代码"));
		String staffId = Utils.getDate(hash.get("员工编号"));
		DBHelper.deleteVacationBalance(leaveCode, staffId);
	}
	 
	 @Then("^I add leave record of (.+) and leaveType (.+)$")
	 public void I_add_leave_record_of(String leaveCode, String leaveType, DataTable data) throws Exception {
		 HashMap<String, String> hash = DataTableUtils.toHashMap(data);
		 String startDate = Utils.getDate(hash.get("开始日期"));
		 String endDate =  Utils.getDate(hash.get("结束日期"));
		 String startTime = Utils.getDate(hash.get("开始时间"));
		 String endTime = Utils.getDate(hash.get("结束时间"));
		 vp.addLeaveRecord(leaveCode, leaveType, startDate, endDate, startTime, endTime);
	 }
	 
	 @Then("^I delete leave record of (.+)$")
	 public void i_delete_leave_record_of(String leave, DataTable data) throws Exception {
		 HashMap<String, String> hash = DataTableUtils.toHashMap(data);
		 String startDate = Utils.getDate(hash.get("开始日期"));
		 String endDate =  Utils.getDate(hash.get("结束日期"));   
		 vp.deleteLeaveRecord(leave, startDate, endDate);
	 }
	 
	 @Then("^I click statistics button $")
	 public void I_click_statistics_button () {
		 vp.clickStatisticsButton();
	 }
	 
	 @Then("^I verify totalHoursLeave (.+) and staffNo (.+)$")
	 public void verify_total_hours_Leave(String hours, String staffNo) {
         vp.verifyTotalHoursLeaveTime(hours, staffNo);
	 }
	 
	 @Then("^input the keyWords (\\d+) and edit leaveCode (.+)$")
	 public void input_the_keyWords_and_edit_leaveCode_PL(String keyWords, String leaveCode, DataTable data) throws Exception {
		 HashMap<String, String> hash = DataTableUtils.toHashMap(data);
		 String minimumLeaveNumber = hash.get("最小请假数");
		 String minimumLeaveUnit = hash.get("请假最少单位");
		 vp.editLeaveCodeSet(keyWords, leaveCode, minimumLeaveNumber, minimumLeaveUnit);
	 }
	 
	 @Then("^add calendar watch$")
	 public void add_calendar_watch(DataTable data) throws Exception {
		 HashMap<String, String> hash = DataTableUtils.toHashMap(data);
		 String vacationType = hash.get("假期类型");
		 String ChineseDes = hash.get("中文描述");
		 String EnglishDes = hash.get("英文描述");
		 vp.addCalendarWatch(vacationType, ChineseDes, EnglishDes);
	 }

	 @Then("^delete calendar watch type (\\d+)$")
	 public void delete_calendar_watch_type(String vacationType) throws Exception {
	     vp.deleteCalendarWatch(vacationType);
	 }
	 
	 @Then("^verify calendar watch contains (.+)$")
	 public void verify_calendar_watch_contains(String calendarName) throws Exception {
	     vp.verifycalendarWatch(calendarName);
	 }	
	
	@Then("^I add leave details for (.+)$")
	public void i_add_leave_details(String leaveCode, DataTable data) throws Exception {
		HashMap<String, String> hash = DataTableUtils.toHashMap(data);
		String startDate = Utils.getDate(hash.get("开始日期")==null?hash.get("开始时间"):hash.get("开始日期"));
		String endDate =  Utils.getDate(hash.get("结束日期")==null?hash.get("结束时间"):hash.get("结束日期"));
		vp.addLeaveDetails(leaveCode, startDate, endDate);
	}
	
	@Then("^verify leaveDetails contains (.+)$")
	public void verify_leaveDetails_contains (String saveHint) throws Exception {
	    vp.verifyLeaveDetails(saveHint);
	}
	
	@Then("^I choose (.+) and click year end button$")
	public void i_choose_and_click_year_end_button(String yearType, DataTable data) throws Exception {
		HashMap<String, String> hash = DataTableUtils.toHashMap(data);
		String cleanupDeadline = hash.get("清理截止日期");
		vp.clickYearEndButton(yearType, cleanupDeadline);  
	}
	
	@Then("^I add Overtime application$")
	public void i_add_Overtime_application(DataTable data) throws Exception {
	    HashMap<String, String> hash = DataTableUtils.toHashMap(data);
	    String staffNo = hash.get("staff");
	    String date = Utils.getDate(hash.get("date"));
	    String beginTime = hash.get("begin time");
	    String endTime = hash.get("end time");
	    String deadline = Utils.getDate(hash.get("换休截止日 "));
	    String switchTime = hash.get("转换时间");
	    vp.addOvertimeApplication(staffNo, date, beginTime, endTime, deadline, switchTime);
	}
	
	@Then("^I input the keyWords leaveCode (\\d+)$")
	public void i_input_the_keyWords_leaveCode(String keyWords) throws Exception {
	    vp.inputKeyWordsleaveCode(keyWords);
	}

	@Then("^edit leaveCode settings (.+)$")
	public void edit_leaveCode_settings( String leaveCode, DataTable data) throws Exception {
		HashMap<String, String> hash = DataTableUtils.toHashMap(data);
		String checkBox = hash.get("事后补假是否启用"); 
		String allowDays = hash.get("允许事后多少天内补假期");
		vp.editLeaveCodeSettings(leaveCode, checkBox, allowDays);
	}
	
	@And("^I open edit form for leave code (.+)$")
	public void open_edit_leaveCode_form(String leaveCode) throws Exception {
		vp.openEditLeaveCodeForm(leaveCode);		
	}
	
	@And("^I update 假期审批选项$")
	public void updateLeaveItems(DataTable data) throws Exception {
		HashMap<String, String> hash = DataTableUtils.toHashMap(data);
		vp.updateLeaveCodeItems(hash);		
	} 
	
	@Then("^I advanced filter staffNo (\\d+)$")
	public void i_advanced_filter_staffNo(String staffNo) throws Exception {
		vp.advancedFilterStaffNo(staffNo);
	}
	
	@Then("^I Employee attendance analysis$")
	public void i_Employee_attendance_analysis(DataTable data) throws Exception {
	    HashMap<String, String> hash = DataTableUtils.toHashMap(data);
	    String startDate = hash.get("开始日期");
	    String endDate = hash.get("结束日期");
	    vp.employeeAttendanceAnalysis(startDate, endDate);
	}
	
	@Then("^I attendance counts period$")
	public void i_attendance_counts_period(DataTable data) throws Exception {
		HashMap<String, String> hash = DataTableUtils.toHashMap(data);
		String period = hash.get("period");
		String detail = hash.get("detail");
		vp.attendanceCountsPeriod(period, detail);
	}
	
	@Then("^verify totalHoursLeave (.+) and for staffno (\\d+)$")
	public void verify_totalHoursLeave_and_for_staffno(String totolHours, String staffNo) throws Exception {
		vp.verifyTotalHoursLeaveTime(totolHours, staffNo);
	}
	
	@Then("^I verfify leave code (.+) and totalHours (\\d+)$")
	public void i_verfify_leave_code_NPL_and_totalHours(String leave, String totalHours, DataTable data) throws Exception {
	    HashMap<String, String> hash = DataTableUtils.toHashMap(data);
	    String startDate = Utils.getDate(hash.get("开始日期"));
		String endDate =  Utils.getDate(hash.get("结束日期"));
		vp.verifyVacationTotalHours(leave, totalHours, startDate, endDate);		
	}
	
	@Then("^I Clear off vacation date$")
	public void i_Clear_off_vacation_date() throws Exception {
	    vp.clearOffVacationDate();
	}

	@Then("^I click workFlow forms button $")
	public void i_click_workFlow_forms_button(String buttonName) throws Exception {
	    vp.clickWorkFlowFormsButton(buttonName);
	}

	@Then("^verify workFlow information $")
	public void verify_workFlow_information(String information) throws Exception {
	    vp.verifyWorkFlowInformation(information);
	}
	
	@When("^I import leave details excel (.+)$")
	public void i_import_leave_details_excel(String fileName) throws Exception {
	    vp.importLeaveDetailsExcel(fileName);
	}
	
	@Then("^I select leaveCode for (.+)$")
	public void i_select_leaveCode_for(String leaveCode) throws Throwable {
	    vp.selectLeaveCodeFor(leaveCode);
	}
	
	@Then("^I click export button leave Banlance$")
	public void i_click_export_button_leave_Banlance() throws Exception {
	    vp.clickExportButtonLeaveBanlance();
	}

	@Then("^verify the export Excel Data compare with Interface table$")
	public void verify_the_export_Excel_Data_compare_with_Interface_table() throws Exception {
		List<HashMap<String, String>> previewData = vp.getLeaveBanlaceList();
	    String fileName = anp.downloadFile();
	    vp.verifyExportExcelDataCompareWithInterfaceTable(fileName, previewData);
	}
	
	@When("^I import Attendance at attendance excel (.+)$")
	public void i_import_Attendance_at_attendance_excel(String fileName) throws Exception {
        vp.importAttendanceExcel(fileName);
	}

	@Then("^verify  Attendance preiod Name (.+) and staffNo (.+)$")
	public void verify_Attendance_preiod_for(String preiodName, String staffNo) throws Exception {
	    vp.verifyAttendancePreiod(preiodName, staffNo);
	}

	@When("^I import the working hours of the month excel (.+)$")
	public void i_import_the_working_hours_of_the_month_excel(String fileName) throws Exception {
        vp.importWorkingHoursMonthExcel(fileName);
	}
	
	@Then("^I select attendance period (.+) and detail (.+)$")
	public void i_select_attendance_period_and_detail(String preiodName, String preiodDetails) throws Exception {
        vp.selectAttendancePeriodAttendanceDetail(preiodName, preiodDetails);
	}
	
	@Then("^I click Sync calculate btn and click freeze btn$")
	public void i_click_Sync_calculate_btn_and_click_freeze_btn() throws Throwable {
	    vp.clickSyncCalculateBtnAndFreeze();
	}

	@Then("^verify Attendance calculation result for (\\d+)$")
	public void verify_Attendance_calculation_result_for(String staffNo, DataTable data) throws Exception {
		HashMap<String, String> hash = DataTableUtils.toHashMap(data);
	    String hoursAttendance = hash.get("排班小时数");
	    String shouldBeWorkingHours = hash.get("应工作小时数");
	    String oweClassHours = hash.get("欠班小时数");
	    vp.verifyAttendanceCalculationResult(staffNo, hoursAttendance, shouldBeWorkingHours, oweClassHours);
	}

	@Then("^I cancel calculate freeze btn$")
	public void i_cancel_calculate_freeze_btn() throws Exception {	
		vp.cancelCalculateFreezeBtn();
	}
	
	@Then("^I select Order date range$")
    public void i_select_Order_date_range(DataTable data) throws Exception {
        HashMap<String, String> hash = DataTableUtils.toHashMap(data);
        String startDate =  hash.get("开始日期");
        String endDate =  Utils.getDate(hash.get("结束日期 "));
        vp.selectOrderDateRange(startDate, endDate);
    }

    @Then("^I verify order information contains (.+)$")
    public void i_verify_order_information_contains(String info) throws Exception {
        vp.verifyOrderInformationContains(info);
    }
    
    @Then("^I serach hidden Elemen$")
    public void i_serach_hidden_Element() throws Exception {
        vp.optionOrder();
    }
    
    @Then("^visit baidu in a browser$")
    public void visit_baidu_in_a_browser() throws Exception {
        vp.visitBaiduInBrowser();
    }

    @Then("^I find infomation (.+)$")
    public void i_find_infomation(String content) throws Throwable {
        vp.findInfomation(content);
    }

}
