#Author: only
@advertisement
@adaisle @activity
Feature: 推广活动功能验证

Background:
    Given I login system 推广通
    Then 验证推广通登录成功
    And 推广通点击菜单 商家管理>推广活动
@E-001
Scenario:  E-001: 登录系统

@E-100
Scenario:  E-100: 验证添加推广活动功能
    When 推广活动列表点击 添加推广活动按钮
    And 填写推广活动信息
        |活动名称 |报名开始日期|报名结束日期|活动开始日期|活动结束日期|促销内容 |佣金门槛|
        |UI-自动化|@today+1    |@today+1    |@today+2   |@today+2    |UI-自动化|    1   |
    And 提交活动表单点击确认按钮
    Then 验证提示为 推广活动新增成功
    And  验证新建活动后列表状态为 报名未开始

@E-101 @E-102
Scenario Template:  E-101:验证编辑推广活动状态为报名未开始 E-102:验证编辑推广活动状态为报名中
    When 推广活动列表选择活动名称 UI-自动化
    And 推广活动列表选择<活动名称>点击 编辑按钮
    And 编辑推广活动信息
        | 活动名称 | 报名开始日期 | 报名结束日期 | 活动开始日期 |  活动结束日期 | 促销内容 |佣金门槛|
        |<活动名称>|<报名开始日期>|<报名结束日期>|<活动开始日期>|<活动结束日期>|<活动名称>|   10   |
    And 提交活动表单点击确认按钮
    Then 验证提示为 推广活动编辑成功
    And  验证编辑活动后列表状态为 <状态>
Examples:
| 活动名称 |报名开始日期|报名结束日期|活动开始日期|活动结束日期|    状态   |
| UI-自动化|@today+1    |@today+1    |@today+2   |@today+2    | 报名未开始|
| UI-自动化|@today      |@today      |@today+1   |@today+1    |   报名中  |

@E-103 @E-104
Scenario:  E-103:验证添加活动优惠卷功能 E-104:验证优惠卷校验功能
  When 推广活动列表选择UI-自动化点击 编辑按钮
  And 编辑推广活动信息优惠卷ID11498
  And 提交活动表单点击确认按钮
  Then 验证提示为 已经存在相同的优惠券活动
  And 编辑推广活动信息优惠卷ID999999
  And 提交活动表单点击确认按钮
  Then 验证提示为 无效的优惠卷

@E-105 @E-106 @E-107
Scenario:  E-105:验证暂停活动功能 E-106:验证恢复活动功能 E-107:验证终止活动功能
    When 推广活动列表选择活动名称 UI-自动化
    And 推广活动列表点击 终 止按钮
    Then 验证活动列表状态为 已终止
    And 推广活动列表点击 暂 停按钮
    Then 验证提示为 只允许暂停活动进行中的活动
    And 推广活动列表点击 恢 复按钮
    Then 验证提示为 活动已经终止

@E-108
Scenario:  E-108: 验证复制SPUID功能
    When 推广活动列表点击 复制SPUID按钮
    Then 验证提示为 复制Spuid成功


	 
	 
    
    	
    
  

   
   
   
   
   
   
  	 
    
      
    
    
		
 	 	
 	
    
   
	 
	 
	 
 	

  
  
 	 



 

 
