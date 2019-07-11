#Author: only
@advertisement
@adaisle @anchorCommission
Feature: 主播商品分成功能验证

Background:
    Given I login system 推广通
    Then 验证推广通登录成功
    And 推广通点击菜单 主播管理>分成管理

@E-600
Scenario:  E-600: 验证编辑主播固定分成功能
    When 固定分成点击编辑按钮，输入99
    Then 验证分成修改成功
    And 固定分成点击编辑按钮，输入5.0
    Then 验证分成修改成功

@E-601
Scenario:  E-601: 验证编辑主播激励分成功能
    When 激励分成点击编辑按钮，输入99
    Then 验证分成修改成功
    And 激励分成点击编辑按钮，输入5.0
    Then 验证分成修改成功

	 
	 
    
    	
    
  

   
   
   
   
   
   
  	 
    
      
    
    
		
 	 	
 	
    
   
	 
	 
	 
 	

  
  
 	 



 

 
