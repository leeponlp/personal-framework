package com.personal.utils;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlUtil {

	//private static Logger logger = Logger.getLogger(XmlUtil.class);

	public static String formatXml(String str, String encoding) {
		try {
			StringWriter writer = new StringWriter();
			formatXml(str, encoding, writer);
			writer.close();
			return writer.toString();
		} catch (Exception e) {
			return str;
		}
	}

	/**
	 * @param str
	 * @param encoding
	 * @param writer
	 * @throws IOException
	 */
	public static void formatXml(String str, String encoding, Writer writer) throws IOException {
		OutputFormat format = OutputFormat.createCompactFormat();
		format.setEncoding(encoding);
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		try {
			org.dom4j.Document document = DocumentHelper.parseText(str);
			xmlWriter.write(document);
		} catch (DocumentException e) {
		}

	}

	public static Document getDocument(String xml) {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new CharArrayReader(xml.toCharArray()));
		} catch (DocumentException e) {
		}
		return document;
	}

	public static String filterInvalid(String xml) {
		StringBuffer sb = new StringBuffer(xml);
		for (int i = 0; i < sb.length(); i++) {
			int c = sb.charAt(i);
			if (c < 0x20 && c != 0x09/* \t */ && c != 0x0A/* \n */ && c != 0x0D/* \r */) {
				sb.delete(i, i + 1);
			}
		}
		return sb.toString();
	}

	/**
	 * @see xpath syntax: http://www.w3schools.com/xpath/default.asp
	 * @param xml
	 * @param xpath
	 * @return
	 */
	public static String getNodeText(String xml, String xpath) {
		return getNodeText(getDocument(xml), xpath);
	}

	/**
	 * @see xpath syntax: http://www.w3schools.com/xpath/default.asp
	 * @param document
	 * @param xpath
	 * @return
	 */
	public static String getNodeText(Document document, String xpath) {
		if (document == null)
			return null;
		try {
			@SuppressWarnings("unchecked")
			List<Node> nodeList = document.selectNodes(xpath);
			if (nodeList.isEmpty())
				return null;
			return getText(nodeList.get(0));
		} catch (Exception e) {
			return null;
		}
	}

	public static List<String> getNodeTextList(String xml, String xpath, boolean ignoreEmpty) {
		return getNodeTextList(getDocument(xml), xpath, ignoreEmpty);
	}

	public static List<String> getNodeTextList(Document document, String xpath, boolean ignoreEmpty) {
		List<String> result = new ArrayList<String>();
		if (document == null)
			return result;
		try {
			@SuppressWarnings("unchecked")
			List<Node> nodeList = document.selectNodes(xpath);
			for (Node node : nodeList) {
				String s = getText(node);
				if (StringUtils.isNotBlank(s) || !ignoreEmpty)
					result.add(s);
			}
		} catch (Exception e) {
		}
		return result;
	}

	private static String getText(Node node) {
		if (node instanceof Attribute) {
			return ((Attribute) node).getValue();
		} else {
			return node.getText();
		}
	}

	public static Map<String, Object> xml2Map(String infoXML) {
		Document document;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			document = DocumentHelper.parseText(infoXML);
			Element root = document.getRootElement();
			Iterator<?> it = root.elements().iterator();
			while (it.hasNext()) {
				Element info = (Element) it.next();
				map.put(info.getName(), info.getText());
				Iterator<?> itc = info.elements().iterator();
				while (itc.hasNext()) {
					Element infoc = (Element) itc.next();
					map.put(infoc.getName(), infoc.getText());
				}
			}
		} catch (DocumentException e1) {
		}
		return map;
	}

}
