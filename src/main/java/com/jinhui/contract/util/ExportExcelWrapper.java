package com.jinhui.contract.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Collection;

public class ExportExcelWrapper<T> extends ExportExcel<T> {
    /**
     * 导出带有头部标题行的Excel， 时间格式默认：yyyy-MM-dd hh:mm:ss
     * @param fileName
     * @param title
     * @param headers
     * @param dataset
     * @param response
     * @param version
     */
    public void exportExcel(String fileName, String title, String[] headers, Collection<T> dataset, HttpServletResponse response, String version) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
            if(StringUtils.isBlank(version) || EXCEL_FILE_2003.equals(version.trim())){
                exportExcel2003(title, headers, dataset, response.getOutputStream(), "yyyy-MM-dd hh:mm:ss");
            }else{
                exportExcel2007(title, headers, dataset, response.getOutputStream(), "yyyy-MM-dd hh:mm:ss");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
