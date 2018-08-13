@vacation_ByOnly
Feature: vacation related tests

Background:

 @E-999
 Scenario:  E-999:登录神州租车，查询订单信息
 Given I login system
 Then I serach hidden Elemen
 Then I select Order date range
  |开始日期     | 结束日期   |
  |2018-01-01 | @today   |
 Then I verify order information contains 还没租过车？	
 
 @E-888
 Scenario:  E-999: 打开百度，查找Jemeter
 Then visit baidu in a browser
 Then I find infomation jmeter教程
 
 @E-001
 Scenario:  E-001: 登录Winmax
	Given I login winmax
 	When I click on menu 我的广告>推广计划 from selfplatform page
 
  @E-002
 Scenario:  E-002:test
	Given I login winmax
 	When I click testout   
	 
	 
    
    	
    
  

   
   
   
   
   
   
  	 
    
      
    
    
		
 	 	
 	
    
   
	 
	 
	 
 	

  
  
 	 



 

 
