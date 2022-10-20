package Models;

public class OrderModel {
    private String OrderNumber;
    private String OrderItemCount;
    private String OrderPlacedDate;
    private String OrderAmount;
    private String Status;
    private String OrderName;

    public OrderModel(String orderNumber, String orderItemCount,String orderName, String orderPlacedDate, String orderAmount, String status) {
        OrderNumber = orderNumber;
        OrderItemCount = orderItemCount;
        OrderPlacedDate = orderPlacedDate;
        OrderAmount = orderAmount;
        OrderName = orderName;
        Status = status;
    }

    public String getOrderName() {
        return OrderName;
    }

    public void setOrderName(String orderName) {
        OrderName = orderName;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }

    public String getOrderItemCount() {
        return OrderItemCount;
    }

    public void setOrderItemCount(String orderItemCount) {
        OrderItemCount = orderItemCount;
    }

    public String getOrderPlacedDate() {
        return OrderPlacedDate;
    }

    public void setOrderPlacedDate(String orderPlacedDate) {
        OrderPlacedDate = orderPlacedDate;
    }

    public String getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        OrderAmount = orderAmount;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
