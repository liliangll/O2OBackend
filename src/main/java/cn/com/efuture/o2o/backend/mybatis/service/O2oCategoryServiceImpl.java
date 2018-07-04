package cn.com.efuture.o2o.backend.mybatis.service;

import cn.com.efuture.o2o.backend.mybatis.entity.ChannelCategory;
import cn.com.efuture.o2o.backend.mybatis.entity.ChannelCategoryLog;
import cn.com.efuture.o2o.backend.mybatis.entity.Dept;
import cn.com.efuture.o2o.backend.mybatis.entity.O2oCategory;
import cn.com.efuture.o2o.backend.mybatis.mapper.O2oCategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class O2oCategoryServiceImpl {
    
    private final O2oCategoryMapper o2oCategoryMapper; 

    public O2oCategoryServiceImpl(O2oCategoryMapper o2oCategoryMapper) {
		super();
		this.o2oCategoryMapper = o2oCategoryMapper;
	}

	public List<O2oCategory> getCategoryList(Map<String, Object> map) {
        return o2oCategoryMapper.getCategoryList(map);
    }

    public O2oCategory getCategory(Map<String, Object> map) {
        return o2oCategoryMapper.getCategory(map);
    }

    public int getCategoryIdCount(O2oCategory o2oCategory) {
        return o2oCategoryMapper.getCategoryIdCount(o2oCategory);
    }

    public int getSeqNoMax(O2oCategory o2oCategory) {
        return o2oCategoryMapper.getSeqNoMax(o2oCategory);
    }

    public void insertCategory(O2oCategory o2oCategory,String userName) {
        ChannelCategory channelCategory = new ChannelCategory();
        ChannelCategoryLog channelCategoryLog = new ChannelCategoryLog();
        //生成新CategoryId
        String cid = String.format("%02d", o2oCategoryMapper.getCategoryIdCount(o2oCategory) + 1);

        //判断该上级类别下是否有子类别
        if (o2oCategoryMapper.getCategoryIdCount(o2oCategory) == 0) {
            o2oCategory.setSeqNo(1);
        } else {
            o2oCategory.setSeqNo(o2oCategoryMapper.getSeqNoMax(o2oCategory) + 1);
        }
        //判断是否为上级类别
        if (o2oCategory.getParentCategoryId().equals("0")) {
            //生成上级类别CategoryId
            o2oCategory.setCategoryId(cid);
            o2oCategory.setLevel(1);
        } else {
            //生成子类别CategoryId
            o2oCategory.setCategoryId(o2oCategory.getParentCategoryId() + cid);
            o2oCategory.setLevel(2);
        }
        //写入本地类别和渠道类别表
        channelCategory.setCategoryId(o2oCategory.getCategoryId());
        channelCategory.setIsSys(0);
        o2oCategoryMapper.insertCategory(o2oCategory);
        o2oCategoryMapper.insertChannelCategory(channelCategory);
        //写入类别日志
        channelCategoryLog.setOperator(userName);
        channelCategoryLog.setCategoryId(o2oCategory.getCategoryId());
        channelCategoryLog.setCategoryName(o2oCategory.getCategoryName());
        channelCategoryLog.setTaskType(1);
        channelCategoryLog.setProcessStatus(0);
        o2oCategoryMapper.insertChannelCategoryLog(channelCategoryLog);
    }

    public void updateCategory(O2oCategory o2oCategory,String userName) {
    	//执行修改
    	o2oCategoryMapper.updateCategory(o2oCategory);
        ChannelCategoryLog channelCategoryLog = new ChannelCategoryLog(); 
        //判断是否修改禁用状态
        if (o2oCategory.getFlag() !=null) {
        	channelCategoryLog.setTaskType(3);
        }else {
        //修改类别名称或排序时写入类别日志
          if (o2oCategory.getCategoryName() != null) {
        	channelCategoryLog.setTaskType(2);
        }else if (o2oCategory.getSeqNo() !=null) {
        	channelCategoryLog.setTaskType(4);
        } 
    }
        channelCategoryLog.setCategoryId(o2oCategory.getCategoryId());
        channelCategoryLog.setOperator(userName);
        channelCategoryLog.setProcessStatus(0);
        o2oCategoryMapper.insertChannelCategoryLog(channelCategoryLog);  
        
}
    //获取业务系统类别
    public Dept getDept(Map<String, Object> map) {
        return o2oCategoryMapper.getDept(map);
    }
    
    public List<Map<String, Object>> getChannelCategoryList(Map<String, Object> map) {
        return o2oCategoryMapper.getChannelCategoryList(map);
    }
    
    public List<Map<String, Object>> getCategoryListProduct(Map<String, Object> map) {
        return o2oCategoryMapper.getCategoryListProduct(map);
    }
}
