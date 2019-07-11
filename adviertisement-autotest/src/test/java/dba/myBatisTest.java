package dba;

import com.pajk.test.spring.SpringContext;
import dba.mapper.*;
import dba.model.*;
import org.testng.annotations.Test;


public class myBatisTest {
    private static SpringContext springContext = new SpringContext("application-service.xml");
    private static OwnerMapper ownerMapper = (OwnerMapper) springContext.getBean("ownerMapper");
    private static AdMapper adMapper = (AdMapper) springContext.getBean("adMapper");

    @Test
    public void test1(){
        Owner owner = ownerMapper.selectByName("公司简称");
        System.out.println("test1:"+owner.getFullName());
    }
    @Test
    public void test2(){
        Ad ad = adMapper.selectByName("商城测试");
        System.out.println("test2:"+ad.getId());
    }
}
