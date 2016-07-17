package jp.ac.hal.httpsample;

public class Shop {
    private int id;
    private String name;
    private String address;
    private double lat;
    private double lng;
    public Shop(int id,String name , String address,double lat,double lng){
        this.id =id;
        this.name=name;
        this.address=address;
        this.lat=lat;
        this.lng=lng;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getAddress(){
        return address;
    }
    public double getLat(){
        return lat;
    }
    public double getLng(){
        return lng;
    }
}

