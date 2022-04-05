package test;

import java.util.List;

import controller.InventoryController;
import infra.exceptions.DbException;
import vo.InventoryVO;

public class TestJoin {

    public static void main(String[] args) throws DbException {

      InventoryController controller = new InventoryController();
    	  List<InventoryVO> listInventoriesVo = controller.list();
    	  listInventoriesVo.forEach(System.out::println);
    }

}
