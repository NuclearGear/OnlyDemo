package dba.mapper;

import dba.model.BoothAppRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoothAppRelationMapper {

    /**
     * 根据boohtCode获取所有appVersion的设定信息
     */
    List<BoothAppRelation> selectByBoothCode(String boothCode);



    /**
     * 根据boohtCode，srcId,以及platform,获取所有appVersion的设定信息
     */
    List<BoothAppRelation> selectByBoothCodeSrcPlatform(@Param("boothCode") String boothCode,
                                                        @Param("srcId") int srcId,
                                                        @Param("platform") int platform);

}