package dba.mapper;

import dba.model.SpSwitch;

public interface SpSwitchMapper {


    /**
     * 根据环境值，获取配置信息
     * @param env
     * @return
     */
    SpSwitch selectByEnv(String env);

}