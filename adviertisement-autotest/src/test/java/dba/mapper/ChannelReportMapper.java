package dba.mapper;

import dba.model.ChannelReport;

public interface ChannelReportMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ChannelReport record);

    int insertSelective(ChannelReport record);

    ChannelReport selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ChannelReport record);

    int updateByPrimaryKey(ChannelReport record);



    /**
     * 根据名称删除相应的记录
     * @param boothName
     * @return
     */
    int deleteLikeBoothName(String boothName);
}