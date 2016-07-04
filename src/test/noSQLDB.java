package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import com.google.gson.Gson;

public class noSQLDB {
	
	public void createNoSQLDB(String path) throws Exception {
		FileOutputStream file = new FileOutputStream(path);
		Document document = new Document();
		Element root = new Element("database");
		Element sort1 = new Element("table");
		sort1.setAttribute("name", "testPerson");
		Element sort2 = new Element("table");
		sort2.setAttribute("name", "system.indexs");
		Element sort3 = new Element("table");
		sort3.setAttribute("name", "system.users");

		root.addContent(sort1);
		root.addContent(sort2);
		root.addContent(sort3);
		
		document.setRootElement(root);
		XMLOutputter out = new XMLOutputter();
		out.output(document, file);
		
	}
	
	public void updateData(String path, String tablename, Object po) throws Exception {
		FileInputStream file = new FileInputStream(path);
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = saxBuilder.build(file);
		Element root = document.getRootElement();
		Gson gson = new Gson();

		List<Element> list = root.getChildren();
		for (Element x : list) {
			if (x.getAttributeValue("name").equals(tablename)) {
				List<Element> al = x.getChildren();
				for (Element s : al) {
					// using guid to search
					Map mp = gson.fromJson(s.getText(), Map.class);
					Map newmp = gson.fromJson(gson.toJson(po), Map.class);
					if (mp.get("guid").equals(newmp.get("guid"))) {
						mp.putAll(newmp);
						Element data = new Element("data");
						data.setText(gson.toJson(mp));
						x.removeContent(s);
						x.addContent(data);
						break;
					}
				}
			}
		}
	}
	
	public void addData(String path, String tablename, Object po) throws Exception {
		Gson gson = new Gson();
	   FileInputStream inputFile = new FileInputStream(path);
	   SAXBuilder xmlBuild = new SAXBuilder();
	   Document document = xmlBuild.build(inputFile);
	   Element rootElement = document.getRootElement();
	   
	   List<Element> list = rootElement.getChildren();
	   for (Element elmt:list) {
		   if(elmt.getAttributeValue("name").equals(tablename)){
			   Map mapPK = new HashMap();
			   mapPK.put("guid", UUID.randomUUID().toString());
			   
			   String json = gson.toJson(po);
			   Element element = new Element ("data");
			   Map mp = gson.fromJson(json, Map.class);
			   mapPK.putAll(mp);
			   element.addContent(gson.toJson(mapPK));
				elmt.addContent(element);
			   
		   }
	   }
	   
	   XMLOutputter output = new XMLOutputter();
	   output.output(document, new FileOutputStream(path));
		
	}
	
	public List<Map> loadTableDatas(String path, String tablename) throws Exception {
		Gson gson = new Gson();
		FileInputStream file = new FileInputStream(path);
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = saxBuilder.build(file);
		Element root = document.getRootElement();

		List<Map> l = new ArrayList();
		List<Element> list = root.getChildren();
		for (Element x : list) {
			if (x.getAttributeValue("name").equals(tablename)) {
				List<Element> al = x.getChildren();
				for (Element s : al) {
					String data = s.getText();
					if (data == null) {
						break;
					}
					Map mp = gson.fromJson(data, Map.class);
					l.add(mp);
				}
			}
		}
		return l;
	}
	
	public void deleteData(String path, String tablename, Object po)
			throws Exception {
		FileInputStream file = new FileInputStream(path);
		Gson gson = new Gson();
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = saxBuilder.build(file);
		Element root = document.getRootElement();
		List<Element> list = root.getChildren();
		for (Element x : list) {
			List<Element> al = x.getChildren();
			if (x.getAttributeValue("name").equals(tablename)) {
				for (Element s : al) {
					// using guid to search
					Map mp = gson.fromJson(s.getText(), Map.class);
					Map newmp = gson.fromJson(gson.toJson(po), Map.class);
					if (mp.get("guid").equals(newmp.get("guid"))) {
						x.removeContent(s);
						break;
					}
				}
			}
		}
		XMLOutputter out = new XMLOutputter();
		out.output(document, new FileOutputStream(path));
	}
	

}
