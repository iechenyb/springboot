package com.cyb.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2017年11月8日
 */
public class CSVFileUtil {
	Log log = LogFactory.getLog(CSVFileUtil.class);

	public static final String ENCODE = "UTF-8";

	private FileInputStream fis = null;
	private InputStreamReader isw = null;
	private BufferedReader br = null;

	public CSVFileUtil(String filename) throws Exception {
		fis = new FileInputStream(filename);
		isw = new InputStreamReader(fis, ENCODE);
		br = new BufferedReader(isw);
	}

	// ==========以下是公开方法=============================
	/**
	 * 从CSV文件流中读取一个CSV行。
	 * 
	 * @throws Exception
	 */
	public String readLine() throws Exception {

		StringBuffer readLine = new StringBuffer();
		boolean bReadNext = true;
		int i = 938;
		while (i > 0) {
			//
			if (readLine.length() > 0) {
				readLine.append("\r\n");
			}
			// 一行
			String strReadLine = br.readLine();

			// readLine is Null
			if (strReadLine == null) {
				return null;
			}
			readLine.append(strReadLine);

			// 如果双引号是奇数的时候继续读取。考虑有换行的是情况。
			/*
			 * if (countChar(readLine.toString(), '"', 0) % 2 == 1) { bReadNext
			 * = true; } else { bReadNext = false; }
			 */
			i++;
		}
		return readLine.toString();
	}

	/**
	 * 把CSV文件的一行转换成字符串数组。指定数组长度，不够长度的部分设置为null。
	 */
	public static String[] fromCSVLine(String source, int size) {
		ArrayList tmpArray = fromCSVLinetoArray(source);
		if (size < tmpArray.size()) {
			size = tmpArray.size();
		}
		String[] rtnArray = new String[size];
		tmpArray.toArray(rtnArray);
		return rtnArray;
	}

	/**
	 * 把CSV文件的一行转换成字符串数组。不指定数组长度。
	 */
	public static ArrayList fromCSVLinetoArray(String source) {
		if (source == null || source.length() == 0) {
			return new ArrayList();
		}
		int currentPosition = 0;
		int maxPosition = source.length();
		int nextComma = 0;
		ArrayList rtnArray = new ArrayList();
		while (currentPosition < maxPosition) {
			nextComma = nextComma(source, currentPosition);
			rtnArray.add(nextToken(source, currentPosition, nextComma));
			currentPosition = nextComma + 1;
			if (currentPosition == maxPosition) {
				rtnArray.add("");
			}
		}
		return rtnArray;
	}

	/**
	 * 把字符串类型的数组转换成一个CSV行。（输出CSV文件的时候用）
	 */
	public static String toCSVLine(String[] strArray) {
		if (strArray == null) {
			return "";
		}
		StringBuffer cvsLine = new StringBuffer();
		for (int idx = 0; idx < strArray.length; idx++) {
			String item = addQuote(strArray[idx]);
			cvsLine.append(item);
			if (strArray.length - 1 != idx) {
				cvsLine.append(',');
			}
		}
		return cvsLine.toString();
	}

	/**
	 * 字符串类型的List转换成一个CSV行。（输出CSV文件的时候用）
	 */
	public static String toCSVLine(List strArrList) {
		if (strArrList == null) {
			return "";
		}
		String[] strArray = new String[strArrList.size()];
		for (int idx = 0; idx < strArrList.size(); idx++) {
			strArray[idx] = (String) strArrList.get(idx);
		}
		return toCSVLine(strArray);
	}

	// ==========以下是内部使用的方法=============================
	/**
	 * 计算指定文字的个数。
	 * 
	 * @param str
	 *            文字列
	 * @param c
	 *            文字
	 * @param start
	 *            开始位置
	 * @return 个数
	 */
	private int countChar(String str, char c, int start) {
		int i = 0;
		int index = str.indexOf(c, start);
		return index == -1 ? i : countChar(str, c, index + 1) + 1;
	}

	/**
	 * 查询下一个逗号的位置。
	 * 
	 * @param source
	 *            文字列
	 * @param st
	 *            检索开始位置
	 * @return 下一个逗号的位置。
	 */
	private static int nextComma(String source, int st) {
		int maxPosition = source.length();
		boolean inquote = false;
		while (st < maxPosition) {
			char ch = source.charAt(st);
			if (!inquote && ch == ',') {
				break;
			} else if ('"' == ch) {
				inquote = !inquote;
			}
			st++;
		}
		return st;
	}

	/**
	 * 取得下一个字符串
	 */
	private static String nextToken(String source, int st, int nextComma) {
		StringBuffer strb = new StringBuffer();
		int next = st;
		while (next < nextComma) {
			char ch = source.charAt(next++);
			if (ch == '"') {
				if ((st + 1 < next && next < nextComma) && (source.charAt(next) == '"')) {
					strb.append(ch);
					next++;
				}
			} else {
				strb.append(ch);
			}
		}
		return strb.toString();
	}

	/**
	 * 在字符串的外侧加双引号。如果该字符串的内部有双引号的话，把"转换成""。
	 * 
	 * @param item
	 *            字符串
	 * @return 处理过的字符串
	 */
	private static String addQuote(String item) {
		if (item == null || item.length() == 0) {
			return "\"\"";
		}
		StringBuffer sb = new StringBuffer();
		sb.append('"');
		for (int idx = 0; idx < item.length(); idx++) {
			char ch = item.charAt(idx);
			if ('"' == ch) {
				sb.append("\"\"");
			} else {
				sb.append(ch);
			}
		}
		sb.append('"');
		return sb.toString();
	}

	public static List<String> show(String filePath) {
		File csv = new File(filePath); // CSV文件路径
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(csv));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = "";
		String everyLine = "";
		try {
			List<String> allString = new ArrayList<>();
			while ((line = br.readLine()) != null) // 读取到的内容给line变量
			{
				everyLine = line;
				System.out.println(everyLine.replace("\"", ""));
				allString.add(everyLine);
			}
			System.out.println("csv表格中所有行数：" + allString.size());
			return allString;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		BufferedWriter bw = null;
		List<String> r1 = new ArrayList<String>();
		List<String> r2 = new ArrayList<String>();
		List<String> r3 = new ArrayList<String>();

		r1.add("name");
		r1.add("age");
		r2.add("chneyb");
		r2.add("220");
		r3.add("sff");
		r3.add("20");
		System.out.println(toCSVLine(r1));
		System.out.println(toCSVLine(r2));
		System.out.println(toCSVLine(r3));
		writeCSV("d://data/tmp.csv",toCSVLine(r2));
	}

	public static void genData() {
		List<Map<String, String>> exportData = new ArrayList<Map<String, String>>();
		Map<String, String> row = null;
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("1", "第一列");
		map.put("2", "第二列");
		map.put("3", "第三列");
		map.put("4", "第四列");

		for (int i = 0; i < 100; i++) {
			row = new LinkedHashMap<String, String>();
			row.put("1", "11" + i);
			row.put("2", "12" + i);
			row.put("3", "13" + i);
			row.put("4", "14" + i);
			exportData.add(row);
		}
		createCSVFile(exportData, map, "d:/data", "mycsv");
	}

	/**
	 * 
	 * @param exportData
	 *            需要导出的数据
	 * @param rowMapper
	 *            行名称
	 * @param outPutPath
	 *            导出的路径
	 * @param filename
	 *            文件名
	 * @return csv文件
	 */
	public static File createCSVFile(List exportData, LinkedHashMap rowMapper, String outPutPath, String filename) {

		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			// 创建文件
			csvFile = new File(outPutPath + filename + ".csv");
			// csvFile.getParentFile().mkdir();
			File parent = csvFile.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			csvFile.createNewFile();

			// GB2312使正确读取分隔符","
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"),
					1024);
			// 写入文件头部
			for (Iterator propertyIterator = rowMapper.entrySet().iterator(); propertyIterator.hasNext();) {
				java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
				csvFileOutputStream.write("\"" + propertyEntry.getValue().toString() + "\"");
				if (propertyIterator.hasNext()) {
					csvFileOutputStream.write(",");
				}
			}
			csvFileOutputStream.newLine();

			// 写入文件内容
			for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
				// Object row = (Object) iterator.next();
				LinkedHashMap row = (LinkedHashMap) iterator.next();
				System.out.println(row);

				for (Iterator propertyIterator = row.entrySet().iterator(); propertyIterator.hasNext();) {
					java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
					// System.out.println( BeanUtils.getProperty(row,
					// propertyEntry.getKey().toString()));
					csvFileOutputStream.write("\"" + propertyEntry.getValue().toString() + "\"");
					if (propertyIterator.hasNext()) {
						csvFileOutputStream.write(",");
					}
				}
				if (iterator.hasNext()) {
					csvFileOutputStream.newLine();
				}
			}
			csvFileOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}

	public static void writeCSV(String filePath ,String row) {
		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			// 创建文件
			csvFile = new File(filePath);
			File parent = csvFile.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			//csvFile.createNewFile();
			// GB2312使正确读取分隔符","
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"),
					1024);
			csvFileOutputStream.write(row);
			csvFileOutputStream.newLine();
			csvFileOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
