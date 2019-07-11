#Author: only
@advertisement
@adaisle @merchantCommission
Feature: 商品底佣功能验证

Background:
    Given I login system 推广通
    Then 验证推广通登录成功
    And 推广通点击菜单 商家管理>商品底佣

@E-400
Scenario:  E-400:验证查询商品分类
    When 查询商品分类
        |  一级分类   |  二级分类 | 三级分类  |
        |旧商品类目_旧|普通商品_旧|普通商品_旧|
    Then 验证查询商品列表数据

@E-401
Scenario:  E-401:验证商品设置最低底佣校验
    When 查询商品分类
        |  一级分类   |  二级分类 | 三级分类  |
        |旧商品类目_旧|普通商品_旧|普通商品_旧|
    And 编辑修改最低佣金 50
    Then 验证查询商品列表最低佣金成功修改
    And 推广通点击菜单 推广计划
    And 查询推广计划
        |  商品类目 |
        |普通商品_旧|
    And 推广计划列表点击直播推广计划 编辑按钮
    And 填写推广计划信息
      |  开始日期  |  结束日期 |
      |  @today    |  @today   |
    And 填写推广计划佣金 49
    And 提交计划表单点击确认按钮
    Then 验证提示为 小于最低佣金

@E-402
Scenario:  E-402:验证修改商品最低底佣
  When 查询商品分类
    |  一级分类   |  二级分类 | 三级分类  |
    |旧商品类目_旧|普通商品_旧|普通商品_旧|
  And 编辑修改最低佣金 10
  Then 验证查询商品列表最低佣金成功修改
	 
    
    	
    
  

   
   
   
   
   
   
  	 
    
      
    
    
		
 	 	
 	
    
   
	 
	 
	 
 	

  
  
 	 



 

 
