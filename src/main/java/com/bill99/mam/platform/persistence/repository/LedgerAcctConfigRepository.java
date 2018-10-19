package com.bill99.mam.platform.persistence.repository;

import com.bill99.mam.platform.common.enumeration.LedgerAcctTypeEnum;
import com.bill99.mam.platform.common.enumeration.message.RepositoryMsgEnum;
import com.bill99.mam.platform.common.util.WrappedBeanCopier;
import com.bill99.mam.platform.persistence.dao.LedgerAcctConfigMapper;
import com.bill99.mam.platform.persistence.dto.LedgerAcctConfigDto;
import com.bill99.mam.platform.persistence.entity.LedgerAcctConfig;
import com.bill99.mam.platform.persistence.entity.LedgerAcctConfigExample;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static com.bill99.mam.platform.common.util.Validator.checkNotNull;

@Component
public class LedgerAcctConfigRepository {
    @Autowired
    private LedgerAcctConfigMapper ledgerAcctConfigMapper;

    public LedgerAcctConfigDto save(LedgerAcctConfigDto ledgerAcctConfigDto){
        checkNotNull(ledgerAcctConfigDto,RepositoryMsgEnum.LEDGER_CONFIG_DTO_EMPTY);
        ledgerAcctConfigDto.setUpdateTime(new Date());
        ledgerAcctConfigDto.setCreateTime(new Date());
        LedgerAcctConfig ledgerAcctConfig = WrappedBeanCopier.copyProperties(ledgerAcctConfigDto,LedgerAcctConfig.class);
        ledgerAcctConfigMapper.insert(ledgerAcctConfig);
        return WrappedBeanCopier.copyProperties(ledgerAcctConfig,LedgerAcctConfigDto.class);
    }

    public LedgerAcctConfigDto update(LedgerAcctConfigDto ledgerAcctConfigDto){
        checkNotNull(ledgerAcctConfigDto,RepositoryMsgEnum.LEDGER_CONFIG_DTO_EMPTY);
        ledgerAcctConfigDto.setUpdateTime(new Date());
        LedgerAcctConfig ledgerAcctConfig = WrappedBeanCopier.copyProperties(ledgerAcctConfigDto,LedgerAcctConfig.class);
        ledgerAcctConfigMapper.updateByPrimaryKeySelective(ledgerAcctConfig);
        return WrappedBeanCopier.copyProperties(ledgerAcctConfig,LedgerAcctConfigDto.class);
    }

    public LedgerAcctConfigDto findByid(Long id){
        if (id == null) return null;
        LedgerAcctConfig config = ledgerAcctConfigMapper.selectByPrimaryKey(id);
        return WrappedBeanCopier.copyProperties(config,LedgerAcctConfigDto.class);
    }

    public LedgerAcctConfigDto qryWithDrawLedgerCongfig(){
        LedgerAcctConfigExample example = new LedgerAcctConfigExample();
        example.createCriteria().andLedgerAcctTypeEqualTo(LedgerAcctTypeEnum.WITH_DRAW.value());
        List<LedgerAcctConfig> configs = ledgerAcctConfigMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(configs)) return null;
        return WrappedBeanCopier.copyProperties(configs.get(0),LedgerAcctConfigDto.class);
    }

    public LedgerAcctConfigDto qryLedgerCongfigByGoodId(Long goodId){
        if (goodId == null) return null;
        LedgerAcctConfigExample example = new LedgerAcctConfigExample();
        example.createCriteria().andGoodsIdEqualTo(goodId);
        List<LedgerAcctConfig> configs = ledgerAcctConfigMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(configs)) return null;
        return WrappedBeanCopier.copyProperties(configs.get(0),LedgerAcctConfigDto.class);
    }

    public LedgerAcctConfigDto qryLedgerCongfigByLedgerAcctType (Integer ledgerAcctType){
        if (ledgerAcctType == null) return null;
    	LedgerAcctConfigExample example = new LedgerAcctConfigExample();
    	example.createCriteria().andLedgerAcctTypeEqualTo(ledgerAcctType);
    	List<LedgerAcctConfig> configs = ledgerAcctConfigMapper.selectByExample(example);
    	if (CollectionUtils.isEmpty(configs)) return null;
    	return WrappedBeanCopier.copyProperties(configs.get(0),LedgerAcctConfigDto.class);
    }

}
