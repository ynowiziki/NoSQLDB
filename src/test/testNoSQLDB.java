package test;
import java.util.List;
import java.util.Map;


public class testNoSQLDB {
	public static void main(String[] args) {
		new testNoSQLDB().createDatabse();//创建数据库
		new testNoSQLDB().insertData();//插入数据
		new testNoSQLDB().updateData();//修改数据
		//new testNoSQLDB().deleteData();//删除数据
		new testNoSQLDB().listAll();//列表数据
	}
	
	
	/**
	 * 创建数据库
	 */
	public void createDatabse(){
		try {
			noSQLDB dao = new noSQLDB();
			dao.createNoSQLDB("data.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 插入数据
	 */
	public void insertData(){
		Person po = new Person();
		po.setName("derek");
		po.setAge(30);
		po.setMoney(200098);
		po.setSex("男");
		try {
			noSQLDB dao = new noSQLDB();
			dao.addData("data.xml","testPerson",po);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改数据
	 */
	public void updateData(){
		Person po = new Person();
		po.setguid(_guid);
		po.setName("derek yang");
		po.setAge(28);
		try {
			noSQLDB dao = new noSQLDB();
			dao.updateData("data.xml","testPerson",po);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 删除数据
	 */
	public void deleteData(){
		Person po = new Person();
		po.setguid(_guid);
		try {
			noSQLDB dao = new noSQLDB();
			dao.deleteData(database_name,"testPerson",po);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取所有数据
	 */
	public void listAll(){
		try {
			noSQLDB dao = new noSQLDB();
			List<Map> list = dao.loadTableDatas(database_name,"testPerson");
			System.out.println(list.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	String database_name = "data.xml";
	String _guid = "c66c05e9-b524-4ab8-a9c8-4b742a9d0d5f";

}
