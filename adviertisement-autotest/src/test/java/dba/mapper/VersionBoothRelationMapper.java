package dba.mapper;

import dba.model.VersionBoothRelation;

import java.util.List;

public interface VersionBoothRelationMapper {


    /**
     * 根据boothCode获取与版本的关联信息
     * @param boothCode
     * @return
     */
    List<VersionBoothRelation> selectByBoothCode(String boothCode);

    /**
     * 删除关系表中无意义的记录
     *
     * @return
     */
    int deleteVersionBoothRelationNotInBooth();

}