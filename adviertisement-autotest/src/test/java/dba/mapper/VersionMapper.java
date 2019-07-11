package dba.mapper;

import dba.model.Version;

import java.util.List;

public interface VersionMapper {

    /**
     * 根据versionId获取version的基本信息
     * @param id
     * @return
     */
    Version selectById(Long id);


    /**
     * 根据sysId,返回其下version的基本信息
     */
     List<Version> selectBySysId(int sysId);


    /**
     * 返回所有的position
     */
    List<Version> getAllVersion();


    /**
     * 根据名称获取version的基本信息
     */
    Version selectByName(String name);


    /**
     * 根据名称删除相应的记录
     * @param name
     * @return
     */
    int deleteLikeName(String name);

}