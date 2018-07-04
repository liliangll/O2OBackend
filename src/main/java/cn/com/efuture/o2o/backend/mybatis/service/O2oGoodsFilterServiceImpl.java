package cn.com.efuture.o2o.backend.mybatis.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;

import cn.com.efuture.o2o.backend.mybatis.entity.O2oGoodsFilterSheetDetail;
import cn.com.efuture.o2o.backend.mybatis.entity.O2oGoodsFilterSheetHead;
import cn.com.efuture.o2o.backend.mybatis.mapper.O2oGoodsFilterMapper;
import cn.com.efuture.o2o.backend.util.PageCount;
import cn.com.efuture.o2o.backend.util.SerialHelper;
import cn.com.efuture.o2o.backend.util.export.CommonExportExcel;
import cn.com.efuture.o2o.backend.util.export.ExportDataSource;

@Service
public class O2oGoodsFilterServiceImpl implements PageCount{
	
	private final O2oGoodsFilterMapper o2oGoodsFilterMapper;
	
	private final SerialHelper serialHelper;
	
	public O2oGoodsFilterServiceImpl(O2oGoodsFilterMapper o2oGoodsFilterMapper,SerialHelper serialHelper) {
		this.serialHelper = serialHelper;
        this.o2oGoodsFilterMapper = o2oGoodsFilterMapper;
    }
	
    public List<Map<String, Object>> getO2oGoodsFilterList(Map<String, Object> map) {
        return o2oGoodsFilterMapper.getO2oGoodsFilterList(map);
    }

    public void insertO2oGoodsFilters(Map<String, Object> map) {
    	o2oGoodsFilterMapper.insertO2oGoodsFilters(map);
    }

    @Override
    public long getCount(Map<String, Object> map) {
        return o2oGoodsFilterMapper.getO2oGoodsFilterListCount(map);
    }
	
	public List<Map<String,Object>>getO2oGoodsFilterSheetHead(Map<String, Object> map){
		return o2oGoodsFilterMapper.getO2oGoodsFilterSheetHead(map);
	}
	
	public List<Map<String,Object>>getO2oGoodsFilterSheetDetail(Map<String, Object> map){
		return o2oGoodsFilterMapper.getO2oGoodsFilterSheetDetail(map);
	}
	
	public Map<String,Object> getSheetGoodsNameById(String itemCode){
		return o2oGoodsFilterMapper.getSheetGoodsNameById(itemCode);
	}
	
	public void insertO2oGoodsFilterSheet(JSONObject map, String userName) {		
		O2oGoodsFilterSheetHead o2oGoodsFilterSheetHead  = map.getObject("sheetHead", O2oGoodsFilterSheetHead.class);
		List<O2oGoodsFilterSheetDetail> list = JSON.parseArray(map.getString("sheetDetail"), O2oGoodsFilterSheetDetail.class);
		//生成单据编号
		String sheetId = serialHelper.getSheetSerial();
		o2oGoodsFilterSheetHead.setSheetId(sheetId);
		o2oGoodsFilterSheetHead.setCreator(userName);
		o2oGoodsFilterSheetHead.setFlag(0);
		//新建单据
		o2oGoodsFilterMapper.insertO2oGoodsFilterSheetHead(o2oGoodsFilterSheetHead);
		//新建明细
		int rowNo=1;
		for (O2oGoodsFilterSheetDetail o2oGoodsFilterSheetDetail : list) {
			if(o2oGoodsFilterSheetDetail.getShopId()==null||o2oGoodsFilterSheetDetail.getShopId().isEmpty()) {
				throw new RuntimeException("门店id不能为空！");
			}
			if(o2oGoodsFilterSheetDetail.getItemCode() == null||o2oGoodsFilterSheetDetail.getItemCode().isEmpty()) {
				throw new RuntimeException("商品编码不能为空！");
			}
			if(o2oGoodsFilterSheetDetail.getItemCode().length()<8||o2oGoodsFilterSheetDetail.getItemCode().length()>10) {
				throw new RuntimeException("新增失败，商品编码有误");
			}
			if(o2oGoodsFilterSheetDetail.getGoodsName() == null||o2oGoodsFilterSheetDetail.getGoodsName().isEmpty()) {
				throw new RuntimeException("商品名称不能为空！");
			}
			o2oGoodsFilterSheetDetail.setSheetId(sheetId);
			o2oGoodsFilterSheetDetail.setRowNo(rowNo);
			o2oGoodsFilterSheetDetail.setStatus(1);
			o2oGoodsFilterMapper.insertO2oGoodsFilterSheetDetail(o2oGoodsFilterSheetDetail);
			rowNo++;
		}
		
	}
	
    public void updateO2oGoodsFilterSheetDetail(JSONObject map, String userName) {
    	O2oGoodsFilterSheetHead o2oGoodsFilterSheetHead  = map.getObject("sheetHead", O2oGoodsFilterSheetHead.class);
		List<O2oGoodsFilterSheetDetail> list = JSON.parseArray(map.getString("sheetDetail"), O2oGoodsFilterSheetDetail.class);
		//获取rowNo最大值
		String sheetId = o2oGoodsFilterSheetHead.getSheetId();
		for (O2oGoodsFilterSheetDetail o2oGoodsFilterSheetDetail : list) {
			int animation = o2oGoodsFilterSheetDetail.getAnimation();
			o2oGoodsFilterSheetDetail.setSheetId(sheetId);
			//animation为1则新增明细
			if(animation==1) {
				if(o2oGoodsFilterSheetDetail.getShopId()==null||o2oGoodsFilterSheetDetail.getShopId().isEmpty()) {
					throw new RuntimeException("门店id不能为空！");
				}
				if(o2oGoodsFilterSheetDetail.getItemCode() == null||o2oGoodsFilterSheetDetail.getItemCode().isEmpty()) {
					throw new RuntimeException("商品编码不能为空！");
				}
				if(o2oGoodsFilterSheetDetail.getItemCode().length()<8||o2oGoodsFilterSheetDetail.getItemCode().length()>10) {
    				throw new RuntimeException("新增失败，商品编码有误");
    			}
				if(o2oGoodsFilterSheetDetail.getGoodsName() == null||o2oGoodsFilterSheetDetail.getGoodsName().isEmpty()) {
					throw new RuntimeException("商品名称不能为空！");
				}
				//生成新数据的rowNo
				int rowNo = o2oGoodsFilterMapper.getSheetRowNoMax(sheetId)+1;
				o2oGoodsFilterSheetDetail.setRowNo(rowNo);
				o2oGoodsFilterSheetDetail.setStatus(1);
				o2oGoodsFilterMapper.insertO2oGoodsFilterSheetDetail(o2oGoodsFilterSheetDetail);
			}else if(animation==2) {
				//animation为2则修改明细
				o2oGoodsFilterMapper.deleteO2oGoodsFilterSheetDetail(o2oGoodsFilterSheetDetail);
				int rowNo = o2oGoodsFilterMapper.getSheetRowNoMax(sheetId)+1;
				o2oGoodsFilterSheetDetail.setRowNo(rowNo);
				o2oGoodsFilterSheetDetail.setStatus(1);
				o2oGoodsFilterMapper.insertO2oGoodsFilterSheetDetail(o2oGoodsFilterSheetDetail);
			}else if(animation==3) {
				//animation为3则删除明细
				o2oGoodsFilterMapper.deleteO2oGoodsFilterSheetDetail(o2oGoodsFilterSheetDetail);
			}else if(animation==4) {
				//animation为4则修改商品明细状态
				if(o2oGoodsFilterSheetDetail.getStatus()==1) {
					o2oGoodsFilterSheetDetail.setStatusMsg("");
				}
				o2oGoodsFilterMapper.updateO2oGoodsFilterSheetDetail(o2oGoodsFilterSheetDetail);
			}
		}	
		o2oGoodsFilterMapper.updateO2oGoodsFilterSheetHead(o2oGoodsFilterSheetHead);
    } 
    
    
    public void updateO2oGoodsFilterSheetHead(O2oGoodsFilterSheetHead o2oGoodsFilterSheetHead) throws ParseException {  
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("sheetId", o2oGoodsFilterSheetHead.getSheetId());
    	map.put("status", 1);
    	if(o2oGoodsFilterSheetHead.getFlag()==100) {
    		o2oGoodsFilterSheetHead.setAuditTime(df.parse(df.format(new Date())));
    		List<Map<String, Object>> list = o2oGoodsFilterMapper.getO2oGoodsFilterSheetDetail(map);
    		for (Map<String, Object> maps : list) {
    			maps.put("status", 0);
    			o2oGoodsFilterMapper.insertO2oGoodsFilter(maps);
			}  
    	}
    	o2oGoodsFilterMapper.updateO2oGoodsFilterSheetHead(o2oGoodsFilterSheetHead);
    }
    
    
    public AbstractXlsView exportO2oGoodsFilterSheetDetail(final Map<String, Object> map) {
		 // execl 表头数据
       Object[][] o = new Object[][]{{"门店编码", "shopId", 0, null},
    	   {"商品编码", "itemCode", 0, null}, {"商品名称", "goodsName", 0, null},
           {"状态", "status", 0, null}, {"拒绝的原因记录", "statusMsg", 0, null}
           };
               return new CommonExportExcel(o, "商品提报单明细", new ExportDataSource() {
                   @Override
                   public List<Map<String, Object>> getData(int pageSize, int page) {
                       List<Map<String, Object>> resultList = new ArrayList<>();
                       //设置分页信息
                       PageHelper.startPage(page, pageSize);
                       
							List<Map<String,Object>> list = o2oGoodsFilterMapper.getO2oGoodsFilterSheetDetail(map);
							for (Map<String,Object> map : list) {
			                    JSONObject jsonObject = JSONObject.parseObject(JSON.toJSON(map).toString());
			                    resultList.add(jsonObject);
			                }
				
                       return resultList;
                   }
               });
	}
    
    public void importO2oGoodsFilterSheetDetail(List<O2oGoodsFilterSheetDetail> list,Map<String, Object> map) {
    	O2oGoodsFilterSheetHead o2oGoodsFilterSheetHead  = new O2oGoodsFilterSheetHead();
    	//查询用户下所有门店
		List<String> sbn = o2oGoodsFilterMapper.getShopIdByName((String) map.get("userName"));
		String sheetId =  map.get("sheetId").toString();
    	if(sheetId.equals("") ) {
    		String sId = serialHelper.getSheetSerial();
    		o2oGoodsFilterSheetHead.setSheetId(sId);
    		o2oGoodsFilterSheetHead.setCreator((String) map.get("userName"));
    		o2oGoodsFilterSheetHead.setFlag(0);
    		//新建单据
    		o2oGoodsFilterMapper.insertO2oGoodsFilterSheetHead(o2oGoodsFilterSheetHead);
    		//新建明细
    		int rowNo=1;
    		for (O2oGoodsFilterSheetDetail o2oGoodsFilterSheetDetail : list) {
    			o2oGoodsFilterSheetDetail.setSheetId(sId);
    			o2oGoodsFilterSheetDetail.setRowNo(rowNo);
    			if(o2oGoodsFilterSheetDetail.getItemCode().length()<8||o2oGoodsFilterSheetDetail.getItemCode().length()>10) {
    				throw new RuntimeException("导入失败，商品编码有误");
    			}
    			if(sbn.contains(o2oGoodsFilterSheetDetail.getShopId())) {
    				o2oGoodsFilterMapper.insertO2oGoodsFilterSheetDetail(o2oGoodsFilterSheetDetail);
        			rowNo++;
    			}else {
    				throw new RuntimeException("导入失败，门店编码有误");
    			}
    		}
    	}else {
    		for (O2oGoodsFilterSheetDetail o2oGoodsFilterSheetDetail : list) {
    			int rowNo = o2oGoodsFilterMapper.getSheetRowNoMax(sheetId)+1;
    			o2oGoodsFilterSheetDetail.setSheetId(sheetId);
    			o2oGoodsFilterSheetDetail.setRowNo(rowNo);
    			if(o2oGoodsFilterSheetDetail.getItemCode().length()<8||o2oGoodsFilterSheetDetail.getItemCode().length()>10) {
    				throw new RuntimeException("导入失败，商品编码有误");
    			}
    			if(sbn.contains(o2oGoodsFilterSheetDetail.getShopId())) {
    				o2oGoodsFilterMapper.insertO2oGoodsFilterSheetDetail(o2oGoodsFilterSheetDetail);
        			rowNo++;
    			}else {
    				throw new RuntimeException("导入失败，门店编码有误");
    			}
    		}
    	}
    	
    }
}
