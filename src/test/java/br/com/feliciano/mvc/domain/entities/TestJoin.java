package br.com.feliciano.mvc.domain.entities;

import java.util.List;

import br.com.feliciano.mvc.controller.InventoryController;
import br.com.feliciano.mvc.domain.entities.vo.InventoryVO;

public class TestJoin {

    public static void main(String[] args) {

      InventoryController controller = new InventoryController();
    	  List<InventoryVO> listInventoriesVo = controller.list();
    	  listInventoriesVo.forEach(System.out::println);
    }

}