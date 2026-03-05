package org.json0368.tliassystem.service.impl;


import org.json0368.tliassystem.mapper.EmpLogMapper;
import org.json0368.tliassystem.pojo.EmpLog;
import org.json0368.tliassystem.service.EmpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpLogServiceImpl implements EmpLogService {

    @Autowired
    private EmpLogMapper empLogMapper;

    // 该方法需要在一个新的事务中执行，避免回滚时导致日志数据也被回滚
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void insertLog(EmpLog empLog) {
        empLogMapper.insert(empLog);
    }
}
