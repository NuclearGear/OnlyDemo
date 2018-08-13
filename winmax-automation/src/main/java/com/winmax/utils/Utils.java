package com.winmax.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.apache.commons.lang.RandomStringUtils;

import com.winmax.config.Const;

import cucumber.api.Scenario;

public class Utils {
	
	public static String getImportExcelFilePath(String fileName) {
		return System.getProperty("user.dir") + "\\src\\test\\resources\\" + fileName + ".xlsx"; 
	}
	
	public static String getExportExcelFilePath(String fileName) {
		return System.getProperty("user.dir") + Const.DOWNLOAD_FOLDER + fileName; 
	}
	
	public static String getDefaultFilePath(String fileName) {
		return System.getProperty("user.dir") + "\\src\\test\\resources\\" + fileName; 
	}
	
	public static void waitABit(long ms) {
		try {
			Thread.sleep(ms);
		} catch(InterruptedException e) {
		}
	}
	
	/**
	 * 
	 * @param dateStr： @today, @today+5, @today-5, @tomorrow
	 * @throws Exception 
	 */
	public static String getDate(String dateStr) throws Exception {
		if (dateStr==null) {
			return null;
		} else if (dateStr.equals("@today")) {
			return getTodayDate();
		} else if (dateStr.equals("@yesterday")) {
			return getYesterdayDate();
		} else if (dateStr.equals("@tomorrow")) {
			return getYesterdayDate();
		} else if (dateStr.contains("+") && dateStr.startsWith("@")){			
			return shiftDate(Integer.valueOf(dateStr.substring(dateStr.indexOf("+"))));
		} else if (dateStr.contains("-") && dateStr.startsWith("@")){			
			return shiftDate(Integer.valueOf(dateStr.substring(dateStr.indexOf("-"))));
		} else if (dateStr.equals("@nextWorkDay")){			
			return getNextWorkingDay();
		} else if (dateStr.equals("@tomorrow_workday")){
			return getNextDayofWorkingDay();
		} else if (dateStr.equals("@preWorkDay")){
			return getPreWorkingDay();
		} else {
			return dateStr;
		}		 
	}
	
	/**
	 * 计算2个日期之间相差的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public static String getDaysNo(String startDate, String endDate) throws Exception {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	     Calendar cal = Calendar.getInstance();    
	     cal.setTime(sdf.parse(startDate));    
	     long time1 = cal.getTimeInMillis();                 
	     cal.setTime(sdf.parse(endDate));    
	     long time2 = cal.getTimeInMillis();         
	     long between_days = (time2-time1)/(1000*3600*24);  
	            
	     return String.valueOf(between_days);     
	}
	
	/**
	 * 指定日期加天数得到新的日期
	 * @param date
	 * @param day
	 * @return
	 * @throws ParseException
	 */
	public static String addDate(String dateStr, int num) throws Exception {
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式
		 Date date = dateFormat.parse(dateStr); // 指定日期
		 //得到指定日期的毫秒数
		 long time = date.getTime();    
		// 要加上的天数转换成毫秒数
		 num = num*24*60*60*1000; 
		 time+= num; // 相加得到新的毫秒数
		 //return new Date(time); // 将毫秒数转换成日期
		 return (new SimpleDateFormat("yyyy-MM-dd").format(time));
	}
	
	/**
	 * 
	 * @return 2017-07-12
	 */
	public static String getTodayDate() {		
		return shiftDate(0);
	}

	public static String getYesterdayDate() {		
		return shiftDate(-1);
	}
	
	public static String getTomorrowDate() {
		return shiftDate(1);
	}
	
	/**
	 * 
	 * @param n: +3, -5
	 * @return
	 */
	public static String shiftDate(int n) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, n);
		Date time = cal.getTime();
		return (new SimpleDateFormat("yyyy-MM-dd").format(time));
	}

	public static String getRandString(int length) {
	    return RandomStringUtils.randomAlphabetic(length); 
	}
	
	public static String getRandNumeric(int n) {
        return RandomStringUtils.randomNumeric(n);
    }
	
	/**
	 * 生成随机数0- max， 不包括max
	 * @param max
	 * @return
	 */
	public static int getRandInt(int max) {
         Random ran = new Random();
         return ran.nextInt(max);
    }
	
	/**
	 * 生成随机数 min- max， 包括min， 不包括max
	 * @param max
	 * @return
	 */
	public static int getRandInt(int min, int max) {
         Random ran = new Random();
         return min + ran.nextInt(max);
    }
	
	/**
	 * 取n个不重复的数，这些数范围是在min 和max 之间
	 * @param min 指定范围最小值
	 * @param max 指定范围最大值
	 * @param n 随机数个数
	 * @return
	 */
	public static int[] getRandArray(int min, int max, int n) {
        int len = max-min+1;
        int[] result = new int[n];
        int count = 0;
        while(count<n) {
        	int num = (int) (Math.random()*(max-min))+min;
        	boolean flag = true;
        	for (int j=0; j<n; j++){
        		if (num==result[j]) {
        			flag = false;
        			break;
        		}
        	}
        	if (flag) {
        		result[count] = num;
        		count++;
        	}
        }
        return result;
   }
	
	
    /**
     * 使用用户格式提取字符串星期
     * @param strDate 星期
     * @param pattern 日期格式
     * @return getWeek(new Date())
     */
    public static String getWeek(Date date){
		String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if(week_index<0){
			week_index = 0;
		} 
		return weeks[week_index];
	}
    
    //@nextWorkDay
    //取到最近的一個工作日日期，如果今天是周六或者周日，则返回下周一 日期，如今天是周一至周五，则反回当天日期
    public static String getNextWorkingDay() throws Exception {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式
		Date date = dateFormat.parse(getDate("@today"));
    	Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK);
		String value = getDate("@today");
		if (week_index ==1) {  //周日
			value = getDate("@today+1");
		} else if (week_index ==7) {  //周六
			value = getDate("@today+2");
		} 
		return value;
    }
    
    //@preWorkDay
    //取到之前最近的一個工作日日期，如果今天是周六或者周日，则返回上周五 日期，如今天是周一至周五，则反回当天日期
    public static String getPreWorkingDay() throws Exception {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式
		Date date = dateFormat.parse(getDate("@today"));
    	Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK);
		String value = getDate("@today");
		if (week_index ==1) { //周日
			value = getDate("@today-2");
		} else if (week_index ==7) { //周六
			value = getDate("@today-1");
		} 
		return value;
    }
    
    //取到最近的一個工作日次日日期，如果今天是周五，周六或者周日，则返回下周一 日期，如今天是周一至周四，则反回第二天日期
    public static String getNextDayofWorkingDay() throws Exception {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式
		Date date = dateFormat.parse(getDate("@today"));
    	Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK);
		String value = getDate("@tomorrow");
		if (week_index ==1) {
			value = getDate("@today+1");
		} else if (week_index ==7) {
			value = getDate("@today+2");
		} else if (week_index ==6) {
			value = getDate("@today+3");
		} 
		return value;
    }
    
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }  
    
    //String prefix @random + prefix string ,e,g @random + E529
    public static String getRandStr(String prefix) {
    	if (prefix.contains("@random")) {
    	String randomStr = Utils.getRandString(5);
    		return prefix.substring(prefix.indexOf("+")+1).trim() + randomStr;
    	} else {
    		return prefix;
    	}
    	
    }
    
    public static int compare_date(String DATE1, String DATE2) { 
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                  return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
  
    public static void updateTestDataProperty(String key, String value) throws Exception {
    	PropertyUtil.updateProperties(System.getProperty("user.dir") + "\\src\\test\\resources\\property\\testData.properties", key, value);
	}
	   
    //以@@开头的变量需要从property文件取得值
    public static String getTestDataProperty(String key) throws Exception {
    	if (key.startsWith("@@"))  {
    		return PropertyUtil.getValueByKey(System.getProperty("user.dir") + "\\src\\test\\resources\\property\\testData.properties", key);
    	} else {
    		return key;
    	}
    }
    
	public String covertDateToFormat(String format, String value) throws ParseException {
		if (value.contains("/")) {                    //数据格式yyyy/mm/dd
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date date = sdf.parse(value);
			value = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
		} 
		return value;
	}
	
	//从feature文件的scenario取得该scenario标注的tag，该cae对应的test case id
	public static ArrayList<String> getTestCaseIDs(Scenario scenario) throws Exception {
		Object[] array = scenario.getSourceTagNames().toArray();
		ArrayList<String> list = new ArrayList<String>();
	    String regEx = "^@(E|e)-?[0-9]{1,8}$";
	    Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE); 
    	for (int i=0;i<array.length;i++) {
    		String tag = array[i].toString();
    		java.util.regex.Matcher matcher = pattern.matcher(tag);
    		if(matcher.matches()) {
    			tag = tag.replace("@e", "@E").replace("@", "");
    			if (!tag.contains("-")) {
    				list.add(tag.replace("E", "E-"));    				
    			} else {
    				list.add(tag);
    			}
    		}
    	}
		return list;		
	}	
	
	public static void getAppAppuim(){
		DesiredCapabilities des = new DesiredCapabilities();
		des.setCapability("platformName", "Android");// 平台名称
		des.setCapability("platformVersion", "4.3");// 手机操作系统版本
		des.setCapability("udid", "emulator-5554");// 连接的物理设备的唯一设备标识
		des.setCapability("deviceName", "S4");// 使用的手机类型或模拟器类型 UDI
		des.setCapability("appPackage", "com.sky.jisuanji");// App安装后的包名,注意与原来的CalcTest.apk不一样
		des.setCapability("appActivity", ".JisuanjizixieActivity");// app测试人员常常要获取activity，进行相关测试,后续会讲到
		des.setCapability("unicodeKeyboard", "True");// 支持中文输入
		des.setCapability("resetKeyboard", "True");// 支持中文输入
		des.setCapability("newCommandTimeout", "10");// 没有新命令时的超时时间设置
		des.setCapability("nosign", "True");// 跳过检查和对应用进行 debug 签名的步骤
	}
}
