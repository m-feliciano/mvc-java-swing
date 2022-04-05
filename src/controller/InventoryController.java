package controller;

import java.sql.Connection;
import java.util.List;

import dao.InventoryDAO;
import entities.Inventory;
import infra.ConnectionFactory;
import vo.InventoryVO;

public class InventoryController {

    private final InventoryDAO inventoryDAO;

    public InventoryController() {
        Connection conn = new ConnectionFactory().getConnection();
        this.inventoryDAO = new InventoryDAO(conn);
    }

    public void save(Inventory inventory) {
        this.inventoryDAO.save(inventory);
    }

    public void delete(int id) {
        this.inventoryDAO.delete(id);
    }

    public List<InventoryVO> list() {
        return this.inventoryDAO.list();
    }

    //
//    public List<Product> getProductsByCategoryId(int id) {
//        return this.inventoryDAO.getProductsByCategoryId(id);
//    }
//
//    public Inventory findById(int id) {
//        return this.inventoryDAO.findById(id);
//    }
//
    public void update(Inventory inventory) {
        this.inventoryDAO.update(inventory);
    }

}
