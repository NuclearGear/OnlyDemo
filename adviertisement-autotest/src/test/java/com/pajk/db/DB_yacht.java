package com.pajk.db;

import com.pajk.utils.DBhandle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Array;
import java.util.*;

public class DB_yacht {
    private static final Logger logger = LogManager.getLogger(DB_yacht.class);
    DBhandle yacht = new DBhandle("jdbc:mysql://testdb-m1.db.pajkdc.com:3306/","yacht","yacht","yacht");

/*    public List selectOwnerByName(String name) throws Exception {
        List<Map> ownerList = yacht.query("SELECT id from Owner WHERE Name LIKE'"+name+"%'");
        List<String> ownerIdList = new ArrayList<String>();
        for(Map<String,String> map :ownerList){
            for(String value:map.values()){
                ownerIdList.add(value);
            }
        }
        return ownerIdList;
    }*/
    public List querySql(String sql) throws Exception {
        List result = yacht.query(sql);
        return result;
    }

    public List selectOwnerLikeName(String name) throws Exception {
        String querySql = "SELECT id from Owner WHERE Name LIKE'"+name+"%'";
        List<Map> ownerIdList = yacht.query(querySql);
        return ownerIdList;
    }

    public List<String> selectAdIdByName(String adName) throws Exception {
        String querySql = "SELECT id from ad ad where system = 0 and ad_name = '"+adName+"'";
        List<String> adIdList = yacht.query(querySql);
        return adIdList;
    }

    public List<String> selectPlanIdByName(String planName) throws Exception {
        String querySql = "SELECT id from plan where type = 0 and name = '"+planName+"'";
        List<String> planIdList = yacht.query(querySql);
        return planIdList;
    }

    public List<String> selectTemplateIdByName(String templateName) throws Exception {
        String querySql = "SELECT id from material_template where name = '"+templateName+"'";
        List<String> templateIdList = yacht.query(querySql);
        return templateIdList;
    }

    public List<String> selectBoothIdByCode(String boothCode) throws Exception {
        String querySql = "SELECT id from booth where booth_code = '"+boothCode+"'";
        List<String> boothIdList = yacht.query(querySql);
        return boothIdList;
    }

    public void deleteOwnerLikeName(String name) throws Exception {
        List<String> ownerIdList =selectOwnerLikeName(name);
        if(!ownerIdList.isEmpty()) {
            logger.info("数据库清理广告主:" +name+ownerIdList);
            for(String id : ownerIdList){
                yacht.delete("DELETE from owner WHERE id ='" + id + "'");
                yacht.delete("DELETE from advertiser_income WHERE advertiser_id ='" + id + "'");
                yacht.delete("DELETE from owner_booth_permission WHERE owner_id ='" + id + "'");
            }
        }
    }

    public void deleteAdByName(String name) throws Exception {
        List<String> adIdList =selectAdIdByName(name);
        if(!adIdList.isEmpty()) {
            logger.info("数据库清理广告活动:" +name+adIdList);
            for(String id : adIdList){
                yacht.delete("DELETE from ad WHERE id ='" + id + "'");
            }
        }
    }

    public void deletePlanByName(String name) throws Exception {
        List<String> planIdList =selectPlanIdByName(name);
        if(!planIdList.isEmpty()) {
            logger.info("数据库清理广告投放:" +name+planIdList);
            for(String id : planIdList){
                yacht.delete("DELETE from plan WHERE id ='" + id + "'");
                yacht.delete("DELETE from plan_booth_relation WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from plan_date WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from material WHERE id in(SELECT material_id from plan_material_relation WHERE plan_id ='"+ id +"')");
                yacht.delete("DELETE from plan_material_relation WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from targetting_keywords WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from targetting_location WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from targetting_platform WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from targetting_time WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from frequency_control WHERE plan_id ='" + id + "'");
            }
        }
    }

    public void deletePlanById(String planId) throws Exception {
        if(planId.length()!=0) {
            logger.info("数据库清理广告投放:" +planId);
            List<String> planIdList=Arrays.asList(planId.split(","));
            for(String id : planIdList){
                yacht.delete("DELETE from plan WHERE id ='" + id + "'");
                yacht.delete("DELETE from plan_booth_relation WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from plan_date WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from material WHERE id in(SELECT material_id from plan_material_relation WHERE plan_id ='"+ id +"')");
                yacht.delete("DELETE from plan_material_relation WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from targetting_keywords WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from targetting_location WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from targetting_platform WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from targetting_time WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from frequency_control WHERE plan_id ='" + id + "'");
            }
        }
    }

    public void deletePlanById(List<String> planIdList) throws Exception {
        if(!planIdList.isEmpty()) {
            logger.info("数据库清理广告投放:" +planIdList);
            for(String id : planIdList){
                yacht.delete("DELETE from plan WHERE id ='" + id + "'");
                yacht.delete("DELETE from plan_booth_relation WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from plan_date WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from material WHERE id in(SELECT material_id from plan_material_relation WHERE plan_id ='"+ id +"')");
                yacht.delete("DELETE from plan_material_relation WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from targetting_keywords WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from targetting_location WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from targetting_platform WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from targetting_time WHERE plan_id ='" + id + "'");
                yacht.delete("DELETE from frequency_control WHERE plan_id ='" + id + "'");
            }
        }
    }

    public void deleteTemplateByName(String name) throws Exception {
        List<String> templateIdList =selectTemplateIdByName(name);
        if(!templateIdList.isEmpty()) {
            logger.info("数据库清理素材模板:" + name+templateIdList);
            for(String id : templateIdList){
                yacht.delete("DELETE from material_template WHERE id ='" + id + "'");
                yacht.delete("DELETE from template_element WHERE template_id ='" + id + "'");
            }
        }
    }

    public void deleteBoothByCode(String code) throws Exception {
        List<String> boothCodeList = yacht.query("SELECT booth_code from booth WHERE booth_code ='" + code + "'");
        if(!boothCodeList.isEmpty()){
            logger.info("数据库清理广告位:" +code);
            for(String boothCode:boothCodeList){
                yacht.delete("DELETE from booth WHERE booth_code ='" + boothCode + "'");
                yacht.delete("DELETE from booth_template_relation WHERE booth_code ='" + boothCode + "'");
                yacht.delete("DELETE from booth_app_relation WHERE booth_code ='" + boothCode + "'");
                yacht.delete("DELETE from booth_link_relation WHERE booth_code ='" + boothCode + "'");
                yacht.delete("DELETE from booth_platform_relation WHERE booth_code ='" + boothCode + "'");
                yacht.delete("DELETE from booth_targetting_list WHERE booth_code ='" + boothCode + "'");
            }
        }
    }


    public List<String> selectLivePlanIdByName(String planName) throws Exception {
        String querySql =
            "SELECT plan.id FROM ad LEFT JOIN plan ON ad.id = plan.ad_id WHERE ad.ad_name = '"+planName+"' AND plan.type = '1'";
        List<String> liveplanIdList = yacht.query(querySql);
/*        List livePlanLists = yacht.query(querySql);
        List<String> planIdList = new ArrayList();
        if (!livePlanLists.isEmpty()) {
            for (int i = 0; i < livePlanLists.size(); i++) {
                Map<String, String> map = (HashMap<String, String>) livePlanLists.get(i);
                for ( String value : map.values()) {
                    planIdList.add(value);
                }
            }
        }*/
        return liveplanIdList;
    }

    public void deleteLivePlanById(List<String> planId) throws Exception {
        if(!planId.isEmpty()) {
            logger.info("数据库清理直播推广:"+planId);
            for (String Id : planId) {
                yacht.delete("DELETE plan,plan_item,plan_date from plan " +
                        "LEFT JOIN plan_item ON plan.id = plan_item.plan_id " +
                        "LEFT JOIN plan_date ON plan.id = plan_date.plan_id " +
                        "WHERE plan.id =" + Id);
            }
        }
    }

    public void deleteLivePlanByName(String planName) throws Exception {
        List<String> planId = selectLivePlanIdByName(planName);
        if(!planId.isEmpty()) {
            logger.info("数据库清理直播推广:"+planId);
            for (String Id : planId) {
                yacht.delete("DELETE plan,plan_item,plan_date from plan " +
                        "LEFT JOIN plan_item ON plan.id = plan_item.plan_id " +
                        "LEFT JOIN plan_date ON plan.id = plan_date.plan_id " +
                        "WHERE plan.id =" + Id);
            }
        }
    }

/*    public void deleteTrafficPlanById(List<String> planId) throws Exception {
        for (String Id : planId) {
            yacht.delete("");
        }
    }*/

    public List selectActivityByName(String activityName) throws Exception {
        List activityList = yacht.query("select id from tgt_activity WHERE activity_name ='"+activityName+"'");
        return activityList;
    }

    //不实时生效，10分钟任务刷新
    public void deleteActivityByName(String activityName) throws Exception {
        List<String> activityId = selectActivityByName(activityName);
        if(!activityId.isEmpty()) {
            logger.info("数据库清理推广活动:"+activityName+activityId);
            for(String id :activityId){
                yacht.delete("DELETE from tgt_activity WHERE id ='"+id+"'");
                yacht.delete("DELETE from tgt_activity_anchor_relation WHERE activity_id ='"+id+"'");
                yacht.delete("DELETE from tgt_activity_category_relation WHERE activity_id ='"+id+"'");
                yacht.delete("DELETE from tgt_activity_item WHERE activity_id ='"+id+"'");
                yacht.delete("DELETE from tgt_activity_item_config WHERE activity_id ='"+id+"'");
                yacht.delete("DELETE from tgt_activity_merchant_relation WHERE activity_id ='"+id+"'");
            }
        }
    }

    //不实时生效，10分钟任务刷新
    public void deleteActivityById(List<String> activityIdList) throws Exception {
        if(!activityIdList.isEmpty()) {
            logger.info("数据库清理推广活动:"+activityIdList);
            for(String id :activityIdList){
                yacht.delete("DELETE from tgt_activity WHERE id ='"+id+"'");
                yacht.delete("DELETE from tgt_activity_anchor_relation WHERE activity_id ='"+id+"'");
                yacht.delete("DELETE from tgt_activity_category_relation WHERE activity_id ='"+id+"'");
                yacht.delete("DELETE from tgt_activity_item WHERE activity_id ='"+id+"'");
                yacht.delete("DELETE from tgt_activity_item_config WHERE activity_id ='"+id+"'");
                yacht.delete("DELETE from tgt_activity_merchant_relation WHERE activity_id ='"+id+"'");
            }
        }
    }

    public void deleteAnchorByName(String anchorName) throws Exception {
        logger.info("数据库清理主播数据:"+anchorName);
        yacht.delete("DELETE from anchor_account WHERE user_name ='"+anchorName+"'");
        yacht.delete("DELETE from anchor_settings WHERE user_name ='"+anchorName+"'");

    }

    public void deleteAnchorById(String id) throws Exception {
        logger.info("数据库清理主播数据:"+id);
        yacht.delete("DELETE from anchor_account WHERE user_id ='"+id+"'");
        yacht.delete("DELETE from anchor_selected_item WHERE user_id ='"+id+"'");
        yacht.delete("DELETE from anchor_settings WHERE user_id ='"+id+"'");

    }

    public void deleteMerchantById(String id) throws Exception {
        logger.info("数据库清理商户:"+id);
        yacht.delete("DELETE from owner WHERE merchant_id ='"+id+"'");
        yacht.delete("DELETE from merchant_account_detail WHERE merchant_id ='"+id+"'");
    }


    public static void main(String[] args) throws Exception {
        DB_yacht yacht = new DB_yacht();
//        yacht.deleteLivePlanById(planId);
//        yacht.deleteActivityByName("UI-自动化");
        yacht.deleteAnchorByName("ONLY");
        yacht.deleteAnchorById("7320307");
//        yacht.deleteMerchantById("99990001");


        //List<String> ownerId = yacht.selectOwnerLikeName("UI-自动化");
        //List<String> adId = yacht.selectAdIdByName("广告活动_20190426154309");
        //List<String> planId = yacht.selectPlanIdByName("种草Banner");
        //List<String> templateId = yacht.selectTemplateIdByName("3.28素材");
        //System.out.println(templateId);
        //yacht.deleteOwnerByName("UI自动化公司");
        //yacht.deleteBoothByCode("TEST101");
        //yacht.deleteTemplateByName("渠道参数");

/*        yacht.deleteOwnerLikeName("UI-自动化");
        yacht.deleteAdByName("UI-自动化-运营活动");
        yacht.deleteAdByName("UI-自动化-商业活动");
        yacht.deletePlanByName("UI-自动化商业投放");
        yacht.deletePlanByName("UI-自动化运营投放");
        yacht.deleteTemplateByName("UI-自动化广告系统");
        yacht.deleteTemplateByName("UI-自动化业务系统");
        yacht.deleteBoothByCode("UI001");*/
/*        yacht.deletePlanByName("UI-自动化商业投放");
        yacht.deletePlanByName("UI-自动化运营投放");*/
//        yacht.deletePlanByName("健康头条信息流");

//        yacht.deletePlanById(yacht.querySql("SELECT p.id FROM plan p LEFT JOIN plan_date pd ON p.id = pd.plan_id WHERE pd.plan_id IS NULL AND p.type = 0"));
//        yacht.deleteActivityById(yacht.querySql("SELECT id FROM tgt_activity limit 30"));
//        yacht.deleteLivePlanByName("处方药测试汪");


    }
}