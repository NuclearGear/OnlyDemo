#Author: only
@advertisement
@adaisle @merchantAccount
Feature: 商家账户功能验证

Background:
    Given I login system 推广通
    Then 验证推广通登录成功
    And 推广通点击菜单 商家管理>商户账户

@E-300 @E-301
Scenario:  E-300:验证添加商户功能 E-301:验证添加重复商户校验
    When 数据库清理商户ID 99990001
    And 商户账户列表点击 添加商户按钮
    And 填写商户信息
        | 商户ID |充值金额|
        |99990001|   100  |
    Then 验证提示为 成功
    And 商户账户列表点击 添加商户按钮
    And 填写商户信息
        | 商户ID |充值金额|
        |99990001|   100  |
    Then 验证提示为 商家已经存在

@E-302
Scenario:  E-302: 验证查询商户功能
    When 查询商户账户
        | 商户ID | 商户名称 |
        |99990001|testSeller|
    Then 验证商户列表查询数据

@E-303
Scenario:  E-303: 验证商户充值功能
    When 查询商户账户
        | 商户ID | 商户名称 |
        |99990001|testSeller|
    And 商户账户列表点击 充值按钮
    And 填写充值金额 99.99
	Then 验证提示为 成功
	And 商户账户列表点击 账户明细按钮
	Then 验证账户明细增加充值记录且充值金额计算准确

@E-304
Scenario:  E-304: 验证商户扣费功能
    When 查询商户账户
        | 商户ID | 商户名称 |
        |99990001|testSeller|
    And 商户账户列表点击 扣费按钮
    And 填写扣费信息
        |扣费金额|扣费原因 |
        | 99.99  |UI-自动化|
	Then 验证提示为 成功
	And 商户账户列表点击 账户明细按钮
	Then 验证账户明细增加扣费记录且扣费金额计算准确

@E-305
Scenario:  E-305: 验证商户实时余额与账户明细余额一致
    When 查询商户账户
        | 商户ID | 商户名称 |
        |99990001|testSeller|
    And 商户账户列表点击 账户明细按钮
    Then 验证账户明细账户余额与实时余额相同

@E-306 @E-307
Scenario:  E-306:验证商户关闭账号功能 E-307:验证商户开启账号功能
    When 查询商户账户
        | 商户ID | 商户名称 |
        |99990001|testSeller|
    And 商户账户列表点击 编辑按钮
    And 商户账户列表点击 关闭账号按钮
    And 商户账户列表点击 确 定按钮
    Then 验证提示为 成功
    And 商户账户列表点击 查 询按钮
    Then 验证商户列表账户状态为 无效
    And 商户账户列表点击 编辑按钮
    And 商户账户列表点击 开启账号按钮
    And 商户账户列表点击 确 定按钮
    Then 验证提示为 成功
    And 商户账户列表点击 查 询按钮
    Then 验证商户列表账户状态为 有效

    
  

   
   
   
   
   
   
  	 
    
      
    
    
		
 	 	
 	
    
   
	 
	 
	 
 	

  
  
 	 



 

 
