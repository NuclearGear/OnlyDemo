#Author: only
@advertisement
@adaisle @anchorAccount
Feature: 主播账户功能验证

Background:
    Given I login system 推广通
    Then 验证推广通登录成功
    And 推广通点击菜单 主播管理>主播账户

@E-500
Scenario:  E-500:验证添加主播功能 E-501:验证添加重复主播校验
    When 数据库清理主播ID 7320307
    And 主播账户列表点击 添加主播按钮
    And 填写主播手机号 18555555555
    Then 验证提示为 成功
    And 主播账户列表点击 添加主播按钮
    And 填写主播手机号 18555555555
    Then 验证提示为 主播已经存在

@E-502
Scenario:  E-502: 验证查询主播功能
    When 查询主播账号
        |   手机号  |主播名称|
        |18555555555|  ONLY  |
    Then 验证主播列表查询数据

@E-503 @E-504
Scenario:  E-503:验证关闭主播账号功能 E-504:验证开启主播账号功能
    When 查询主播账号
        |   手机号  |主播名称|
        |18555555555|  ONLY  |
    And 主播账户列表点击 关闭账户按钮
    Then 验证提示为 成功
    When 查询主播账号
        |   手机号  |主播名称|
        |18555555555|  ONLY  |
    And 主播账户列表点击 开启账户按钮
    Then 验证提示包含 成功

@E-505
Scenario:  E-505: 验证编辑主播直播间单张优惠卷功能
    When 查询主播账号
        |   手机号  |主播名称|
        |18555555555|  ONLY  |
    And 主播账户列表点击 编辑按钮
    And 填写主播直播间信息
        |引导话术|优惠券|
        |UI自动化|10226 |
    Then 验证提示包含 成功

@E-506
Scenario:  E-506: 验证编辑主播直播间多张优惠卷功能
    When 查询主播账号
        |   手机号  |主播名称|
        |18555555555|  ONLY  |
    And 主播账户列表点击 编辑按钮
    And 填写主播直播间信息
        |引导话术|   优惠券  |
        |UI自动化|10226,11498|
    Then 验证提示包含 成功

@E-507
Scenario:  E-507: 验证编辑主播直播间校验无效优惠卷功能
    When 查询主播账号
        |   手机号  |主播名称|
        |18555555555|  ONLY  |
    And 主播账户列表点击 编辑按钮
    And 填写主播直播间信息
        |引导话术|优惠券|
        |UI自动化|123456|
    Then 验证提示为 优惠券【123456】校验失败

@E-508 @E-509
Scenario:  E-508:验证主播选品页选择商品功能 E-509:验证主播选品页取消商品功能
    When 查询主播账号
        |   手机号  |主播名称|
        |18555555555|  ONLY  |
    And 主播账户列表点击 选品设置按钮
    And 编辑主播页点击 添加商品按钮
    And 主播选品页点击 全部商品TAB
    And 主播选品页点击 选择商品按钮
    And 主播选品页点击 选择商品按钮
    And 主播选品页点击 选择商品按钮
    And 主播选品页点击 选择商品按钮
    And 获取选品页已选商品数据
    And 返回编辑主播页
    Then 验证主播已选商品列表
    And 编辑主播页点击 添加商品按钮
    And 主播选品页点击 全部商品TAB
    And 主播选品页点击 取消商品按钮
    And 获取选品页已选商品数据
    And 返回编辑主播页
    Then 验证主播已选商品列表

@E-510 @E-511
Scenario:  E-510:验证主播置顶商品功能 E-511:验证主播删除已选商品功能
    When 查询主播账号
        |   手机号  |主播名称|
        |18555555555|  ONLY  |
    And 主播账户列表点击 选品设置按钮
    And 获取主播已选商品数据
    And 置顶最后一位商品
    Then 验证主播已选商品列表顺序
    And 清空主播已选列表商品
    Then 验证主播已选商品列表成功清空商品

#寿险渠道
@E-512 @E-513
Scenario:  E-512:验证金管家主播选品页选择商品功能 E-513:验证金管家主播选品页取消商品功能
    When 查询主播账号
        |   手机号  |主播名称|
        |18555555555|  ONLY  |
    And 主播账户列表点击 选品设置按钮
    And 编辑主播页点击 金管家直播间TAB
    And 编辑主播页点击 添加商品按钮
    And 主播选品页点击 全部商品TAB
    And 主播选品页点击 选择商品按钮
    And 主播选品页点击 选择商品按钮
    And 主播选品页点击 选择商品按钮
    And 主播选品页点击 选择商品按钮
    And 获取选品页已选商品数据
    And 返回编辑主播页
    And 编辑主播页点击 金管家直播间TAB
    Then 验证主播已选商品列表
    And 编辑主播页点击 添加商品按钮
    And 主播选品页点击 全部商品TAB
    And 主播选品页点击 取消商品按钮
    And 获取选品页已选商品数据
    And 返回编辑主播页
    And 编辑主播页点击 金管家直播间TAB
    Then 验证主播已选商品列表

@E-514 @E-515
Scenario:  E-514:验证金管家主播置顶商品功能 E-515:验证金管家主播删除已选商品功能
    When 查询主播账号
        |   手机号  |主播名称|
        |18555555555|  ONLY  |
    And 主播账户列表点击 选品设置按钮
    And 编辑主播页点击 金管家直播间TAB
    And 获取主播已选商品数据
    And 置顶最后一位商品
    Then 验证主播已选商品列表顺序
    And 清空主播已选列表商品
    Then 验证主播已选商品列表成功清空商品

    
  

   
   
   
   
   
   
  	 
    
      
    
    
		
 	 	
 	
    
   
	 
	 
	 
 	

  
  
 	 



 

 
