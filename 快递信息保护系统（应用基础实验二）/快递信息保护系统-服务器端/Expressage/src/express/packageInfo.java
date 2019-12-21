package express;

public class packageInfo {
private String client;
private String address;
private String phone;
private String courier;
private String id;
private String send;
private String phone_send;

    public packageInfo() {
        this.client = "";
        this.address = "";
        this.phone = "";
        this.courier = "";
        this.id = "";
        this.send = "";
        this.phone_send = "";
    }

    public packageInfo(String client, String address, String phone, String courier, String id, String send, String phone_send) {
        this.client = client;
        this.address = address;
        this.phone = phone;
        this.courier = courier;
        this.id = id;
        this.send = send;
        this.phone_send = phone_send;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getPhone_send() {
        return phone_send;
    }

    public void setPhone_send(String phone_send) {
        this.phone_send = phone_send;
    }
}
