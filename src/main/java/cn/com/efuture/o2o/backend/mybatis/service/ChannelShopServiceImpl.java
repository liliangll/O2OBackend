package cn.com.efuture.o2o.backend.mybatis.service;

import cn.com.efuture.o2o.backend.mybatis.entity.*;
import cn.com.efuture.o2o.backend.mybatis.mapper.ChannelShopMapper;
import cn.com.efuture.o2o.backend.mybatis.mapper.O2oCategoryMapper;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ChannelShopServiceImpl {

    private final ChannelShopMapper channelShopMapper;

    private final O2oCategoryMapper o2oCategoryMapper;

    public ChannelShopServiceImpl(ChannelShopMapper channelShopMapper, O2oCategoryMapper o2oCategoryMapper) {
        this.channelShopMapper = channelShopMapper;
        this.o2oCategoryMapper = o2oCategoryMapper;
    }

    public List<ChannelShop> getChannelShopList(Map<String, Object> map) {
        return channelShopMapper.getChannelShopList(map);
    }

    public ChannelShop getChannelShop(Map<String, Object> map) {
        return channelShopMapper.getChannelShop(map);
    }

    @Transactional
    public void updateChannelShop(ChannelShop channelShop, String userName) {
        ChannelShopLog channelShopLog = new ChannelShopLog();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("channelId", channelShop.getChannelId());
        jsonObject.put("channelShopId", channelShop.getChannelShopId());
        ChannelShop cShop = channelShopMapper.getChannelShop(jsonObject);

        //更新店铺信息
        channelShopMapper.updateChannelShop(channelShop);
        //写入店铺操作日志表
        channelShopLog.setChannelId(channelShop.getChannelId());
        channelShopLog.setChannelShopId(channelShop.getChannelShopId());
        channelShopLog.setShopId(channelShop.getShopId());
        channelShopLog.setOperator(userName);

        if (channelShop.getIsOpen() != null) {
            //则为更新营业状态操作
            channelShopLog.setTaskType(1);//记录任务类型，1=开关店
            channelShopLog.setIsOpen(channelShop.getIsOpen());
            channelShopMapper.insertChannelShopLog(channelShopLog);
        } else if (channelShop.getNotice() != null) {
            //则为更新店铺公告操作
            if (cShop.getNotice() == null) {
                //新增店铺 第一次修改公告
                channelShopLog.setTaskType(3);  //记录任务类型，3=公告修改
                channelShopLog.setNotice(channelShop.getNotice());
                channelShopMapper.insertChannelShopLog(channelShopLog);
            } else if (cShop.getNotice() != null && !cShop.getNotice().equals(channelShop.getNotice())) {
                channelShopLog.setTaskType(3);  //记录任务类型，3=公告修改
                channelShopLog.setNotice(channelShop.getNotice());
                channelShopMapper.insertChannelShopLog(channelShopLog);
            }
            if (channelShop.getAddress() != null && cShop.getAddress() == null ||
                    channelShop.getServiceTel() != null && cShop.getServiceTel() == null ||
                    channelShop.getContactTel() != null && cShop.getContactTel() == null ||
                    channelShop.getPreOrder() != null && cShop.getPreOrder() == null) {
                //新增店铺 第一次修改基本信息
                channelShopLog.setTaskType(4);
                channelShopMapper.insertChannelShopLog(channelShopLog);
            } else if (channelShop.getAddress() != null && !cShop.getAddress().equals(channelShop.getAddress()) ||
                    channelShop.getServiceTel() != null && !cShop.getServiceTel().equals(channelShop.getServiceTel()) ||
                    channelShop.getContactTel() != null && !cShop.getContactTel().equals(channelShop.getContactTel()) ||
                    channelShop.getPreOrder() != null && !cShop.getPreOrder().equals(channelShop.getPreOrder())) {
                //修改门店基础信息
                channelShopLog.setTaskType(4);
                channelShopMapper.insertChannelShopLog(channelShopLog);
            }
        }
    }

    @Transactional
    public void updateChannelShopTime(ChannelShopTime channelShopTime, String userName) {
        //更新店铺营业时间
        channelShopMapper.updateChannelShopTime(channelShopTime);
        //写入店铺操作日志表
        ChannelShopLog channelShopLog = new ChannelShopLog();
        channelShopLog.setChannelId(channelShopTime.getChannelId());
        channelShopLog.setChannelShopId(channelShopTime.getChannelShopId());
        channelShopLog.setShopId(channelShopTime.getShopId());
        channelShopLog.setOperator(userName);
        if (channelShopTime.getOpenTime() != null && channelShopTime.getStartDate().equals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))) {
            //则为更新店铺公告操作
            channelShopLog.setTaskType(2); //记录任务类型，2=营业时间修改
            channelShopLog.setOpenTime(channelShopTime.getOpenTime());
            channelShopMapper.insertChannelShopLog(channelShopLog);
        }
    }

    public List<ChannelShopTime> getChannelShopTime(Map<String, Object> map) {
        return channelShopMapper.getChannelShopTime(map);
    }

    @Transactional
    public void insertChannelShop(ChannelShop channelShop, String userName) {
        channelShop.setStatus(1);
        channelShop.setIsOpen(0);
        channelShopMapper.insertChannelShop(channelShop);
        //插入渠道类别
        ChannelCategory channelCategory = new ChannelCategory();
        channelCategory.setIsSys(0);
        channelCategory.setChannelId(channelShop.getChannelId());
        channelCategory.setChannelShopId(channelShop.getChannelShopId());
        o2oCategoryMapper.insertChannelCategory(channelCategory);
        //插入渠道类别日志
        ChannelCategoryLog channelCategoryLog = new ChannelCategoryLog();
        channelCategoryLog.setOperator(userName);
        channelCategoryLog.setTaskType(1);
        channelCategoryLog.setChannelId(channelShop.getChannelId());
        channelCategoryLog.setChannelShopId(channelShop.getChannelShopId());
        o2oCategoryMapper.insertChannelCategoryLog(channelCategoryLog);
    }
/*
    public AbstractXlsView exportChannelShopList(final Map<String, Object> map) {
        // execl 表头数据
        Object[][] o = new Object[][]{{"渠道", "channelName", 0, null}, {"家乐福门店编码", "shopId", 0, null},
                {"渠道商店编码", "channelShopId", 0, null}, {"渠道商店名称", "channelShopName", 0, null},
                {"区域", "regionName", 0, null}, {"城市", "city", 0, null}, {"地址", "address", 0, null},
                {"营业状态", "isOpen", 0, null}, {"营业时间", "openTime", 0, null},
                {"联系电话", "contactTel", 0, null}, {"服务电话", "serviceTel", 0, null},
                {"公告", "notice", 0, null}, {"最后修改时间", "lastModifyTime", 0, Date.class}, {"最后同步时间", "syncGoodsTime", 0, Date.class}};

        return new CommonExportExcel(o, "渠道商店表", new ExportDataSource() {
            @Override
            public List<Map<String, Object>> getData(int pageSize, int page) {
                List<Map<String, Object>> resultList = new ArrayList<>();
                //设置分页信息
                PageHelper.startPage(page, pageSize);
                List<ChannelShop> channelShopList = channelShopMapper.getChannelShopList(map);
                for (ChannelShop channelShop : channelShopList) {
                    JSONObject jsonObject = JSONObject.parseObject(JSON.toJSON(channelShop).toString());
                    jsonObject.put("isOpen", (channelShop.getIsOpen() == 0 ? "暂停营业" : "正在营业"));
                    resultList.add(jsonObject);
                }
                return resultList;
            }
        });
    }
 */
}
