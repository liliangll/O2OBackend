package cn.com.efuture.o2o.backend.util.export;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CommonExportExcel extends AbstractXlsxStreamingView {
    private static final int CHAR_WIDTH = 256;
    private static final int PAGE_SIZE = 5000;
    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 表体数据
     */
    private ExportDataSource datasource = null;

    /*
     * cell结构 {"表头列名称","列的key","单元格宽度","单元格数据类型"}
     */
    private Object[][] cellStructs = null;

    // 导出文件名
    private String name = "";

    public CommonExportExcel(Object[][] cellStructs, String name, ExportDataSource datasource) {
        this.cellStructs = cellStructs;
        this.name = name;
        this.datasource = datasource;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        OutputStream outputStream = null;
        try {
            // 设置下载时客户端Excel的名称
            String filename = map.get("excel") == null ? name + ".xlsx" : name + "." + map.get("excel");
            //处理中文表名称
            encodingName(request, response, filename);
            outputStream = response.getOutputStream();
            this.buildExcelDocumentStream(outputStream);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    private void encodingName(HttpServletRequest request, HttpServletResponse response, String filename) throws UnsupportedEncodingException {
        String agent = request.getHeader("User-Agent");
        if (null != agent) {
            agent = agent.toLowerCase();
            // 火狐浏览器文件中文名处理
            if (agent.contains("firefox")) {
                response.setHeader("content-disposition", String.format("attachment;filename*=utf-8'zh_cn'%s", URLEncoder.encode(filename, "utf-8")));
            } else {
                response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            }
        }
    }

    private void buildExcelDocumentStream(OutputStream outputStream) {
        /*
         * 新建工作薄
		 */
        Workbook wb = new SXSSFWorkbook();

        /*
         * 新建表格第一页
		 */
        Sheet sheet = wb.createSheet();

        /*
         * excel默认列宽度
		 */
        int columnWidths[] = new int[cellStructs.length];

        /*
         * 组织excel文件头部 设置Excel表的第一行即表头
		 */
        int i = 0;
        Row row = sheet.createRow(0);
        /*
         * 表头样式
		 */
        CellStyle headCellStyle = getHeadCellStyle(wb);
        /*
         * 表头数据处理
         */
        for (Object[] cellStruct : cellStructs) {
            i++;
            int ii = 1;
            Object ksk;
            if (cellStruct.length < ii || (ksk = cellStruct[ii - 1]) == null) {
                throw new RuntimeException("第" + i + "列未设置头部名称");
            }
            /*
             * 表头数据
			 */
            Cell headCell = row.createCell(i - 1);
            headCell.setCellValue(String.valueOf(ksk));
            headCell.setCellStyle(headCellStyle);// 设置表头样式
            // get max width
            columnWidths[i - 1] = Math.max(columnWidths[i - 1], ksk.toString().getBytes().length);
        }
        
        /*
		 * 表体样式
		 */
        CellStyle bodyCellStyle = getBodyCellStyle(wb);
        /*
		 * 组织excel表体数据
		 */
        int j = 0;
        List<Map<String, Object>> data = null;
        int page = 1;
        while (true) {
            if (data != null) {
                data.clear();
            }
            data = this.datasource.getData(PAGE_SIZE, page++);
            if (data == null || data.size() == 0) {
                break;
            }
            for (Map<String, Object> map : data) {
                /*
				 * 行数标志
				 */
                j++;
                Row bodyRow = sheet.createRow(j);// 创建表体数据行

                i = 0;

                for (Object[] cellStruct : cellStructs) {
                    /*
					 * 列数标志
					 */
                    i++;
                    /*
					 * 所取单元格列数
					 */
                    int ii = 2;
                    /*
					 * key
					 */
                    Object ksk;
                    if (cellStruct.length < ii || (ksk = cellStruct[ii - 1]) == null) {
                        throw new RuntimeException("第" + i + "列未设置data数据集key键名");
                    }
                    /*
					 * value
                    */
                    Object value = map.get(ksk.toString());
                    if (value == null) {
                        value = "";
//                        throw new RuntimeException("第" + j + "行" + i + "列数据为空（请提供默认值）");
                    }
                    // get max width
                    columnWidths[i - 1] = Math.max(columnWidths[i - 1], value.toString().getBytes().length);
                    // 表格列类型(default string)
                    Object columnType = cellStruct[3];
                    columnType = columnType == null ? String.class : columnType;

                    /*
                     * 单元格数据
                     */
                    Cell cellData = bodyRow.createCell(i - 1);
                    cellData.setCellStyle(bodyCellStyle);
                    cellData.setCellType(CellType.STRING);
                    if (columnType == String.class) {
                        cellData.setCellValue(value.toString());
                    } else if (columnType == Date.class) {
                        if (!StringUtils.isEmpty(value)) {
                            cellData.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value));
                        } else {
                            cellData.setCellValue(value.toString());
                        }
                    } else {
                        throw new RuntimeException("unknown columnType");
                    }
                }
            }
        }
        /*
		 * 设置表格单无格宽度
		 */
        i = 0;
        for (int columnWidth : columnWidths) {
            Object temp = cellStructs[i++][2];
            int userWidth = 0;
            if (temp != null) {
                userWidth = Integer.parseInt(temp.toString());
            }
            if (userWidth != 0) {
                columnWidth = userWidth;
            }
            // 256为一个字符的宽度(max show 50 chars)
            sheet.setColumnWidth(i - 1, (CHAR_WIDTH * Math.min(columnWidth, 50)));
        }

        /*
		 * 构造输出流
		 */
        try {
            wb.write(outputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private CellStyle getBodyCellStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10); //设置字体大小
        style.setFont(font);
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /*
     * 表头样式
     */
    private CellStyle getHeadCellStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12); //设置字体大小
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }
}
