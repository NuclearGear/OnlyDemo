#Author: only
@advertisement
@adbackend @template
Feature: 素材模板功能验证

Background:
    Given I login system 广告后台
    Then 广告后台登录成功
    And 广告后台点击菜单 配置中心>素材模板管理

@E-400
Scenario: E-400: 验证新建广告系统素材模板功能
  When 素材模板列表点击 新建素材模版按钮
  And 填写素材模板信息
  |  素材模版名称   |素材模版类型|素材内容格式|素材模版样式 |是否可点击|                     素材模版元素                  |
  |UI-自动化广告系统|  多图轮播  |    图片     | 信息流大图 |    是     | 图片,标题,副标题,视频,价格,收藏按钮,广告关闭时间|
  And 填写素材模板元素信息
  |图片宽度|图片高度|图片个数|图片大小|标题最少字数|标题最多字数|副标题最少字数|副标题最多字数|最大播放时长|是否支持健康金|
  |   780  |   150  |   2    |   200   |     1      |     10      |      1       |       10      |     60     |      是      |
  And 提交模板信息
  Then 验证提示为 请稍等, 已经新增成功, 正在帮您跳转!
  And 查询素材模板 UI-自动化
  Then 验证素材模板列表首行数据

@E-401
Scenario: E-401: 验证新建业务系统素材模板功能
  When 素材模板列表点击 新建素材模版按钮
  And 填写素材模板信息
    |   素材模版名称  |素材获取方式|获取内容 |是否传递渠道参数|是否允许替换素材属性|
    |UI-自动化业务系统|  业务系统  |商品SPUID|       否        |          否        |
  And 提交模板信息
  Then 验证提示为 请稍等, 已经新增成功, 正在帮您跳转!
  And 查询素材模板 UI-自动化
  Then 验证素材模板列表首行数据

@E-402
Scenario: E-402: 验证查询素材模板功能
  When 查询素材模板 UI-自动化业务系统
  Then 验证素材模板列表数据
  When 查询素材模板 UI-自动化广告系统
  Then 验证素材模板列表数据

@E-403
Scenario: E-403: 验证编辑业务系统替换素材模板功能
  When 查询素材模板 UI-自动化业务系统
  And 素材模板列表点击 编辑按钮
  And 填写素材模板信息
    |是否传递渠道参数|是否允许替换素材属性|                     素材模版元素                  |
    |       是        |          是         | 图片,标题,副标题,视频,价格,收藏按钮,广告关闭时间|
  And 填写素材模板元素信息
    |图片宽度|图片高度|图片个数|图片大小|标题最少字数|标题最多字数|副标题最少字数|副标题最多字数|最大播放时长|是否支持健康金|
    |   780  |   150  |   2    |   200   |     1      |     10      |      1       |       10      |     60     |      是      |
  And 提交模板信息
  Then 验证提示为 请稍等, 已经更新成功, 正在为您跳转!
  When 查询素材模板 UI-自动化业务系统
  And 验证素材模板列表首行数据

@E-404
Scenario: E-404: 验证编辑广告系统素材模板功能
  When 查询素材模板 UI-自动化广告系统
  And 素材模板列表点击 编辑按钮
  And 填写素材模板信息
    |素材模版类型|素材内容格式|素材模版样式|  素材模版元素  |
    |    原生     |    图文     |     无     | 图片,标题,副标题|
  And 填写素材模板元素信息
    |图片宽度|图片高度|图片个数|图片大小|
    |   120  |   120  |   1    |   200   |
  And 提交模板信息
  Then 验证提示为 请稍等, 已经更新成功, 正在为您跳转!
  When 查询素材模板 UI-自动化广告系统
  And 验证素材模板列表首行数据